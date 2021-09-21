package com.edu_netcracker.cmp.entities.users;

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
public class User {

    @Id
    private String userName;

    private String FIO;

    private Role role;

    private String password;
}
