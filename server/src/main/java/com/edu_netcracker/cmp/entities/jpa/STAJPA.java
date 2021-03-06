package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface STAJPA extends JpaRepository<StudentsToAttributes, Long> {

    @Query("SELECT st from StudentsToAttributes st join st.attributes a join st.student s " +
            "WHERE st.student.id = ?1 and a.attribute_name = ?2 and st.char_value is not null")
    List<StudentsToAttributes> findStudentAttributeValue(Long studentId, String attribute_name);
}
