package com.edu_netcracker.cmp.RestControllers;

import com.edu_netcracker.cmp.entities.Session;
import com.edu_netcracker.cmp.notificationEngine.SessionsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "sessions")
public class SessionsControllers {

    @Autowired
    SessionsService sessionsService;

    @RequestMapping( method = RequestMethod.GET)
    public List<Session> getAllSessions() {
        return sessionsService.getAllSessions();
    }

    @RequestMapping(value = "create")
    public Session createSessions(@RequestParam String name) {
        return sessionsService.createSession(name);
    }

    @RequestMapping(value = "{id}" , method = RequestMethod.GET)
    public Session getSession(@PathVariable String id) {
        return sessionsService.getSession(Long.parseLong(id));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable String id) {
        sessionsService.deleteSession(Long.parseLong(id));
    }

    @RequestMapping(value = "{id}/pick-file", method = RequestMethod.POST)
    public void pickFile(@PathVariable String id, @RequestBody MultipartFile file) {
        sessionsService.parseFile(Long.parseLong(id), file);
    }

    @RequestMapping(value = "{id}/map-columns", method = RequestMethod.GET)
    public String getMapColumns(@PathVariable String id) {
        return sessionsService.getStudentsAttributes(Long.parseLong(id));
    }

    @RequestMapping(value = "{id}/save-columns-mapping", method = RequestMethod.POST)
    public ResponseEntity<String> saveMapColumns(@PathVariable String id, @RequestBody String body) {
        try {
            sessionsService.saveMapping(Long.parseLong(id), body);
            return ResponseEntity.ok("ok");
        }
       catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "{id}/get-template", method = RequestMethod.GET)
    public String getTemplate(@PathVariable String id) {
        return sessionsService.getTemplate(Long.parseLong(id));
    }

    @RequestMapping(value = "{id}/save-template", method = RequestMethod.POST)
    public void saveTemplate(@PathVariable String id, @RequestBody String template) {
        sessionsService.saveTemplate(Long.parseLong(id), template);
    }

    @RequestMapping(value = "{id}/validation", method = RequestMethod.GET)
    public String validation(@PathVariable String id) {
        return sessionsService.getValidationTemplate(Long.parseLong(id));
    }

    @RequestMapping(value = "{id}/send", method = RequestMethod.GET)
    public void sendMessages(@PathVariable String id) throws JsonProcessingException {
        sessionsService.sendMessages(Long.parseLong(id));
    }

    @RequestMapping(value = "{id}/report", method = RequestMethod.GET)
    public void report(@PathVariable String id) {

    }
}
