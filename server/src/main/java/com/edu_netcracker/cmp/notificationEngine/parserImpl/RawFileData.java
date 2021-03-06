package com.edu_netcracker.cmp.notificationEngine.parserImpl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class RawFileData {
    private Map<Integer, String> rawFileData;

    public void addStudentAttributes(int attribute, String value) {
        if (rawFileData == null) {
            rawFileData = new HashMap<>();
        }
        rawFileData.put(attribute, value);
    }
}
