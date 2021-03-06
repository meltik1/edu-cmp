package com.edu_netcracker.cmp.notificationEngine.sessionImpl;

import com.edu_netcracker.cmp.entities.Session;
import com.edu_netcracker.cmp.entities.SessionStatus;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.jpa.SessionJPA;
import com.edu_netcracker.cmp.notificationEngine.SessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class SessionServiceImpl implements SessionsService {
    @Autowired
    SessionJPA sessionJPA;

    @Override
    public List<Session> getAllSessions() {
        List<Session> sessions = sessionJPA.findAll();

        return sessions;
    }

    @Override
    public Session createSession() {
        Session session = new Session();

        session.setStatus(SessionStatus.FILESELECTION);
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

    }

    @Override
    public List<StudentsToAttributes> getStudentsAttributes(Long id) {
        return null;
    }

    @Override
    public String getReport(Long id) {
        return null;
    }

    @Override
    public void saveMapping(Map<String, String> map) {

    }

    @Override
    public String getValidationTemplate(Long id) {
        return null;
    }
}
