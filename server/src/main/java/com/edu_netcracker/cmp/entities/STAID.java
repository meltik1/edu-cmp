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

    private Long studentId;

    public STAID(Long attributeId, Long studentId) {
        this.attributeId = attributeId;
        this.studentId = studentId;
    }

    public STAID() {

    }

    public Long getAttributeId() {
        return attributeId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
