package com.edu_netcracker.cmp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Attributes {
    @Id
    @GeneratedValue
    private Long id;

    private String attribute_name;


}
