package demo.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

  /**
   * 스프링 빈의 Event LifeCycle
   *
   * 1. 스프링 컨테이너 생성
   * 2. 스프링 빈 생성
   * 3. 의존관계 주입
   * 4. 초기화 콜백
   * 5. 사용
   * 6. 소멸전 콜백
   * 7. 스프링 종료
   */
  
  @Test
  public void lifeCycleTest() {
    ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
    NetworkClient client = ac.getBean(NetworkClient.class);
    ac.close(); //스프링 컨테이너를 종료
    // close 메서드는 ConfigurableApplicationContext에 존재
  }

  @Configuration
  static class LifeCycleConfig {
    @Bean
    public NetworkClient networkClient() {
      // 객체의 생성과 초기화는 분리하는 것이 좋다.
      NetworkClient networkClient = new NetworkClient();
      networkClient.setUrl("http://test-spring.dev");

      return networkClient;
    }
  }
}
/**
 * 스프링의 빈 생명주기 콜백 지원 방식 3가지
 * 1. 인터페이스(InitializingBean, DisposableBean)
 * 2. 설정 정보에 초기화 메서드, 종료 메서드 지정
 * 3. @PostConstruct, @PreDestroy 애노테이션 지원
 */