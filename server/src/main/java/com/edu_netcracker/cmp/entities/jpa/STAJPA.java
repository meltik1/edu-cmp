package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.AbstractJpaDao;
import com.edu_netcracker.cmp.entities.Attributes;
import com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO;
import com.edu_netcracker.cmp.entities.StudentsToAttributes;
import com.edu_netcracker.cmp.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
@Component
public class STAJPA extends AbstractJpaDao<StudentsToAttributes, String> {

    public STAJPA() {
        super.setClazz(StudentsToAttributes.class);
    }

    public List<StudentsAttributesDTO> findStudentAttributeValue(String studentId) {
        EntityManager entityManager = super.entityManager;
        TypedQuery<StudentsAttributesDTO> query = entityManager.createQuery("select new com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO(a.attributeName, sa.intValue, sa.charValue, sa.dateValue)  from StudentsToAttributes sa " +
                "join Attributes a on sa.attributes = a.id join User s on sa.student = s.userName " +
                "where s.userName = ?1", StudentsAttributesDTO.class);
        List<StudentsAttributesDTO> resultList = query.setParameter(1, studentId).getResultList();
        return resultList;
    }


    public List<StudentsAttributesDTO> findDataSources(String userId) {
        EntityManager entityManager = super.entityManager;
        TypedQuery<StudentsAttributesDTO> query = entityManager.createQuery("select new com.edu_netcracker.cmp.entities.DTO.StudentsAttributesDTO(a.attributeName, sa.intValue, sa.charValue, sa.dateValue) from StudentsToAttributes  sa " +
                "join Attributes a on sa.attributes = a.id join User s on sa.student = s.userName " +
                "where s.userName = ?1 and a.isCommunicationSource=true and sa.charValue is not null ", StudentsAttributesDTO.class);
        List<StudentsAttributesDTO> resultList = query.setParameter(1, userId).getResultList();
        return resultList;
    }

    public StudentsToAttributes findStudentsToAttributesByAttributesAndAndStudent(Attributes attribute, User user) {
        EntityManager entityManager = super.entityManager;
        TypedQuery<StudentsToAttributes> query = entityManager.createQuery("select sa from StudentsToAttributes  sa " +
                "join Attributes a on sa.attributes = a.id join User s on sa.student = s.userName " +
                "where s.userName = ?1 and a.id = ?2 ", StudentsToAttributes.class);

        query.setParameter(1, user.getUserName());
        query.setParameter(2, attribute.getId());
        StudentsToAttributes singleResult = query.getResultList().stream().findFirst().orElse(null);
        return singleResult;
    }

}
