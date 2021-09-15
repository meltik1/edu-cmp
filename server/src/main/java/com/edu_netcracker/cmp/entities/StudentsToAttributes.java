package com.edu_netcracker.cmp.entities;

import com.edu_netcracker.cmp.entities.users.User;
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
    User student;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_name")
    Attributes attributes;

    @Column(name = "int_value")
    private Integer intValue;


    @Column(name = "char_value")
    private String charValue;

    @Column(name = "date_value")
    private Date dateValue;
}
