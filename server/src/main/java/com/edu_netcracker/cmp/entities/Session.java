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
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private SessionStatus status;

    private String columnMappingJson;

    @Transient
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> columnMappingMap;

    private String template;
}
