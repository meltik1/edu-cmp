package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.AbstractJpaDao;
import com.edu_netcracker.cmp.entities.TGUsersInfo;
import com.edu_netcracker.cmp.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Component
public class TgUsersInfoJPA  extends AbstractJpaDao<TGUsersInfo, Long> {

    public TgUsersInfoJPA() {
        super.setClazz(TGUsersInfo.class);
    }

    public Long findByUserName(String userName) {
            EntityManager entityManager = super.entityManager;
            TypedQuery<Long> query = entityManager.createQuery("select tg.chatId from TGUsersInfo tg where tg.userName = ?1", Long.class);
            Long singleResult = query.setParameter(1, userName).getResultList().stream().findFirst().orElse(null);
            return singleResult;
        }
}
