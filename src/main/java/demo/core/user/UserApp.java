package demo.core.user;

public class UserApp {

  public static void main(String[] args) {
    UserService userService = new UserServiceImpl();

    User userA = new User(1L, "userA", Grade.VIP);
    userService.signUp(userA);

    User user = userService.findUser(1L);
    System.out.println("new user : " + userA.getName());
    System.out.println("found user = " + user.getName());
  }

}
