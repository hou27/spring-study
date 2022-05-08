package demo.core.order;

import demo.core.config.AppConfig;
import demo.core.user.Grade;
import demo.core.user.User;
import demo.core.user.UserService;
import demo.core.user.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
  UserService userService;
  OrderService orderService;
  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig();
    userService = appConfig.userService();
    orderService = appConfig.orderService();
  }
//  UserService userService = new UserServiceImpl();
//  OrderService orderService = new OrderServiceImpl();

  @Test
  void createOrder() {
    Long userId = 1L;
    User user = new User(userId, "test1", Grade.VIP);
    userService.signUp(user);

    Order order = orderService.createOrder(userId, "productA", 10000);
    Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
  }
}
