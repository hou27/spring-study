package demo.core.user;

import demo.core.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserApp {

  public static void main(String[] args) {
//    AppConfig appConfig = new AppConfig();
//    UserService userService = appConfig.userService(); // 설정을 통해 제공받는다.

    // Spring 컨테이너(ApplicationContext) 적용
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    UserService userService = ac.getBean("userService", UserService.class);

    User userA = new User(1L, "userA", Grade.VIP);
    userService.signUp(userA);

    User user = userService.findUser(1L);
    System.out.println("new user : " + userA.getName());
    System.out.println("found user = " + user.getName());
  }

}
