package demo.core.user;

import demo.core.user.Grade;
import demo.core.user.User;
import demo.core.user.UserService;
import demo.core.user.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
  UserService userService = new UserServiceImpl();
  @Test
  void signUp() {
    // given
    User test1 = new User(1L, "test1", Grade.VIP);
    // when
    userService.signUp(test1);
    User findUser = userService.findUser(test1.getId());
    // then
    Assertions.assertThat(test1).isEqualTo(findUser);
  }

}
