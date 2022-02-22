package com.edu_netcracker.cmp.entities.repositories;

import com.edu_netcracker.cmp.entities.Attributes;
import com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO;
import com.edu_netcracker.cmp.entities.STAID;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
@Component
@Repository
public interface STARepository extends JpaRepository<StudentsToAttributes, STAID> {

    @Query(value = "select new com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO(a.attributeName, sa.intValue, sa.charValue, sa.dateValue)  from StudentsToAttributes sa " +
            "join Attributes a on sa.attributes.id = a.id join User s on sa.student.userName = s.userName " +
            "where s.userName = ?1")
    List<StudentsAttributesDTO> findStudentAttributeValue(String studentId);

    @Query(value = "select new com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO(a.attributeName, sa.intValue, sa.charValue, sa.dateValue) from StudentsToAttributes  sa " +
            "join Attributes a on sa.attributes.id = a.id join User s on sa.student.userName = s.userName " +
            "where s.userName = ?1 and a.isCommunicationSource=true and sa.charValue is not null ")
    List<StudentsAttributesDTO> findDataSources(String userId);

    StudentsToAttributes findStudentsToAttributesByAttributesAndStudent(Attributes attribute, User user);
}
