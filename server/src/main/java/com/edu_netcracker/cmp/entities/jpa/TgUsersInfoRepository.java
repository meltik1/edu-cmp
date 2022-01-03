package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.TGUsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface TgUsersInfoRepository extends JpaRepository<TGUsersInfo, Long> {

    TGUsersInfo findByUserName(String userName);
}
