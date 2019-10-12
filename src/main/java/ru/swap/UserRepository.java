package ru.swap;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.swap.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

}
