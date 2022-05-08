package demo.core.order;

public class Order {
  private Long userId;
  private String productName;
  private int productPrice;
  private int discountPrice;

  public Order(Long userId, String productName, int productPrice, int discountPrice) {
    this.userId = userId;
    this.productName = productName;
    this.productPrice = productPrice;
    this.discountPrice = discountPrice;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(int productPrice) {
    this.productPrice = productPrice;
  }

  public int getDiscountPrice() {
    return discountPrice;
  }

  public void setDiscountPrice(int discountPrice) {
    this.discountPrice = discountPrice;
  }

  @Override
  public String toString() {
    return "Order{" +
        "userId=" + userId +
        ", productName='" + productName + '\'' +
        ", productPrice=" + productPrice +
        ", discountPrice=" + discountPrice +
        '}';
  }
}
