package demo.core.user;

import demo.core.config.AppConfig;

public class UserApp {

  public static void main(String[] args) {
    AppConfig appConfig = new AppConfig();
    UserService userService = appConfig.userService(); // 설정을 통해 제공받는다.

    User userA = new User(1L, "userA", Grade.VIP);
    userService.signUp(userA);

    User user = userService.findUser(1L);
    System.out.println("new user : " + userA.getName());
    System.out.println("found user = " + user.getName());
  }

}
