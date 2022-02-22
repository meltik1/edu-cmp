package com.edu_netcracker.cmp.notificationEngine.sessionImpl;

import com.edu_netcracker.cmp.entities.Session;
import com.edu_netcracker.cmp.entities.SessionStatus;
import com.edu_netcracker.cmp.entities.repositories.STARepository;
import com.edu_netcracker.cmp.entities.repositories.SessionsRepository;
import com.edu_netcracker.cmp.notificationEngine.*;
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

    private NotificationService notificationService;

    private ITemplate iTemplate;

    private IUserMessageInfo iUserMessageInfo;

    private SessionsRepository sessionsRepository;

    private STARepository STARepository;

    private EAVInfoTransformer eavInfoTransformer;

    private List<NotificationService> notificationServicesRealisation;

    private FileHandler handler;

    @Autowired
    public SessionServiceImpl(NotificationService notificationService, ITemplate iTemplate, IUserMessageInfo iUserMessageInfo, SessionsRepository sessionsRepository, STARepository STARepository, EAVInfoTransformer eavInfoTransformer, List<NotificationService> notificationServicesRealisation, FileHandler handler) {
        this.notificationService = notificationService;
        this.iTemplate = iTemplate;
        this.iUserMessageInfo = iUserMessageInfo;
        this.sessionsRepository = sessionsRepository;
        this.STARepository = STARepository;
        this.eavInfoTransformer = eavInfoTransformer;
        this.notificationServicesRealisation = notificationServicesRealisation;
        this.handler = handler;
    }


    @Override
    public List<Session> getAllSessions() {
        return sessionsRepository.findAll();
    }

    @Override
    public Session createSession(String name) {
        Session session = new Session();

        session.setName(name);
        session.setStatus(SessionStatus.FILESELECTION);
        session.setDate(this.getDate());
        sessionsRepository.save(session);
        return session;
    }

    @Override
    public Session getSession(String id) {
        return sessionsRepository.findById(id).get();
    }

    @Override
    public void deleteSession(String id) {
        Session session = sessionsRepository.findById(id).get();
        sessionsRepository.delete(session);
    }

    @Override
    public void parseFile(String id, MultipartFile file) {
       Session session =  sessionsRepository.findById(id).get();
       String json = handler.handle(file);
       session.setUsersInfoJSON(json);
       session.setStatus(SessionStatus.MAPPING);
       sessionsRepository.save(session);
    }

    @Override
    public String getStudentsAttributes(String id) {
        Session session = sessionsRepository.findById(id).get();
        return session.getUsersInfoJSON();
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
    public void sendMessages(String id) throws JsonProcessingException {
        Session session = sessionsRepository.findById(id).get();

        iTemplate.setTemplate(session.getTemplate());

        String students = session.getUsersInfoJSON();
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

                            try {
                                service.send(iUserMessageInfo, iTemplate);
                                studentIReport.put("Email status", "ok");
                            }
                            catch (Exception exception) {
                                studentIReport.put("Email status", "Ошибка отправки Email");
                            }
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
            sessionsRepository.save(session);
        }
    }

    public void transformDataToEAV(String sessionId) throws JsonProcessingException {
        Session session = sessionsRepository.findById(sessionId).get();
        eavInfoTransformer.transformUserDataToEAV(session);
    }

    @Override
    public String getReport(String id) {
        Session session = sessionsRepository.findById(id).get();
        String report =  session.getReportJSON();
        if (report != null) {
            session.setStatus(SessionStatus.REPORT);
            sessionsRepository.save(session);
        }
        return report;
    }

    @Override
    public String getTemplate(String id) {
        return sessionsRepository.findById(id).get().getTemplate();
    }

    @Override
    public List<String> getMappedAttributes(String id) {
        Map<String, String> params = sessionsRepository.findById(id).get().getColumnMappingMap();
        List<String> paramsList = new ArrayList<>(params.values());

        return paramsList;

    }

    @Override
    public void saveTemplate(String id, String template) {
        Session session = sessionsRepository.findById(id).get();
        session.setTemplate(template);
        session.setStatus(SessionStatus.VALIDATION);
        sessionsRepository.save(session);
    }

    @Override
    public void saveTheme(String id, String theme) {
        Session session = sessionsRepository.findById(id).get();
        session.setTheme(theme);
        sessionsRepository.save(session);
    }

    @Override
    public String getTheme(String id) {
        Session session = sessionsRepository.findById(id).get();
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
            if (startValue < 0 || endValue < 0) {
                throw new IllegalArgumentException("Range is invalid");
            }

            if (startValue < minStart || endValue < minEnd) {
                throw new IllegalArgumentException("Range is invalid");
            }

            if (startValue > endValue) {
                throw new IllegalArgumentException("Range is invalid");
            }

        }
    }

    @Override
    public void saveMapping(String sessionId, String json) throws JsonProcessingException {
        Session session = sessionsRepository.findById(sessionId).get();
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
        sessionsRepository.save(session);
    }

    @Override
    public String getValidationTemplate(String id) {
        String result = "";
        Session session = sessionsRepository.findById(id).get();
        try {
            Map<String, String> params = session.getColumnMappingMap();
            String s = handler.sendAttributes(session.getUsersInfoJSON(), params);
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
