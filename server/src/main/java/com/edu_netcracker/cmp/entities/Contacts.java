package com.edu_netcracker.cmp.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "contacts")
@NoArgsConstructor
public class Contacts {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "contact_fk")
    private Students students_fk;

    private String phone_number;
    private String email;
    private String tg_chat_id;
}
