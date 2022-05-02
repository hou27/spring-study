package prac.prac_spring.service;

import static org.junit.jupiter.api.Assertions.*; // 이건 JUnit 꺼 여기에 assertEquals
import static org.assertj.core.api.Assertions.*; // 여기에 assertThat 이 있음

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import prac.prac_spring.domain.User;
import prac.prac_spring.repository.MemoryUserRepository;

class UserServiceTest {
  MemoryUserRepository users = new MemoryUserRepository();
  /**
   * new 로 새로운 instance 를 생성하게 되면 행여나 다른 부분이 생길 수 있다.
   * 다시 말하면 test 에 사용되는 instance 가 다르다는 것이다.
   *
   * service 에서 DI가 가능하도록 코드 변경 후 외부에서 사용될 객체를 결정해서 전달받도록 다시 코드를 작성
   */
  UserService userService = new UserService(users);

//  @BeforeEach
//  public void beforeEach() {
//    users = new MemoryUserRepository();
//    userService = new UserService(users);
//  }

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