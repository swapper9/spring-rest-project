package ru.swap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.swap.entity.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;


  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${hostname}")
  private String hostname;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return (UserDetails) user;
  }

  public boolean addUser(User user) {
    User userFromDb = userRepository.findByUsername(user.getUsername());

    if (userFromDb != null) {
      return false;
    }

    user.setActive(true);
    user.setRoles(Collections.singleton(User.Role.USER));
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    userRepository.save(user);

    return true;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public void saveUser(User user, String username, Map<String, String> form) {
    user.setUsername(username);


    userRepository.save(user);
  }

  public void updateProfile(User user, String password, String email) {
    String userEmail = user.getEmail();
    boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
      (userEmail != null && !userEmail.equals(email));
    if (isEmailChanged) {
      user.setEmail(email);

    }

    if (!StringUtils.isEmpty(password)) {
      user.setPassword(password);
    }
    userRepository.save(user);
  }
}
