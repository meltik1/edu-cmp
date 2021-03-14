package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.TGUsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface TgUsersInfoJPA extends JpaRepository<TGUsersInfo, Long> {
        @Query("SELECT tg  FROM TGUsersInfo tg " +
            "WHERE tg.userName = ?1")
    TGUsersInfo findByUserName(String userName);
}
