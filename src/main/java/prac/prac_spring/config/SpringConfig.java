package prac.prac_spring.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prac.prac_spring.repository.JdbcTemplateUserRepository;
import prac.prac_spring.repository.JdbcUserRepository;
import prac.prac_spring.repository.MemoryUserRepository;
import prac.prac_spring.repository.UserRepository;
import prac.prac_spring.service.UserService;

@Configuration
public class SpringConfig {
//  @Autowired DataSource dataSource;
  private DataSource dataSource;

  /**
   * spring이 application.properties(설정 파일)를 보고
   * 자체적으로 Bean을 생성해준다. 그 후 DI를 진행한다.
   * @param dataSource
   */
  @Autowired
  public SpringConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public UserService userService() {
    return new UserService(userRepository());
  }

  @Bean
  public UserRepository userRepository() {
//    return new MemoryUserRepository();
    // 간단하게 설정 파일만 변경(다른 어떤 코드도 손대지 않음)
//    return new JdbcUserRepository(dataSource);  // spring이 생성해준 dataSource를 주입
    return new JdbcTemplateUserRepository(dataSource);
  }
}
