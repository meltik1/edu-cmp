package com.edu_netcracker.cmp.RestControllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "sessions")
public class SessionsControllers {

    @RequestMapping(value = "create")
    public void createSessions() {

    }

    @RequestMapping(value = "{id}/get")
    public void getSession(@PathVariable String id) {

    }

    @RequestMapping(value = "{id}/delete")
    public void deleteSession(@PathVariable String id) {

    }

    @RequestMapping(value = "{id}/pick-file")
    public void pickFile(@PathVariable String id) {

    }

    @RequestMapping(value = "{id}/map-columns")
    public void getMapColumns(@PathVariable String id) {

    }

    @RequestMapping(value = "{id}/send-columns-mapping")
    public void saveMapColumns(@PathVariable String id) {

    }

    @RequestMapping(value = "{id}/get-template")
    public void getTemplate(@PathVariable String id) {

    }

    @RequestMapping(value = "{id}/save-template")
    public void saveTemplate(@PathVariable String id) {

    }

    @RequestMapping(value = "{id}/validation")
    public void validation(@PathVariable String id) {

    }

    @RequestMapping(value = "{id}/report")
    public void report(@PathVariable String id) {

    }
}
