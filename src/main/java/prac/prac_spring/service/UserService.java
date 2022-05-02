package prac.prac_spring.service;

import java.util.List;
import java.util.Optional;
import prac.prac_spring.domain.User;
import prac.prac_spring.repository.MemoryUserRepository;
import prac.prac_spring.repository.UserRepository;

public class UserService {

  /**
   * DI 가 가능하도록 코드를 변경
   */
  private final UserRepository users;

  public UserService(UserRepository users) {
    this.users = users;
  }

  /**
   * Sign Up
   */
  public Long signUp(User user) {
    try {
      // Check User exists
      checkUserExists(user);
      // Save user
      User savedUser = users.save(user);

      return savedUser.getId();
    } catch (Error e) {
      System.out.println(e);
      throw new IllegalStateException("회원 생성 중 오류가 발생했습니다.");
    }
  }

  private void checkUserExists(User user) {
    Optional<User> exists = users.findByName(user.getName());

    exists.ifPresent(item -> {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    });
  }

  /**
   * Find All Users
   */
  public List<User> findAllUsers() {
    return users.findAll();
  }

  /**
   * @param userId
   * @return Optional<User>
   */
  public Optional<User> findUserById(Long userId) {
    return users.findById(userId);
  }

  /**
   * @param userName
   * @return Optional<User>
   */
  public Optional<User> findUserByName(String userName) {
    return users.findByName(userName);
  }

}
