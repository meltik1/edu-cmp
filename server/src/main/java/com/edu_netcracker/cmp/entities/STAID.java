package com.edu_netcracker.cmp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class STAID implements Serializable {

    private Long attributeId;

    private String studentId;

    public STAID(Long attributeId, String studentId) {
        this.attributeId = attributeId;
        this.studentId = studentId;
    }

    public STAID() {

    }

    public Long getAttributeId() {
        return attributeId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
