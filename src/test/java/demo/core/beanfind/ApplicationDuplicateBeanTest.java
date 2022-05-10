package demo.core.beanfind;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import demo.core.config.AppConfig;
import demo.core.discount.DiscountPolicy;
import demo.core.user.MemoryUserRepository;
import demo.core.user.UserRepository;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ApplicationDuplicateBeanTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

  @Test
  @DisplayName("Duplicate error occurs when more than one type exists when querying by type")
  void findBeanByTypeDuplicate() {
//    UserRepository bean = ac.getBean(UserRepository.class);
//    System.out.println("bean = " + bean);
    assertThrows(NoUniqueBeanDefinitionException.class, () ->
        ac.getBean(UserRepository.class));
  }

  @Test
  @DisplayName("Designate it as an Bean name when more than one type exists when querying by type")
  void findBeanByName() {
    UserRepository userRepository = ac.getBean("userRepository1", UserRepository.class);
    assertThat(userRepository).isInstanceOf(UserRepository.class);
  }

  @Test
  @DisplayName("Query All specific types")
  void findAllBeansByType() {
    Map<String, UserRepository> beans = ac.getBeansOfType(UserRepository.class);
    for(String key : beans.keySet()) {
      System.out.println("key = " + key + " value = " +
          beans.get(key));
    }
    System.out.println("beans that find by type = " + beans);
    assertThat(beans.size()).isEqualTo(2);
  }

  @Configuration
  static class SameBeanConfig {
    @Bean
    public UserRepository userRepository1() {
      return new MemoryUserRepository();
    }

    @Bean
    public UserRepository userRepository2() {
      return new MemoryUserRepository();
    }
  }

}
