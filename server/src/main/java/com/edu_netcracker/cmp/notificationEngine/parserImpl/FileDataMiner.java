package com.edu_netcracker.cmp.notificationEngine.parserImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Component
@Slf4j
@Getter
@Setter
public abstract class FileDataMiner {

    private List<Student> students;
    private String        json;

    public void mine(MultipartFile file) {
        this.students = parseData(file);
        listToJson(students);
    }

    private void listToJson(List<Student> students) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            this.json = ow.writeValueAsString(students);
        } catch (JsonProcessingException e) {
            log.info(e.toString());
        }
    }

    public abstract List<Student> parseData(MultipartFile file);
}
