package com.edu_netcracker.cmp.entities;

import com.edu_netcracker.cmp.entities.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "students_to_attributes")
public class StudentsToAttributes  implements Serializable {
    public StudentsToAttributes() {
        this.staid = new STAID();
    }

    @EmbeddedId
    private STAID staid;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @MapsId("studentId")
    @JoinColumn(name = "student_id", referencedColumnName = "user_name", insertable = false, updatable = false)
    User student;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_name", referencedColumnName = "id", insertable = false, updatable = false)
    Attributes attributes;

    @Column(name = "int_value")
    private Integer intValue;


    @Column(name = "char_value")
    private String charValue;

    @Column(name = "date_value")
    private Date dateValue;
}
