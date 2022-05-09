package demo.core.order;

import demo.core.config.AppConfig;
import demo.core.user.Grade;
import demo.core.user.User;
import demo.core.user.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

  public static void main(String[] args) {
//    AppConfig appConfig = new AppConfig();
//    UserService userService = appConfig.userService();
//    OrderService orderService = appConfig.orderService();

    // Spring 컨테이너(ApplicationContext) 적용
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    UserService userService = ac.getBean("userService", UserService.class);
    OrderService orderService = ac.getBean("orderService", OrderService.class);
    
    long userId = 1L;
    User user = new User(userId, "userA", Grade.VIP);
    userService.signUp(user);
    Order order = orderService.createOrder(userId, "productA", 20000);
    System.out.println("order = " + order);
  }
}