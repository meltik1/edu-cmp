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

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String LastName;
    private String middleName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Contacts contact_fk;

}
