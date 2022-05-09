# SOLID [OCP, DIP]
> 객체지향 설계 5원칙 SOLID 중 둘이다.

* OCP(Open Closed Principle) : 개방 폐쇄 원칙  
* DIP(Dependency Inversion Principle) : 의존 역전 원칙

# 문제
```java
FixDiscountPolicy.java

public class FixDiscountPolicy implements DiscountPolicy {
  ...
}


OrderServiceImpl.java

public class OrderServiceImpl implements OrderService {
  ...
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  @Override
  ...
}
```
위 코드를 보면 역할과 구현을 분리하긴 했지만  
OrderServiceImpl은 interface(DiscountPolicy)에만 의존하는 것이 아니라  
구현된 FixDiscountPolicy에도 의존하고 있는 것을 알 수 있다.  

문제 해결에 앞서서  
우선 OCP와 DIP에 대해 더 알아보자.
## OCP
> 소프트웨어의 요소는 확장에는 열려있으나 변경에는 닫혀있어야 한다는 원칙

이게 무슨 말일까?  
확장하기에는 용이하면서도 변경에는 영향을 받아선 안된다는 뜻이다.  

## DIP
> 상위 계층이 하위 계층에 의존하는 전통적 의존관계를 역전시켜,  
> 결국 상위 계층이 하위 계층의 구현으로부터 독립되게 하는 원칙

이는 추상화된 것은 구체화된(구현된) 것에 의존하면 안된다는 원칙으로,  
자주 변경되는 클래스에 의존하면 안된다는 뜻이다.  

# 해결
그렇다면 OCP와 DIP의 개념을 되새기며 문제를 해결해보자.  

문제를 다시 한번 살펴보면,  
### DIP 위반
OrderServiceImpl이 DiscountPolicy인터페이스 뿐만 아니라  
FixDiscountPolicy 인 구체 클래스도 함께 의존하고 있다.  
### OCP 위반
그래서 FixDiscountPolicy를 다른 정책으로 변경하는 순간 OrderServiceImpl의
소스 코드도 함께 변경해야 한다  

> 이렇게 2가지 문제점이 있다.  


인터페이스(추상화된 것)에만 의존하도록 설계를 변경하여 문제를 해결하도록 하겠다.  

Before
```java
public class OrderServiceImpl implements OrderService {
  ...
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

  ...
}
```
After
```java
public class OrderServiceImpl implements OrderService {
  ...
  private DiscountPolicy discountPolicy; // interface만 의존
  ...
}
```
우선 위와 같이 인터페이스에만 의존하도록 코드를 수정해주었다.

그러나 지금 상황에선 아무것도 할당되어있지 않아 NPE(null pointer exception)이 발생한다.  
그래서 OrderServiceImpl에 DiscountPolicy의 구현 객체를 생성, 주입해주기 위해 설정 파일을 추가하겠다.  

# Config file
```java
public class AppConfig {
  public UserService userService() {
    return new UserServiceImpl(userRepository());
  }

  public OrderService orderService() {
    return new OrderServiceImpl(userRepository(), discountPolicy());
  }

  public UserRepository userRepository() {
    return new MemoryUserRepository();
  }
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }
}
```
이렇게 설정 파일에서 각각의 의존 관계를 설정해주고,  

```java
OrderServiceImpl.java 

public class OrderServiceImpl implements OrderService {
  // interface만 의존
  private final UserRepository userRepository;
  private final DiscountPolicy discountPolicy;

  public OrderServiceImpl(UserRepository userRepository, DiscountPolicy discountPolicy) {
    this.userRepository = userRepository;
    this.discountPolicy = discountPolicy;
  }
  ...
}
```
기존 파일은 위와 같이 생성자를 통해 구현 객체를 주입받도록 하여,  
완벽하게 의존 관계에 대한 부분은 외부에 맡기고 해당 서비스가 담당한 것만 집중할 수 있게 되었다.  

## Config 사용
Test file에 사용된 예시를 살펴보겠다.
```java
public class OrderServiceTest {
// 기존 코드
//  UserService userService = new UserServiceImpl();
//  OrderService orderService = new OrderServiceImpl();

// 설정 파일을 사용한 코드 
  UserService userService;
  OrderService orderService;
  
  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig();
    userService = appConfig.userService();
    orderService = appConfig.orderService();
  }
  ...
}
```
이렇게 AppConfig 객체를 생성하여 해당 객체에게 의존 관계를 부여하는 일을 위임하였다.

# 결론
이렇게 객체의 생성과 연결은 Config 파일이 담당하고  
OrderServiceImpl은 추상 interface에만 의존하게 되어 DIP를 잘 지켰으며,  
의존 관계에 있는 것들이 변경되어도 OrderServiceImpl는 수정할 필요가 없어져 OCP 또한 잘 지키게 되었다.

# SRP
> 단일 책임 원칙  

하나의 클래스는 하나의 책임, 역할만 수행해야 한다는 원칙이다.  

현재 코드는 SRP를 따르면서 관심사를 분리하였는데,  
좋은 객체 지향 설계 5원칙(SOLID) 중 SRP, DIP, OCP의 3가지를 확실히 지켰다고 말할 수 있겠다.  

