package com.edu_netcracker.cmp.entities.DTO;

import com.edu_netcracker.cmp.entities.SessionStatus;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Map;

@Data
public class SessionDTO {
    private String id;

    private SessionStatus status;

    private String name;

    private String date;

    private Map<String, String> columnMappingMap;

    private String usersInfoJSON;

    private String reportJSON;

    private String rangeJSON;

    private String template;

    private String theme;
}
