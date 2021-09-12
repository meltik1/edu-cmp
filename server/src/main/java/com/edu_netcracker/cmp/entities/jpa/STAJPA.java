package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface STAJPA extends JpaRepository<StudentsToAttributes, Long> {

    @Query("SELECT st from StudentsToAttributes st join st.attributes a join st.student s " +
            "WHERE st.student.id = ?1 and a.attributeName = ?2 and st.charValue is not null")
    List<StudentsToAttributes> findStudentAttributeValue(Long studentId, String attribute_name);

}
