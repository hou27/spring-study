package demo.core.order;

import demo.core.config.AppConfig;
import demo.core.user.Grade;
import demo.core.user.User;
import demo.core.user.UserService;

public class OrderApp {

  public static void main(String[] args) {
    AppConfig appConfig = new AppConfig();
    UserService userService = appConfig.userService();
    OrderService orderService = appConfig.orderService();
    long userId = 1L;
    User user = new User(userId, "userA", Grade.VIP);
    userService.signUp(user);
    Order order = orderService.createOrder(userId, "productA", 20000);
    System.out.println("order = " + order);
  }
}