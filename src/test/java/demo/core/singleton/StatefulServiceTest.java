package demo.core.singleton;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {
  @Test
  void statefulServiceSingleton() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    StatefulService statefulService1 = ac.getBean(StatefulService.class);
    StatefulService statefulService2 = ac.getBean(StatefulService.class);

    // Thread 1
    statefulService1.order("testUser1", 2000);
    // Thread 2
    statefulService1.order("testUser2", 3000);

    // Thread 1
    // Check order price
    int price1 = statefulService1.getPrice();
    System.out.println("price1 = " + price1); // 문제 발생한 것을 알 수 있다.

    assertThat(price1).isNotEqualTo(2000);
  }

  static class TestConfig {
    @Bean
    public StatefulService statefulService() {
      return new StatefulService();
    }
  }

}