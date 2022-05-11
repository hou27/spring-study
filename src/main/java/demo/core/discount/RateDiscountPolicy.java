package demo.core.discount;

import demo.core.user.Grade;
import demo.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy {
  private int discountPercent = 10;


  @Override
  public int discount(User user, int price) {
    if(user.getGrade() == Grade.VIP) {
      return price * discountPercent / 100;
    }
    else return 0;
  }
}
