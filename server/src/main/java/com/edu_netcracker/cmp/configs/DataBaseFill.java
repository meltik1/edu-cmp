package com.edu_netcracker.cmp.configs;

import com.edu_netcracker.cmp.entities.Contacts;
import com.edu_netcracker.cmp.entities.Students;
import com.edu_netcracker.cmp.entities.jpa.ContactJpa;
import com.edu_netcracker.cmp.entities.jpa.StudentsJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseFill {

    @Autowired
    private ContactJpa contactJpa;
    @Autowired
    private StudentsJpa studentsJpa;


    @Bean
    public void fill_db() {
        Students students = new Students();
        students.setFirstName("Nikita");
        students.setLastName("Zuev");

        Contacts contacts = new Contacts();
        contacts.setEmail("Niki10222@bk.ru");
        contacts.setPhone_number("89096452582");
        contacts.setTg_chat_id("348260500");

        students.setContact_fk(contacts);
        contacts.setStudents_fk(students);

        studentsJpa.save(students);
        contactJpa.save(contacts);


    }
}
