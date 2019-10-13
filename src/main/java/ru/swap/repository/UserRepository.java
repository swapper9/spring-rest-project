package ru.swap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.swap.bean.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

}
