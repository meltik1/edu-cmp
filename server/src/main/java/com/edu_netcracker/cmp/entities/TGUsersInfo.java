package com.edu_netcracker.cmp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "tgusers_info")
public class TGUsersInfo implements Serializable {
    @Id
    @Column(name = "chat_id")
    Long chatId;

    @Column(name = "user_name")
    String userName;
}
