package demo.core.order;

public interface OrderService {
  Order createOrder(Long userId, String productName, int productPrice);


}
