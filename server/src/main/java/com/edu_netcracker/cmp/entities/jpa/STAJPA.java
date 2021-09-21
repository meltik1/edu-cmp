package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.Attributes;
import com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface STAJPA extends JpaRepository<StudentsToAttributes, String> {

    @Query(value = "select new com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO(a.attributeName, sa.intValue, sa.charValue, sa.dateValue)  from StudentsToAttributes sa " +
            "join Attributes a on sa.attributes = a.id join User s on sa.student = s.userName " +
            "where s.userName = ?1")
    List<StudentsAttributesDTO> findStudentAttributeValue(String studentId);

    @Query(value = "select new com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO(a.attributeName, sa.intValue, sa.charValue, sa.dateValue) from StudentsToAttributes  sa " +
            "join Attributes a on sa.attributes = a.id join User s on sa.student = s.userName " +
            "where s.userName = ?1 and a.isCommunicationSource=true and sa.charValue is not null ")
    List<StudentsAttributesDTO> findDataSources(String userId);

    StudentsToAttributes findStudentsToAttributesByAttributesAndAndStudent(Attributes attribute, User user);

}
