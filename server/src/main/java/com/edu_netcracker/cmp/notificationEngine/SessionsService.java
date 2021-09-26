package com.edu_netcracker.cmp.notificationEngine;

import com.edu_netcracker.cmp.entities.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SessionsService {

     List<Session> getAllSessions();

     Session createSession(String name);

     Session getSession(String id);

     void deleteSession(String id);

     void parseFile(String id, MultipartFile file);

     String getStudentsAttributes(String id);

     public void transformDataToEAV(String sessionId) throws JsonProcessingException;

     List<String> getMappedAttributes(String id);

     void sendMessages(String id) throws JsonProcessingException;

     String getReport(String id);


     void saveMapping(String sessionID, String json) throws JsonProcessingException;

     String getValidationTemplate(String id);

     void saveTemplate(String id, String template);

     String getTemplate(String id);

     void saveTheme(String parseLong, String theme);

     String getTheme(String id);
}
