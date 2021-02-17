package com.edu_netcracker.cmp.notificationEngine.parserImpl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class Student {
    private Map<Integer, String> student;

    public void addStudentAttributes(int attribute, String value) {
        if (student == null) {
            student = new HashMap<>();
        }
        student.put(attribute, value);
    }
}
