package com.edu_netcracker.cmp.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@Table(name = "students")
@NoArgsConstructor
public class Students {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Session session;

    private String email;
    private String tg_nick_name;

}
