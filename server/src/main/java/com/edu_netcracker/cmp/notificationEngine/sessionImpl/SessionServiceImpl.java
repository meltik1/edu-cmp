package com.edu_netcracker.cmp.notificationEngine.sessionImpl;

import com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO;
import com.edu_netcracker.cmp.entities.Session;
import com.edu_netcracker.cmp.entities.SessionStatus;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.jpa.STAJPA;
import com.edu_netcracker.cmp.entities.jpa.SessionJPA;
import com.edu_netcracker.cmp.notificationEngine.ITemplate;
import com.edu_netcracker.cmp.notificationEngine.IUserMessageInfo;
import com.edu_netcracker.cmp.notificationEngine.NotificationService;
import com.edu_netcracker.cmp.notificationEngine.SessionsService;
import com.edu_netcracker.cmp.notificationEngine.parserImpl.FileHandler;

import com.edu_netcracker.cmp.notificationEngine.telegramImpl.NotificationServiceTG;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
public class SessionServiceImpl implements SessionsService {

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
        sessionJPA.save(session);
        return session;
    }

    @Override
    public Session getSession(Long id) {
        Session session = sessionJPA.getOne(id);
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

    @Override
    public void sendMessages(Long id) throws JsonProcessingException {
        Session session = sessionJPA.getOne(id);
        ObjectMapper mapper = new ObjectMapper();

        iTemplate.setTemplate(session.getTemplate());

        String students = session.getStudentsJSON();
        Map<String, String> params = session.getColumnMappingMap();
        String s = handler.sendAttributes(params);

        JsonNode root = mapper.readTree(s);


        // Каждая итерация цикла - новый студент
        for (Iterator<JsonNode> it = root.elements(); it.hasNext(); ) {
            JsonNode node = it.next();
            /*String telegramName = node.findValue(tgColumn).asText();
            String email = node.findValue(emailColumn).asText();
            iTemplate.applyParams(params);
            Map<String, String> contacts = new HashMap<>();
            contacts.put("telegram", telegramName);
            contacts.put("email", email);
            iUserMessageInfo.setMapOfContactId(contacts);
            if (telegramName != null) {
                notificationService = new NotificationServiceTG();
                notificationService.send(iUserMessageInfo, iTemplate);
            }*/
        }

    }

    @Override
    public String getReport(Long id) {
        Session session = sessionJPA.getOne(id);
        session.setStatus(SessionStatus.REPORT);
        return "Ok";
    }

    @Override
    public String getTemplate(Long id) {
        return sessionJPA.getOne(id).getTemplate();
    }

    @Override
    public void saveTemplate(Long id, String template) {
        Session session = sessionJPA.getOne(id);
        session.setTemplate(template);
        session.setStatus(SessionStatus.VALIDATION);
        sessionJPA.save(session);
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

        session.setRangeJSON(rangeJson.asText());
        session.setColumnMappingMap(columnsMapping);
        session.setStatus(SessionStatus.TEMPLATE);
        sessionJPA.saveAndFlush(session);
    }

    @Override
    public String getValidationTemplate(Long id) {
        Session session = sessionJPA.getOne(id);
        return session.getTemplate();
    }
}
