package prac.prac_spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prac.prac_spring.repository.MemoryUserRepository;
import prac.prac_spring.repository.UserRepository;
import prac.prac_spring.service.UserService;

@Configuration
public class SpringConfig {

  @Bean
  public UserService userService() {
    return new UserService(userRepository());
  }

  @Bean
  public UserRepository userRepository() {
    return new MemoryUserRepository();
  }
}
