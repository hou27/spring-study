package prac.prac_spring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import prac.prac_spring.domain.User;
import prac.prac_spring.repository.MemoryUserRepository;
import prac.prac_spring.repository.UserRepository;

@SpringBootTest // Spring Container와 Test를 함께 실행한다.
@Transactional
/**
 * 테스트에서의 @Transactional은 각각의 테스트 시작 시 트랜잭션을 시작하고 테스트 종료 시 롤백하여
 * 동일한 테스트 환경을 갖출 수 있게 세팅한다.
 *
 * DB 연동 안되는 순수 자바 테스트의 경우엔,
 * @BeforeEach와 @AfterEach로 동일한 테스트 환경을 구성해야합니다.
 *
 * 그래서 이전에 @BeforeEach와 @AfterEach를 사용했던 것이다.
 */
class UserServiceIntegrationTest {
  // 기존 코드들은 생성자 코드가 좋지만 Test용 코드는 편한 방법을 사용하는게 좋다.
  // 그래서 필드 기반으로 Autowired해주었다.(구현체는 이제 config한 것에서 올라올 것이다.)
  @Autowired
  UserRepository users;
  @Autowired
  UserService userService;
  /**
   * new 로 새로운 instance 를 생성하게 되면 행여나 다른 부분이 생길 수 있다.
   * 다시 말하면 test 에 사용되는 instance 가 다르다는 것이다.
   *
   * service 에서 DI가 가능하도록 코드 변경 후 외부에서 사용될 객체를 결정해서 전달받도록 다시 코드를 작성
   */

//  @BeforeEach
//  public void beforeEach() {
//    users = new MemoryUserRepository();
//    userService = new UserService(users);
//  }

  // 이제 @Transactional 덕분에 아래의 것이 필요없다.
//  @AfterEach
//  public void afterEach() {
//    users.clearStore();
//  }

  @Test
  void signUp() {
    // given
    User user = new User();
    user.setName("test");
    // When
    Long userId = userService.signUp(user);

    // Then
    User findUser = users.findById(userId).get();
    assertEquals(user.getName(), findUser.getName());
  }

  @Test
  void checkOverlap() {
    // Given
    User user1 = new User();
    user1.setName("test");
    User user2 = new User();
    user2.setName("test");
    // When
    userService.signUp(user1);
    IllegalStateException e = assertThrows(IllegalStateException.class,
        () -> userService.signUp(user2));// 예외가 발생해야 한다.
    System.out.println(e.getMessage());
    // Then
    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
  }

  @Test
  void findAllUsers() {
    // Given
    List<User> prevUserList = users.findAll();
    int userListSize = prevUserList.size() + 2;
    User user1 = new User();
    user1.setName("test1");
    userService.signUp(user1);
    User user2 = new User();
    user2.setName("test2");
    userService.signUp(user2);

    // When
    List<User> userList = users.findAll();
//    System.out.println("userList.toArray()");
//    System.out.println(Arrays.toString(userList.toArray()));
    // Then
    assertEquals(userListSize, userList.size());
  }

//  @Test
//  void findUserById() {
//  }
//
//  @Test
//  void findUserByName() {
//  }
}