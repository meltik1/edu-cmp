package com.edu_netcracker.cmp.notificationEngine;

import com.edu_netcracker.cmp.entities.Session;
import com.edu_netcracker.cmp.entities.Students;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface SessionsService {

     Session createSession();

     Session getSession(Long id);

     void deleteSession(Long id);

     void parseFile(Long id, MultipartFile file);

     List<StudentsToAttributes> getStudents(Long id);

     String getReport(Long id);

     void saveMapping(Map<String, String> map);

     String getValidationTemplate(Long id);


}
