package demo.core.beanfind;

import demo.core.config.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

  /**
   * ISP(Interface Segregation Principal) 인터페이스 분리 법칙 준수
   *
   * ApplicationContext에는 getBeanDefinition 메서드가 없고,
   * AnnotationConfigApplicationContext에 존재한다.
   */
  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("모든 Bean 확인")
  void findAllBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames ) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("beanDefinitionName = " + beanDefinitionName + "object = " + bean);
    }
  }

  @Test
  @DisplayName("Application Bean 확인") // 내가 등록한 Bean
  void findApplicationBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
      // Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
      // Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
      if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name=" + beanDefinitionName + " object=" + bean);
      }
    }
  }

}
