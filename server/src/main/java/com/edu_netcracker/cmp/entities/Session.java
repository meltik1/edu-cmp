package com.edu_netcracker.cmp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
public class Session {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private SessionStatus status;


    private String name;


    @ElementCollection
    private Map<String, String> columnMappingMap;

    private String studentsJSON;
    private String rangeJSON;
    private String template;
}
