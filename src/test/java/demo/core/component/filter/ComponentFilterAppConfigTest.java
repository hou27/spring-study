package demo.core.component.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

public class ComponentFilterAppConfigTest {

  /**
   * Filter Test
   */

  @Test
  void filterScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
    BeanA beanA = ac.getBean("beanA", BeanA.class);
    assertThat(beanA).isNotNull();
    assertThrows(NoSuchBeanDefinitionException.class,
        () -> ac.getBean("beanB", BeanB.class));
  }
  @Configuration
  @ComponentScan(
      includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = IncludeComponent.class),
      excludeFilters = @ComponentScan.Filter(classes = ExcludeComponent.class) // type = FilterType.ANNOTATION, <- 기본값이라 생략가능
  )
  static class ComponentFilterAppConfig {
  }
}
