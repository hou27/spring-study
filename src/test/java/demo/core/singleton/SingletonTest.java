package demo.core.singleton;

import static org.assertj.core.api.Assertions.assertThat;

import demo.core.config.AppConfig;
import demo.core.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {
  @Test
  @DisplayName("Pure DI Container without Spring")
  void pureContainer() {
    AppConfig appConfig = new AppConfig();

    // 호출 시마다 객체 생성됨.
    UserService userService1 = appConfig.userService();
    UserService userService2 = appConfig.userService();

    // 참조값이 다른 것 확인
    System.out.println("userService1 = " + userService1);
    System.out.println("userService2 = " + userService2);

//    assertThat(userService1).isNotEqualTo(userService2);
    assertThat(userService1).isNotSameAs(userService2); // SameAs 는 == 연산을 사용한다.
  }

  @Test
  @DisplayName("Use Object with Singleton Pattern")
  public void singletonServiceTest() {
    //private으로 생성자를 막아두어 컴파일 오류가 발생한다.
    //new SingletonService();

    // 호출할 때 마다 같은 객체를 반환
    SingletonService singletonService1 = SingletonService.getInstance();
    SingletonService singletonService2 = SingletonService.getInstance();

    //참조값이 같은 것을 확인
    System.out.println("singletonService1 = " + singletonService1);
    System.out.println("singletonService2 = " + singletonService2);

    // Check singletonService1 == singletonService2
    assertThat(singletonService1).isSameAs(singletonService2);
    singletonService1.logic();
  }


}
