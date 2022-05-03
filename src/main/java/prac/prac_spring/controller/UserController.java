package prac.prac_spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import prac.prac_spring.domain.User;
import prac.prac_spring.dtos.UserDtos.SignUpFrom;
import prac.prac_spring.service.UserService;

@Controller
public class UserController {
  private final UserService userService;

  @Autowired // spring 이 연관된 객체를 찾아 주입해준다.(DI)
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users/signUp")
  public String signUp() {
    return "users/signUp";
  }

  @PostMapping("/users/signUp")
  public String createUser(SignUpFrom form) {
    User user = new User();
    System.out.println(form.getName());
    user.setName(form.getName());

    userService.signUp(user);

    return "redirect:/";
  }

  @GetMapping("/users")
  public String showUserList(Model model) {
    List<User> userList = userService.findAllUsers();
    /**
     * model에 user 객체 list에 대한 정보를 담는다.
     * 이를 통해 private 요소에 접근이 가능해진다.(getter와 setter가 정의되어있을 시)
     */
    model.addAttribute("userList", userList);

    return "users/userList";
  }
}
