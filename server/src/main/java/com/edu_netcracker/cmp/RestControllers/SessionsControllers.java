package com.edu_netcracker.cmp.RestControllers;

import com.edu_netcracker.cmp.entities.Session;
import com.edu_netcracker.cmp.notificationEngine.SessionsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "sessions")
public class SessionsControllers {

    @Autowired
    SessionsService sessionsService;

    @RequestMapping( method = RequestMethod.GET)
    public List<Session> getAllSessions() {
        return sessionsService.getAllSessions();
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Session createSessions(@RequestParam String name) {
        return sessionsService.createSession(name);
    }

    @RequestMapping(value = "{id}" , method = RequestMethod.GET)
    public Session getSession(@PathVariable String id) {
        return sessionsService.getSession(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteSession(@PathVariable String id) {
        sessionsService.deleteSession(id);
    }

    @RequestMapping(value = "{id}/pick-file", method = RequestMethod.POST)
    public void pickFile(@PathVariable String id, @RequestBody MultipartFile file) {
        sessionsService.parseFile(id, file);
    }

    @RequestMapping(value = "{id}/map-columns", method = RequestMethod.GET)
    public String getMapColumns(@PathVariable String id) {
        return sessionsService.getStudentsAttributes(id);
    }

    @RequestMapping(value = "{id}/save-to-db", method = RequestMethod.GET)
    public void saveStudentsInfoToDB(@PathVariable String id) throws JsonProcessingException {
        sessionsService.transformDataToEAV(id);
    }


    @RequestMapping(value = "{id}/save-columns-mapping", method = RequestMethod.POST)
    public ResponseEntity<String> saveMapColumns(@PathVariable String id, @RequestBody String body) {
        try {
            sessionsService.saveMapping(id, body);
            return ResponseEntity.ok("ok");
        }
       catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "{id}/get-template", method = RequestMethod.GET)
    public String getTemplate(@PathVariable String id) {
        return sessionsService.getTemplate(id);
    }

    @RequestMapping(value = "{id}/get-theme", method = RequestMethod.GET)
    public String getTheme(@PathVariable String id) {
        return sessionsService.getTheme(id);
    }

    @RequestMapping(value = "{id}/save-template", method = RequestMethod.POST)
    public void saveTemplate(@PathVariable String id, @RequestBody String template) {
        sessionsService.saveTemplate(id, template);
    }

    @RequestMapping(value = "{id}/save-template-theme", method = RequestMethod.POST)
    public void saveTheme(@PathVariable String id, @RequestBody String theme) {
        sessionsService.saveTheme(id, theme);
    }

    @RequestMapping(value = "{id}/validation", method = RequestMethod.GET)
    public String validation(@PathVariable String id) {
        return sessionsService.getValidationTemplate(id);
    }

    @RequestMapping(value = "{id}/send", method = RequestMethod.GET)
    public void sendMessages(@PathVariable String id) throws JsonProcessingException {
        sessionsService.sendMessages(id);
    }

    @RequestMapping(value = "{id}/attributes", method = RequestMethod.GET)
    public List<String> getAttributes(@PathVariable String id) {
        return sessionsService.getMappedAttributes(id);
    }

    @RequestMapping(value = "{id}/report", method = RequestMethod.GET)
    public String report(@PathVariable String id) {
        return sessionsService.getReport(id);
    }
}
