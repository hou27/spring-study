package demo.core.discount;

import static org.junit.jupiter.api.Assertions.*;

import demo.core.user.Grade;
import demo.core.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RateDiscountPolicyTest {
  RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

  @Test
  @DisplayName("VIP는 10% discount")
  void vip_discount() {
    // given
    User user = new User(1L, "testVIP", Grade.VIP);
    // when
    int discount = rateDiscountPolicy.discount(user, 10000);
    // then
    assertEquals(1000, discount);
  }

  @Test
  @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다.")
  void vip_x() {
    //given
    User user = new User(2L, "testBASIC", Grade.BASIC);
    //when
    int discount = rateDiscountPolicy.discount(user, 10000);
    //then
    assertEquals(0, discount);
  }

  }