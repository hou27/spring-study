package demo.core.order;

import demo.core.discount.DiscountPolicy;
import demo.core.discount.FixDiscountPolicy;
import demo.core.user.MemoryUserRepository;
import demo.core.user.User;
import demo.core.user.UserRepository;

public class OrderServiceImpl implements OrderService {
  private final UserRepository userRepository = new MemoryUserRepository();
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  @Override
  public Order createOrder(Long userId, String productName, int productPrice) {
    User user =  userRepository.findById(userId);
    int discountPrice = discountPolicy.discount(user, productPrice);

    return new Order(userId, productName, productPrice, discountPrice);
  }
}
