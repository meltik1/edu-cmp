package com.edu_netcracker.cmp.entities.users;

import com.edu_netcracker.cmp.entities.DTO.UserDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @Column(name = "user_name")
    private String userName;

    private String FIO;

    private Role role;

    private String password;

    public UserDTO toUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(this.userName);
        userDTO.setFIO(this.FIO);
        return userDTO;
    }
}
