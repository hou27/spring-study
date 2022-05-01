package prac.prac_spring.repository;

import static org.assertj.core.api.Assertions.*; // static import

//import org.assertj.core.api.Assertions;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import prac.prac_spring.domain.User;

class MemoryUserRepositoryTest {
  MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

  /**
   * 모든 Test는 순서가 보장되지 않는다.
   *
   * 그렇기 때문에 정확한 Test를 위해서 하나의 Test가 끝난 후
   * repository를 깔끔하게 정리하는 메소드가 필요하다.
   */
  @AfterEach
  public void afterEach() {
    memoryUserRepository.clearStore();
  }

  @Test
  public void save() {
    User user = new User();
    user.setName("testuser");

    memoryUserRepository.save(user);

    User result = memoryUserRepository.findById(user.getId()).get();
    System.out.println("result = " + (result == user));
//    Assertions.assertEquals(user, result);
//    assertThat(result).isEqualTo(null);
    assertThat(result).isEqualTo(user);
  }

  @Test
  public void findByName() {

    User user1 = new User();
    user1.setName("test1");
    memoryUserRepository.save(user1);

    User user2 = new User();
    user2.setName("test2");
    memoryUserRepository.save(user2);

    User result = memoryUserRepository.findByName("test1").get();
    assertThat(result).isEqualTo(user1);
  }

  @Test
  public void findAll() {
    //given
    User user1 = new User();
    user1.setName("test1");
    memoryUserRepository.save(user1);
    User user2 = new User();
    user2.setName("test2");
    memoryUserRepository.save(user2);

    //when
    List<User> result = memoryUserRepository.findAll();

    //then
    assertThat(result.size()).isEqualTo(2);
  }

}
