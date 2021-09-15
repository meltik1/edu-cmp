package com.edu_netcracker.cmp.notificationEngine;

import com.edu_netcracker.cmp.entities.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SessionsService {

     List<Session> getAllSessions();

     Session createSession(String name);

     Session getSession(Long id);

     void deleteSession(Long id);

     void parseFile(Long id, MultipartFile file);

     String getStudentsAttributes(Long id);

     public void transformDataToEAV(Long sessionId) throws JsonProcessingException;

     List<String> getMappedAttributes(Long id);

     void sendMessages(Long id) throws JsonProcessingException;

     String getReport(Long id);


     void saveMapping(Long sessionID, String json) throws JsonProcessingException;

     String getValidationTemplate(Long id);

     void saveTemplate(Long id, String template);

     String getTemplate(Long id);

     void saveTheme(Long parseLong, String theme);

     String getTheme(Long id);
}
