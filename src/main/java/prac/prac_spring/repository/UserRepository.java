package prac.prac_spring.repository;

import java.util.List;
import java.util.Optional;
import prac.prac_spring.domain.User;

public interface UserRepository {
  User save(User user);
  Optional<User> findById(Long id);
  Optional<User> findByName(String name);
  List<User> findAll();
}
