package ru.swap.bean;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Size(min = 5, max = 20)
    @Column(name = "username")
    private String username;

    @NotEmpty
    @Size(min=5, max=20)
    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private boolean enabled;

}
