package demo.core.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import demo.core.config.AppConfig;
import demo.core.order.OrderServiceImpl;
import demo.core.user.UserRepository;
import demo.core.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {
  @Test
  void configurationTest() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    UserServiceImpl userService = ac.getBean("userService", UserServiceImpl.class);
    OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

    // Test용으로 추가한 메서드를 통해 각각에서 주입된 UserRepository를 확인한다.
    UserRepository userRepository1 = userService.getUserRepository();
    UserRepository userRepository2 = orderService.getUserRepository();
    System.out.println("userService ::: userRepository1 = " + userRepository1);
    System.out.println("orderService ::: userRepository2 = " + userRepository2);

    UserRepository userRepository = ac.getBean("userRepository", UserRepository.class);
    System.out.println("Bean ::: userRepository = " + userRepository);

    assertThat(userService.getUserRepository()).isSameAs(userRepository);
    assertThat(orderService.getUserRepository()).isSameAs(userRepository);
  }
}
