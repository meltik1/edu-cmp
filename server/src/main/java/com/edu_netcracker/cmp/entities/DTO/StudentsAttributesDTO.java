package com.edu_netcracker.cmp.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentsAttributesDTO {

    private String attributeName;
    private Integer intValue;

    private String charValue;

    private Date dateValue;
}
