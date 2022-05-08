package demo.core.discount;

import demo.core.user.User;

public interface DiscountPolicy {
  int discount(User user, int price);

}
