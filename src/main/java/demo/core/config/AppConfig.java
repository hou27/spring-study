package demo.core.config;

import demo.core.discount.DiscountPolicy;
import demo.core.discount.RateDiscountPolicy;
import demo.core.order.OrderService;
import demo.core.order.OrderServiceImpl;
import demo.core.user.MemoryUserRepository;
import demo.core.user.UserRepository;
import demo.core.user.UserService;
import demo.core.user.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean
  public UserService userService() {
    return new UserServiceImpl(userRepository());
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(userRepository(), discountPolicy());
  }

  @Bean
  public UserRepository userRepository() {
    return new MemoryUserRepository();
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }
}
