package prac.prac_spring.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prac.prac_spring.aop.TimeTraceAop;
import prac.prac_spring.repository.JdbcTemplateUserRepository;
import prac.prac_spring.repository.JdbcUserRepository;
import prac.prac_spring.repository.JpaUserRepository;
import prac.prac_spring.repository.MemoryUserRepository;
import prac.prac_spring.repository.UserRepository;
import prac.prac_spring.service.UserService;

@Configuration
public class SpringConfig {
  private final UserRepository userRepository;

  /**
   * Spring Data JPA가 알아서 Bean에 등록해둔 것을 알아서 DI
   */
  @Autowired // 생성자가 하나일 때 생략해줘도 되긴 함.
  public SpringConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public UserService userService() {
//    return new UserService(userRepository());
    return new UserService(userRepository);
  }

  /*
  // AOP가 쓰이는 것을 알리기 위해 Spring Bean에 직접 등록하려다 현재 Component Scan 사용
  @Bean
  public TimeTraceAop timeTraceAop() {
    return new TimeTraceAop();
  }
  */
}
