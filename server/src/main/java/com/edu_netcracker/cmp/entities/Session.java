package com.edu_netcracker.cmp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Map;

@Document
@Getter
@Setter
public class Session {

    @Id
    @Column(name = "id")
    private String id;

    @Enumerated(EnumType.ORDINAL)
    private SessionStatus status;


    private String name;

    private String date;


    @ElementCollection
    private Map<String, String> columnMappingMap;

    @Column(length=100000)
    private String usersInfoJSON;

    @Column(length = 100000)
    private String reportJSON;

    private String rangeJSON;
    private String template;
    private String theme;
}
