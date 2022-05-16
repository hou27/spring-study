package demo.core.order;

import demo.core.annotation.MainDiscountPolicy;
import demo.core.discount.DiscountPolicy;
import demo.core.discount.FixDiscountPolicy;
import demo.core.user.MemoryUserRepository;
import demo.core.user.User;
import demo.core.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {
  private final UserRepository userRepository;
  private final DiscountPolicy discountPolicy;

  @Autowired
  public OrderServiceImpl(UserRepository userRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
    this.userRepository = userRepository;
    this.discountPolicy = discountPolicy;
  }

  @Override
  public Order createOrder(Long userId, String productName, int productPrice) {
    User user =  userRepository.findById(userId);
    int discountPrice = discountPolicy.discount(user, productPrice);

    return new Order(userId, productName, productPrice, discountPrice);
  }
}
