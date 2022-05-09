package demo.core.beanfind;

import static org.junit.jupiter.api.Assertions.*;

import demo.core.config.AppConfig;
import demo.core.user.UserService;
import demo.core.user.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextBasicFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("Find by Bean name")
  void findBeanByName() {
    UserService userService = ac.getBean("userService", UserService.class);
    Assertions.assertThat(userService).isInstanceOf(UserServiceImpl.class);
  }

  @Test
  @DisplayName("Find by Bean type without name")
  void findBeanByType() {
    UserService userService = ac.getBean(UserService.class);
    Assertions.assertThat(userService).isInstanceOf(UserServiceImpl.class);
  }

  @Test
  @DisplayName("Find Bean by real type")
  void findBeanByRealType() {
    // 가능하긴 하지만 구현에 의존하는 것은 좋지 않다.
    UserService userService = ac.getBean("userService", UserServiceImpl.class);
    Assertions.assertThat(userService).isInstanceOf(UserServiceImpl.class);
  }

  @Test
  @DisplayName("Fail to find Bean by name")
  void findBeanByNameFail() {
//    UserService xxblah = ac.getBean("Xxblah", UserService.class);
    assertThrows(NoSuchBeanDefinitionException.class,
        () -> ac.getBean("Xxblah", UserService.class));
  }

}
