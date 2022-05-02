package prac.prac_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import prac.prac_spring.service.UserService;

@Controller
public class UserController {
  private final UserService userService;

  @Autowired // spring 이 연관된 객체를 찾아 주입해준다.(DI)
  public UserController(UserService userService) {
    this.userService = userService;
  }
}
