package com.edu_netcracker.cmp.notificationEngine.sessionImpl;

import com.edu_netcracker.cmp.entities.Session;
import com.edu_netcracker.cmp.entities.SessionStatus;
import com.edu_netcracker.cmp.entities.jpa.STAJPA;
import com.edu_netcracker.cmp.entities.jpa.SessionJPA;
import com.edu_netcracker.cmp.notificationEngine.ITemplate;
import com.edu_netcracker.cmp.notificationEngine.IUserMessageInfo;
import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import com.edu_netcracker.cmp.notificationEngine.SessionsService;
import com.edu_netcracker.cmp.notificationEngine.parserImpl.FileHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class SessionServiceImpl implements SessionsService {

    private final ObjectMapper mapper = new ObjectMapper();

    NotificationService notificationService;

    @Autowired
    ITemplate iTemplate;

    @Autowired
    IUserMessageInfo iUserMessageInfo;

    @Autowired
    SessionJPA sessionJPA;

    @Autowired
    STAJPA stajpa;

    @Autowired
    List<NotificationService> notificationServicesRealisation;

    @Autowired
    FileHandler handler;

    @Override
    public List<Session> getAllSessions() {
        List<Session> sessions = sessionJPA.findAll();

        return sessions;
    }

    @Override
    public Session createSession(String name) {
        Session session = new Session();

        session.setName(name);
        session.setStatus(SessionStatus.FILESELECTION);
        session.setDate(this.getDate());
        sessionJPA.save(session);
        return session;
    }

    @Override
    public Session getSession(Long id) {
        Session session = sessionJPA.findById(id).get();
        return session;
    }

    @Override
    public void deleteSession(Long id) {
        Session session = sessionJPA.getOne(id);
        sessionJPA.delete(session);
    }

    @Override
    public void parseFile(Long id, MultipartFile file) {
       Session session =  sessionJPA.getOne(id);
       String json = handler.handle(file);
       session.setStudentsJSON(json);
       session.setStatus(SessionStatus.MAPPING);
       sessionJPA.saveAndFlush(session);
    }

    @Override
    public String getStudentsAttributes(Long id) {
        Session session =  sessionJPA.getOne(id);
        return session.getStudentsJSON();
    }


    private Boolean checkIfInRange(JsonNode range, Integer index) {

        for(Iterator<JsonNode> itRange = range.elements(); itRange.hasNext(); ) {
            JsonNode rangeNode = itRange.next();
            int start = rangeNode.findValue("start").asInt();
            int end = rangeNode.findValue("end").asInt();

            if (index >= start && index <= end) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void sendMessages(Long id) throws JsonProcessingException {
        Session session = sessionJPA.getOne(id);

        iTemplate.setTemplate(session.getTemplate());

        String students = session.getStudentsJSON();
        Map<String, String> params = session.getColumnMappingMap();


        String s = handler.sendAttributes(students, params);

        JsonNode root = mapper.readTree(s);

        List<Map<String, String>> reportsOfAllStudents = new ArrayList<>();
        String range = session.getRangeJSON();
        JsonNode rangeJSONNode = mapper.readTree(range);


        Integer index = 1;
        for (Iterator<JsonNode> it = root.elements(); it.hasNext(); index++) {
            JsonNode node = it.next();
            if (checkIfInRange(rangeJSONNode, index)) {
                String telegramName = node.findValue("Telegram").asText();
                String email = node.findValue("Email").asText();
                Map<String, String> mappedAttributes = mapper.convertValue(node, new TypeReference<Map<String, String>>() {
                });
                Map<String, String> studentIReport = new HashMap<>(mappedAttributes);


                iTemplate.applyParams(mappedAttributes);
                this.saveTemplate(id, iTemplate.getTemplate());
                iTemplate.setTheme(session.getTheme());
                Map<String, String> contacts = new HashMap<>();
                contacts.put("Telegram", telegramName);
                contacts.put("Email", email);
                iUserMessageInfo.setMapOfContactId(contacts);
                for (NotificationService service : notificationServicesRealisation) {
                    if (service.getName().equals("Telegram")) {
                        if (telegramName != null && !(telegramName.equals(""))) {
                            try {
                                service.send(iUserMessageInfo, iTemplate);
                                studentIReport.put("Telegram status", "ok");
                            } catch (NullPointerException exception) {
                                studentIReport.put("Telegram status", "Пользователь не зарегистрирован в боте");
                            }
                        } else {
                            studentIReport.put("Telegram status", "Пользователь не указал свой ник ");
                        }
                    } else if (service.getName().equals("Email")) {
                        if (email != null && !(email.equals(""))) {
                            service.send(iUserMessageInfo, iTemplate);
                            studentIReport.put("Email status", "ok");
                        } else {
                            studentIReport.put("Email status", "Пользоватеь не указал Email");
                        }
                    }
                    else {
                        studentIReport.put("Telegram status", "Пользователь не указал свой ник ");
                    }
            }
            reportsOfAllStudents.add(studentIReport);
        }
            session.setReportJSON(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reportsOfAllStudents));
            sessionJPA.saveAndFlush(session);
        }
    }

    @Override
    public String getReport(Long id) {
        Session session = sessionJPA.getOne(id);
        String report =  session.getReportJSON();
        if (report != null) {
            session.setStatus(SessionStatus.REPORT);
            sessionJPA.save(session);
        }
        return report;
    }

    @Override
    public String getTemplate(Long id) {
        return sessionJPA.getOne(id).getTemplate();
    }

    @Override
    public List<String> getMappedAttributes(Long id) {
        Map<String, String> params = sessionJPA.findById(id).get().getColumnMappingMap();
        List<String> paramsList = new ArrayList<>(params.values());

        return paramsList;

    }

    @Override
    public void saveTemplate(Long id, String template) {
        Session session = sessionJPA.getOne(id);
        session.setTemplate(template);
        session.setStatus(SessionStatus.VALIDATION);
        sessionJPA.save(session);
    }

    @Override
    public void saveTheme(Long id, String theme) {
        Session session = sessionJPA.getOne(id);
        session.setTheme(theme);
        sessionJPA.save(session);
    }

    @Override
    public String getTheme(Long id) {
        Session session = sessionJPA.getOne(id);
        return session.getTheme();
    }

    private void rangeValidation(JsonNode node) throws IllegalArgumentException{
        List<String> start = node.findValuesAsText("start");
        List<String> end = node.findValuesAsText("end");

        if (end.size() != start.size()) {
            throw new IllegalArgumentException("Range is invalid");
        }
        Integer minStart = Integer.parseInt(start.get(0));
        Integer minEnd = Integer.parseInt(start.get(0));
        for (int i = 0; i < end.size(); i++) {
            Integer startValue = Integer.parseInt(start.get(i));
            Integer endValue = Integer.parseInt(end.get(i));

            if (startValue < minStart || endValue < minEnd) {
                throw new IllegalArgumentException("Range is invalid");
            }

            if (startValue > endValue) {
                throw new IllegalArgumentException("Range is invalid");
            }

        }
    }

    @Override
    public void saveMapping(Long sessionId, String json) throws JsonProcessingException {
        Session session = sessionJPA.getOne(sessionId);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode columnsMappingJson = root.at("/mapping");
        HashMap<String, String> columnsMapping = new HashMap<>();
        for (Iterator<Map.Entry<String, JsonNode>> it = columnsMappingJson.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> node = it.next();
            columnsMapping.put( node.getKey(), node.getValue().textValue() );
        }

        JsonNode rangeJson = root.at("/range");
        rangeValidation(rangeJson);

        session.setRangeJSON(rangeJson.toString());
        session.setColumnMappingMap(columnsMapping);
        session.setStatus(SessionStatus.TEMPLATE);
        sessionJPA.saveAndFlush(session);
    }

    @Override
    public String getValidationTemplate(Long id) {
        String result = "";
        Session session = sessionJPA.getOne(id);
        try {
            Map<String, String> params = session.getColumnMappingMap();
            String s = handler.sendAttributes(session.getStudentsJSON(), params);
            JsonNode root = mapper.readTree(s);
            Map<String, String> firstStudent = mapper.convertValue(root.get(0), new TypeReference<Map<String, String>>(){});
            iTemplate.setTemplate(session.getTemplate());
            iTemplate.applyParams(firstStudent);
            result = iTemplate.getTemplate();
        } catch (JsonProcessingException e) {
            log.warn("Unable to parse json file");
        }
        return result;
    }

    private String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }
}
