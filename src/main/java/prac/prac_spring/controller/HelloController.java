package prac.prac_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("hello") // Get Method
  public String hello(Model model) { // Model from MVC
    model.addAttribute("data","hi~~~");

    return "hello"; // return html(templates/hello.html)
    // Controller에서 return value로 문자를 반환하면 viewResolver가 화면을 찾아서 처리하게 된다.
    // spring boot template 기본 viewName Mapping
    // resources:templates/ +{ViewName}+ .html
  }
}
