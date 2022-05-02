package prac.prac_spring.service;

import static org.junit.jupiter.api.Assertions.*; // 이건 JUnit 꺼 여기에 assertEquals
import static org.assertj.core.api.Assertions.*; // 여기에 assertThat 이 있음

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prac.prac_spring.domain.User;
import prac.prac_spring.repository.MemoryUserRepository;

class UserServiceTest {
  MemoryUserRepository users = new MemoryUserRepository();
  UserService userService = new UserService();

  @AfterEach
  public void afterEach() {
    users.clearStore();
  }

  @Test
  void signUp() {
    //given
    User user = new User();
    user.setName("test");
    //When
    Long userId = userService.signUp(user);

    //Then
    User findUser = users.findById(userId).get();
    assertEquals(user.getName(), findUser.getName());
  }

  @Test
  void checkOverlap() {
    //Given
    User user1 = new User();
    user1.setName("spring");
    User user2 = new User();
    user2.setName("spring");
    //When
    userService.signUp(user1);
    IllegalStateException e = assertThrows(IllegalStateException.class,
        () -> userService.signUp(user2));// 예외가 발생해야 한다.
    System.out.println(e.getMessage());
    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
  }

  @Test
  void findAllUsers() {
  }

  @Test
  void findUserById() {
  }

  @Test
  void findUserByName() {
  }
}