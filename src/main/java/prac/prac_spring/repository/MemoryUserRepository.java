package prac.prac_spring.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import prac.prac_spring.domain.User;

//@Repository // spring 이 동작할 때 spring container 에 등록한다.
public class MemoryUserRepository implements UserRepository {
  private static Map<Long, User> store = new HashMap<>();
  private static long sequence = 0L;

  @Override
  public User save(User user) {
    user.setId(++sequence);
    store.put(user.getId(), user);

    return user;
  }

  @Override
  public Optional<User> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public Optional<User> findByName(String name) {
    return store.values().stream()
        .filter(user -> user.getName().equals(name))
        .findAny();
  }

  @Override
  public List<User> findAll() {
    return new ArrayList<>(store.values());
  }

  // Method for clear store
  public void clearStore() {
    store.clear();
  }
}
