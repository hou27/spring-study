package demo.core.config;

import demo.core.discount.DiscountPolicy;
import demo.core.discount.RateDiscountPolicy;
import demo.core.order.OrderService;
import demo.core.order.OrderServiceImpl;
import demo.core.user.MemoryUserRepository;
import demo.core.user.UserRepository;
import demo.core.user.UserService;
import demo.core.user.UserServiceImpl;

public class AppConfig {
  public UserService userService() {
    return new UserServiceImpl(userRepository());
  }

  public OrderService orderService() {
    return new OrderServiceImpl(userRepository(), discountPolicy());
  }

  public UserRepository userRepository() {
    return new MemoryUserRepository();
  }
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }
}
