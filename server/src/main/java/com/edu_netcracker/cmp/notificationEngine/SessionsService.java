package com.edu_netcracker.cmp.notificationEngine;

import com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO;
import com.edu_netcracker.cmp.entities.Session;
import com.edu_netcracker.cmp.entities.Students;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface SessionsService {

     List<Session> getAllSessions();

     Session createSession(String name);

     Session getSession(Long id);

     void deleteSession(Long id);

     void parseFile(Long id, MultipartFile file);

     String getStudentsAttributes(Long id);


     List<String> getMappedAttributes(Long id);

     void sendMessages(Long id) throws JsonProcessingException;

     String getReport(Long id);


     void saveMapping(Long sessionID, String json) throws JsonProcessingException;

     String getValidationTemplate(Long id);

     void saveTemplate(Long id, String template);

     String getTemplate(Long id);


}
