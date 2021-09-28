package com.edu_netcracker.cmp.entities.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "students")
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @Column(name = "user_name")
    private String userName;

    private String FIO;

    private Role role;

    private String password;
}
