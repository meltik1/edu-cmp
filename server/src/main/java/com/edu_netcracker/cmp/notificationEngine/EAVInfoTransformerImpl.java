package com.edu_netcracker.cmp.notificationEngine;

import com.edu_netcracker.cmp.entities.*;
import com.edu_netcracker.cmp.entities.repositories.AttributesRepository;
import com.edu_netcracker.cmp.entities.repositories.STARepository;
import com.edu_netcracker.cmp.entities.repositories.UsersRepository;
import com.edu_netcracker.cmp.entities.users.Role;
import com.edu_netcracker.cmp.entities.users.User;
import com.edu_netcracker.cmp.notificationEngine.parserImpl.FileHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class EAVInfoTransformerImpl implements EAVInfoTransformer {

    private ObjectMapper mapper;

    private FileHandler handler;

    private STARepository staRepository;
    private UsersRepository usersRepository;
    private AttributesRepository attributesRepository;

    @Autowired
    private PasswordEncoder encoder;

    private String EMAIL = "Email";
    private String FIO = "ФИО";


    private List<String> dataSources = List.of("Email", "Telegram");


    @Autowired
    public EAVInfoTransformerImpl(ObjectMapper mapper, FileHandler handler, STARepository staRepository, UsersRepository usersRepository, AttributesRepository attributesRepository) {
        this.mapper = mapper;
        this.handler = handler;
        this.staRepository = staRepository;
        this.usersRepository = usersRepository;
        this.attributesRepository = attributesRepository;
    }

    private String EMAIL_FIELD = "Email";

    @Override
    public void transformUserDataToEAV(Session session) throws JsonProcessingException {

        log.info("Started saving users to DB");
        if (session.getStatus() != SessionStatus.REPORT) {
            log.error("Illegal state of session");
            throw new IllegalArgumentException("Wrong session condition");
        }

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
            if (checkRange(rangeJSONNode, index)) {
                Map<String, String> mappedAttributes = mapper.convertValue(node, new TypeReference<Map<String, String>>() {
                });
                Map<String, String> studentIReport = new HashMap<>(mappedAttributes);
                saveNewEAVSToDB(studentIReport);

            }
        }
        log.info("Finished saving users to db");
    }

    public Boolean checkRange(JsonNode range, Integer index) {

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

    private User createUserOrGetIfExisted(Map<String, String> map) {
        User usersEmail = usersRepository.findUsersByUserName(map.get("Email"));
        if (usersEmail == null) {
            usersEmail = new User();
            usersEmail.setUserName(map.get(EMAIL));
            usersEmail.setPassword(encoder.encode("123"));
            usersEmail.setFIO(map.get(FIO));
            usersEmail.setRole(Role.USER);
            usersRepository.save(usersEmail);
        }

        return usersEmail;
    }

    private StudentsToAttributes createEavOrGetExisted(User user, Attributes attributes) {
        //StudentsToAttributes studentToAttribute = staRepository.findOne(new STAID(attributes.getId(), user.getUserName()));
        StudentsToAttributes studentToAttribute = staRepository.findById(new STAID(attributes.getId(), user.getUserName())).orElse(null);
        if (studentToAttribute == null) {
            studentToAttribute = new StudentsToAttributes();
            studentToAttribute.setStudent(user);
            studentToAttribute.setAttributes(attributes);
        }
        return studentToAttribute;
    }

    private void saveNewEAVSToDB(Map<String,String> map) {
        if (map.get("Email") == null || map.get(EMAIL_FIELD).equals("")) {
            log.error("User doesn't have email");
            return;
        }




        User student  = createUserOrGetIfExisted(map);

        for (Map.Entry<String, String> it: map.entrySet()) {
            String key = it.getKey();
            Object value = it.getValue();
            Attributes attribute = addNewAttributeOrGetIfExisted(key);

            StudentsToAttributes studentToAttribute = createEavOrGetExisted(student, attribute);
            studentToAttribute.setCharValue((String) value);


            staRepository.save(studentToAttribute);
        }
    }

    private Attributes addNewAttributeOrGetIfExisted(String attributeName) {
        Attributes attributes = attributesRepository.findAttributesByAttributeName(attributeName);

        if (attributes == null) {
            attributes = new Attributes();
            attributes.setAttributeName(attributeName);
            if (dataSources.contains(attributeName)) attributes.setIsCommunicationSource(true);
            attributesRepository.save(attributes);
            attributes = attributesRepository.findAttributesByAttributeName(attributeName);
        }

        return attributes;
    }
}
