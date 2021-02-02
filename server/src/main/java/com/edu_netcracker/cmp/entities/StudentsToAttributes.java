package com.edu_netcracker.cmp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class StudentsToAttributes {
    public StudentsToAttributes() {
        this.staid = new STAID();
    }

    @EmbeddedId
    private STAID staid;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    Students student;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    Attributes attributes;

    private Integer int_value;



    private String char_value;

    private Date date_value;
}
