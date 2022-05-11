package demo.core.order;

import demo.core.discount.DiscountPolicy;
import demo.core.discount.FixDiscountPolicy;
import demo.core.user.MemoryUserRepository;
import demo.core.user.User;
import demo.core.user.UserRepository;

public class OrderServiceImpl implements OrderService {
  // interface만 의존
  private final UserRepository userRepository;
  private final DiscountPolicy discountPolicy;

  public OrderServiceImpl(UserRepository userRepository, DiscountPolicy discountPolicy) {
    this.userRepository = userRepository;
    this.discountPolicy = discountPolicy;
  }

  @Override
  public Order createOrder(Long userId, String productName, int productPrice) {
    User user =  userRepository.findById(userId);
    int discountPrice = discountPolicy.discount(user, productPrice);

    return new Order(userId, productName, productPrice, discountPrice);
  }

  // Test용
  public UserRepository getUserRepository() {
    return userRepository;
  }
}
