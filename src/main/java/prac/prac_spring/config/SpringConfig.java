package prac.prac_spring.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prac.prac_spring.repository.JdbcTemplateUserRepository;
import prac.prac_spring.repository.JdbcUserRepository;
import prac.prac_spring.repository.JpaUserRepository;
import prac.prac_spring.repository.MemoryUserRepository;
import prac.prac_spring.repository.UserRepository;
import prac.prac_spring.service.UserService;

@Configuration
public class SpringConfig {
//  @PersistenceContext // Spring에서는 안해줘도 됨.
  private final EntityManager em;
  private final DataSource dataSource;


  @Autowired
  public SpringConfig(EntityManager em, DataSource dataSource) {
    this.em = em;
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
//    return new JdbcTemplateUserRepository(dataSource);
    return new JpaUserRepository(em);
  }
}
