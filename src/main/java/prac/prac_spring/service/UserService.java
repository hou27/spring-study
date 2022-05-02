package prac.prac_spring.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prac.prac_spring.domain.User;
import prac.prac_spring.repository.MemoryUserRepository;
import prac.prac_spring.repository.UserRepository;

@Service // spring 이 동작할 때 spring container 에 등록한다.
public class UserService {

  /**
   * DI 가 가능하도록 코드를 변경
   */
  private final UserRepository users;

  @Autowired // spring 이 동작할 때 이 service 를 등록하면서 spring container 에 있는 UserRepository 를 주입
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
