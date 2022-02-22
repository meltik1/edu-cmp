package com.edu_netcracker.cmp.restControllers;

import com.edu_netcracker.cmp.entities.DTO.SessionDTO;
import com.edu_netcracker.cmp.entities.Session;
import com.edu_netcracker.cmp.notificationEngine.SessionsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "api/v1/sessions")
public class SessionsControllers {

    @Autowired
    SessionsService sessionsService;

    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<List<SessionDTO>>getAllSessions() {
        return
                new ResponseEntity<>(sessionsService.getAllSessions().stream().map(Session::toSessionDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<SessionDTO> createSessions(@RequestParam String name) {
        return
                new ResponseEntity<>(sessionsService.createSession(name).toSessionDTO(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}" , method = RequestMethod.GET)
    public ResponseEntity<SessionDTO> getSession(@PathVariable String id) {
        Session session = sessionsService.getSession(id);
        if ( session != null) {
            return new ResponseEntity<SessionDTO>(session.toSessionDTO(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSession(@PathVariable String id) {

        sessionsService.deleteSession(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/pick-file", method = RequestMethod.POST)
    public ResponseEntity<Void> pickFile(@PathVariable String id, @RequestBody MultipartFile file) {
        sessionsService.parseFile(id, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/map-columns", method = RequestMethod.GET)
    public ResponseEntity<String>  getMapColumns(@PathVariable String id) {
        return
                new ResponseEntity<>(sessionsService.getStudentsAttributes(id), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/save-to-db", method = RequestMethod.GET)
    public ResponseEntity<Void> saveStudentsInfoToDB(@PathVariable String id) throws JsonProcessingException {
        sessionsService.transformDataToEAV(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
    public  ResponseEntity<String>  getTemplate(@PathVariable String id) {
        return
                new ResponseEntity<>(sessionsService.getTemplate(id), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/get-theme", method = RequestMethod.GET)
    public ResponseEntity<String> getTheme(@PathVariable String id) {

        return new ResponseEntity<>(sessionsService.getTheme(id), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/save-template", method = RequestMethod.POST)
    public ResponseEntity<Void> saveTemplate(@PathVariable String id, @RequestBody String template) {
        sessionsService.saveTemplate(id, template);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/save-template-theme", method = RequestMethod.POST)
    public ResponseEntity<Void> saveTheme(@PathVariable String id, @RequestBody String theme) {
        sessionsService.saveTheme(id, theme);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/validation", method = RequestMethod.GET)
    public ResponseEntity<String> validation(@PathVariable String id) {
        return new ResponseEntity<>(sessionsService.getValidationTemplate(id),HttpStatus.OK) ;
    }

    @RequestMapping(value = "{id}/send", method = RequestMethod.GET)
    public ResponseEntity<Void> sendMessages(@PathVariable String id) throws JsonProcessingException {
        sessionsService.sendMessages(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/attributes", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getAttributes(@PathVariable String id) {
        return new ResponseEntity<>(sessionsService.getMappedAttributes(id), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/report", method = RequestMethod.GET)
    public ResponseEntity<String> report(@PathVariable String id) {

        return new ResponseEntity<>(sessionsService.getReport(id), HttpStatus.OK);
    }
}
