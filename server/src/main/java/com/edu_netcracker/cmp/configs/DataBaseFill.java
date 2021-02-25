package com.edu_netcracker.cmp.configs;

import com.edu_netcracker.cmp.entities.Attributes;
import com.edu_netcracker.cmp.entities.STAID;
import com.edu_netcracker.cmp.entities.Students;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.jpa.AttributesJPA;
import com.edu_netcracker.cmp.entities.jpa.STAJPA;
import com.edu_netcracker.cmp.entities.jpa.StudentsJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseFill {
    @Autowired
    private AttributesJPA attributesJPA;
    @Autowired
    private STAJPA stajpa;
    @Autowired
    private StudentsJpa studentsJpa;


    @Bean
     void fillDB(){
        Attributes attributes1 = new Attributes();
        attributes1.setAttribute_name("tg_chat_id");
        Attributes attributes2 = new Attributes();
        attributes2.setAttribute_name("email");

        Students students = new Students();


        StudentsToAttributes studentsToAttributes = new StudentsToAttributes();
        studentsToAttributes.setStudent(students);
        studentsToAttributes.setAttributes(attributes1);
        studentsToAttributes.setChar_value("348260500");
        attributesJPA.save(attributes1);
        attributesJPA.save(attributes2);
        studentsJpa.save(students);
        stajpa.save(studentsToAttributes);
    }
}
