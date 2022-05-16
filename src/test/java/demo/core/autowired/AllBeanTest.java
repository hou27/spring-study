package demo.core.autowired;

import static org.assertj.core.api.Assertions.assertThat;

import demo.core.config.AutoAppConfig;
import demo.core.discount.DiscountPolicy;
import demo.core.user.Grade;
import demo.core.user.User;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AllBeanTest {
  @Test
  void findAllBean() {
    // Spring 컨테이너를 생성할 때 넘겨주는 class 정보를 2개 넘겨 그 클래스들을 Bean으로 생성.
    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
    // map 키에 모든 DiscountPolicy를 주입받은 DiscountService를 가져옴
    DiscountService discountService = ac.getBean(DiscountService.class);
    User user = new User(1L, "userA", Grade.VIP);
    // discount 메서드는 discountCode로 map에서 해당 Spring Bean을 찾아 실행함.
    int discountPrice = discountService.discount(user, 10000, "fixDiscountPolicy");
    assertThat(discountService).isInstanceOf(DiscountService.class);
    assertThat(discountPrice).isEqualTo(1000);
  }
  static class DiscountService {
    private final Map<String, DiscountPolicy> policyMap;
    private final List<DiscountPolicy> policies;

    public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
      this.policyMap = policyMap;
      this.policies = policies;
      System.out.println("policyMap = " + policyMap);
      System.out.println("policies = " + policies);
    }
    public int discount(User user, int price, String discountCode) {
      // Map에서 Spring Bean을 가져옴
      DiscountPolicy discountPolicy = policyMap.get(discountCode);
      System.out.println("discountCode = " + discountCode);
      System.out.println("discountPolicy = " + discountPolicy);
      return discountPolicy.discount(user, price);
    }
  }
}

/**
 * 로직 분석
 * DiscountService는 Map으로 모든 DiscountPolicy 를 주입받는다. 이때 fixDiscountPolicy ,
 * rateDiscountPolicy 가 주입된다.
 * discount () 메서드는 discountCode로 "fixDiscountPolicy"가 넘어오면 map에서
 * fixDiscountPolicy 스프링 빈을 찾아서 실행한다. 물론 “rateDiscountPolicy”가 넘어오면
 * rateDiscountPolicy 스프링 빈을 찾아서 실행한다.
 *
 * 주입 분석
 * Map<String, DiscountPolicy> : map의 키에 스프링 빈의 이름을 넣어주고, 그 값으로
 * DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
 * List<DiscountPolicy> : DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
 * 만약 해당하는 타입의 스프링 빈이 없으면, 빈 컬렉션이나 Map을 주입한다
 */
