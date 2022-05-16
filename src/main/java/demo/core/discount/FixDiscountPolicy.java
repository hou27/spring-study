package demo.core.discount;

import demo.core.user.Grade;
import demo.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicy implements DiscountPolicy {
  private final int discountFixAmount = 1000;

  @Override
  public int discount(User user, int price) {
    if(user.getGrade() == Grade.VIP) {
      return discountFixAmount;
    }
    else return 0;
  }
}
