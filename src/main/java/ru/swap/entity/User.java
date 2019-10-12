package ru.swap.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

  public enum Role {
    USER,
    ADMIN
  }

  private Long id;
  private String username;
  private String email;
  private String password;
  private Set<Role> roles;
  private boolean active;

}
