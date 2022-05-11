package demo.core.component;

import static org.assertj.core.api.Assertions.assertThat;

import demo.core.config.AutoAppConfig;
import demo.core.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {
  @Test
  void basicScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

    UserService userService = ac.getBean(UserService.class);

    assertThat(userService).isInstanceOf(UserService.class);
  }

}
