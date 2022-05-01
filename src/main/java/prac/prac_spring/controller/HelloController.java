package prac.prac_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

  @GetMapping("prac") // Get Method
  public String hello(Model model) { // Model from MVC
    model.addAttribute("data","hi~~~");

    return "hello"; // return html(templates/hello.html)
    // Controller에서 return value로 문자를 반환하면 viewResolver가 화면을 찾아서 처리하게 된다.
    // spring boot template 기본 viewName Mapping
    // resources:templates/ +{ViewName}+ .html
  }

  @GetMapping("prac-template")
  public String PracTemplate(@RequestParam(value = "value", required = false) String value, Model model) {
    model.addAttribute("value", value);

    return "prac-template";
  }

  @GetMapping("prac-response-body")
  @ResponseBody // HTTP의 Body에 반환값을 넘기겠다는 뜻. (기본적으로 json 으로 반환하게 되어있다.)
  /**
   * @ResponseBody
   *
   * HTTP 의 BODY 에 문자 내용을 직접 반환한다.
   * viewResolver 가 아닌 HttpMessageConverter 가 동작한다.
   * 기본 문자처리: StringHttpMessageConverter
   * 기본 객체처리: MappingJackson2HttpMessageConverter
   * byte 처리 등등 각 상황에 따라 기타 여러 HttpMessageConverter 가 기본으로 등록되어 있다.
   *
   * 클라이언트의 HTTP Accept Header 와 서버의 Controller return type 정보들을 조합해서
   * HttpMessageConverter 가 선택된다.
   */
  public ReturnObject PracResponseBodyApi(@RequestParam(value = "value", required = false) String value) {
    ReturnObject returnObject = new ReturnObject();
    returnObject.setReturnObject(value);

    return returnObject;
  }

  static class ReturnObject {
    private String returnObject;

    // getter
    public String getReturnObject() {
      return returnObject;
    }

    // setter
    public void setReturnObject(String returnObject) {
      this.returnObject = returnObject;
    }
  }
}
