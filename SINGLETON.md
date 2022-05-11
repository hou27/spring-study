# Singleton Pattern
> 클래스의 인스턴스가 1개만 생성되는 것을 보장하는 디자인 패턴

어플리케이션이 시작될 때 어떤 클래스가 최초 1회만 메모리에 인스턴스를 생성하는 방식이다.  

## 예제
기존 코드를 기반으로 한 Test 코드(spring X)
```java
@Test
@DisplayName("Pure DI Container without Spring")
void pureContainer() {
    AppConfig appConfig = new AppConfig();
    
    // 호출 시마다 객체 생성됨.
    UserService userService1 = appConfig.userService();
    UserService userService2 = appConfig.userService();
    
    // 참조값이 다른 것 확인
    System.out.println("userService1 = " + userService1);
    System.out.println("userService2 = " + userService2);
    
    // assertThat(userService1).isNotEqualTo(userService2);
    assertThat(userService1).isNotSameAs(userService2); // SameAs 는 == 연산을 사용한다.
}
```
Test Result :
```
userService1 = demo.core.user.UserServiceImpl@6b1274d2
userService2 = demo.core.user.UserServiceImpl@7bc1a03d
```
각 객체의 참조값이 다르다.  

임의로 작성한 싱글톤 패턴의 Service
```java
public class SingletonService {
  //1. static 영역에 객체를 딱 1개만 생성해둔다.
  private static final SingletonService instance = new SingletonService();
  
  //2. public으로 열어서 객체 인스턴스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.
  public static SingletonService getInstance() {
    return instance;
  }
  
  //3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
  private SingletonService() {}
}
```
1. 미리 static 영역에 객체를 1개 생성해둔다.
2. 객체 인스턴스를 할당받을 수 있는 public 메서드를 준비
3. 생성자는 private으로 만들어 new 키워드를 막는다.

싱글톤 패턴의 서비스를 기반으로 한 Test 코드
```java
@Test
@DisplayName("Use Object with Singleton Pattern")
public void singletonServiceTest() {
    //private으로 생성자를 막아두어 컴파일 오류가 발생한다.
    //new SingletonService();
    
    // 호출할 때 마다 같은 객체를 반환
    SingletonService singletonService1 = SingletonService.getInstance();
    SingletonService singletonService2 = SingletonService.getInstance();
    
    //참조값이 같은 것을 확인
    System.out.println("singletonService1 = " + singletonService1);
    System.out.println("singletonService2 = " + singletonService2);
    
    // Check singletonService1 == singletonService2
    assertThat(singletonService1).isSameAs(singletonService2);
}
```
Test Result :
```
singletonService1 = demo.core.singleton.SingletonService@23a5fd2
singletonService2 = demo.core.singleton.SingletonService@23a5fd2
```
각 객체의 참조값이 같다.

# 장점
- 객체를 매번 생성하지 않아 메모리의 낭비가 없다.  
- 두번째부터는 객체 로딩 시간이 빨라진다.

> ※ 주의 : 동시성 문제에 유의해야한다.

# 단점
- 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
- 의존관계상 클라이언트가 구체 클래스에 의존한다.  
-> DIP를 위반한다.
- 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
- 테스트하기 어렵다.
- 내부 속성을 변경하거나 초기화 하기 어렵다.
- private 생성자로 자식 클래스를 만들기 어렵다.
- 유연성이 떨어진다.

# 극복
Spring Container는 Singleton Pattern의 문제점을 해결하는 동시에  
객체 인스턴스들을 Singleton으로 관리한다.

Bean이 바로 Singleton으로 관리되는 것이다.  


# Singleton Container
예시 Test 코드
```java
@Test
@DisplayName("Spring Container && Singleton")
void springContainer() {
    ApplicationContext ac = new
        AnnotationConfigApplicationContext(AppConfig.class);
    
    //1. 조회: 호출할 때 마다 같은 객체를 반환
    UserService userService1 = ac.getBean("userService",
        UserService.class);
    
    //2. 조회: 호출할 때 마다 같은 객체를 반환
    UserService userService2 = ac.getBean("userService",
        UserService.class);
    
    //참조값이 같은 것을 확인
    System.out.println("userService1 = " + userService1);
    System.out.println("userService2 = " + userService2);
    
    assertThat(userService1).isSameAs(userService2);
}
```
위 테스트를 진행해보면, 당연하게도 성공하며 출력값은 아래와 같다.
```
userService1 = demo.core.user.UserServiceImpl@1672fe87
userService2 = demo.core.user.UserServiceImpl@1672fe87
```

이런 Spring Container는 기본적으로 Bean을 싱글톤 방식으로 등록한다.  
(싱글톤 방식만 지원하지는 않는다.)  


## 주의점
객체 인스턴스를 하나만 생성, 공유하여 사용하기 때문에  
결국 여러 Client들이 같은 인스턴스를 공유한다.  

때문에, stateless 하도록 설계해야한다.

예시를 함께 보자.
```java
public class StatefulService {
  private int price; // 상태를 유지하는 필드

  public void order(String name, int price) {
    System.out.println("name = " + name + " price = " + price);
    this.price = price; // Problem Occur
  }

  public int getPrice() {
    return price;
  }

}
```
위와 같이 stateful한 필드를 가진 객체가 있다고 가정하자.

```java
@Test
void statefulServiceSingleton() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    
    StatefulService statefulService1 = ac.getBean(StatefulService.class);
    StatefulService statefulService2 = ac.getBean(StatefulService.class);
    
    // Thread 1
    statefulService1.order("testUser1", 2000);
    // Thread 2
    statefulService1.order("testUser2", 3000);
    
    // Thread 1
    // Check order price
    int price1 = statefulService1.getPrice();
    System.out.println("price1 = " + price1); // 문제 발생한 것을 알 수 있다.
    
    assertThat(price1).isEqualTo(2000);
}

static class TestConfig {
    @Bean
    public StatefulService statefulService() {
      return new StatefulService();
    }
}
```
위와 같이 간단한 테스트 파일을 작성하여 확인해보면,
```
name = testUser1 price = 2000
name = testUser2 price = 3000
price1 = 3000
```
위와 같은 출력결과와 함께 테스트가 실패하는 것을 확인할 수 있다.  

1번 사용자가 호출 후 price에 특정 값을 저장해둔 후,  
2번 사용자가 호출하여 공유되는 필드에 다른 값을 저장해두어 위와 같은 문제가 발생하는 것이다.  

> 이런 경우 트러블 슈팅도 원인을 바로 알기 어려울 수 있고, 한번 데이터가 꼬이면 난감해질 수 있으므로 주의하자.

### @Configuration

```java
@Configuration
public class AppConfig {
  @Bean
  public UserService userService() {
    return new UserServiceImpl(userRepository()); // 1번째
  }

  @Bean
  public OrderService orderService() {
    return new OrderServiceImpl(
        userRepository(), // 2번째
        discountPolicy()
    );
  }

  @Bean
  public UserRepository userRepository() {
    return new MemoryUserRepository();
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }
}
```
현재 AppConfig 코드이다.  
잘 살펴보면, new MemoryUserRepository();가 2번 호출되는 것 같다.  

객체가 2개 생성되는 것 아닌가?(Singleton 패턴 깨짐)  

Test 코드를 통해 확인해보겠다.  

우선 Test를 위해 Service들에 코드를 추가하였다.

UserServiceImpl.java
```java
public class UserServiceImpl implements UserService {
//  private final UserRepository userRepository = new MemoryUserRepository();
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) { // 생성자로 어떤 구현체가 주입될 지 선택
    this.userRepository = userRepository;
  }
  
  ...
  
  // Test용
  public UserRepository getUserRepository() {
    return userRepository;
  }
}
```
OrderServiceImpl.java
```java
public class OrderServiceImpl implements OrderService {
  // interface만 의존
  private final UserRepository userRepository;
  private final DiscountPolicy discountPolicy;

  public OrderServiceImpl(UserRepository userRepository, DiscountPolicy discountPolicy) {
    this.userRepository = userRepository;
    this.discountPolicy = discountPolicy;
  }

  ...

  // Test용
  public UserRepository getUserRepository() {
    return userRepository;
  }
}
```
Test code
```java
@Test
void configurationTest() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    
    UserServiceImpl userService = ac.getBean("userService", UserServiceImpl.class);
    OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
    
    // Test용으로 추가한 메서드를 통해 각각에서 주입된 UserRepository를 확인한다.
    UserRepository userRepository1 = userService.getUserRepository();
    UserRepository userRepository2 = orderService.getUserRepository();
    System.out.println("userService ::: userRepository1 = " + userRepository1);
    System.out.println("orderService ::: userRepository2 = " + userRepository2);
    
    UserRepository userRepository = ac.getBean("userRepository", UserRepository.class);
    System.out.println("Bean ::: userRepository = " + userRepository);

    assertThat(userService.getUserRepository()).isSameAs(userRepository);
    assertThat(orderService.getUserRepository()).isSameAs(userRepository);
}
```
Test 출력 결과
```
userService ::: userRepository1 = demo.core.user.MemoryUserRepository@6676f6a0
orderService ::: userRepository2 = demo.core.user.MemoryUserRepository@6676f6a0
Bean ::: userRepository = demo.core.user.MemoryUserRepository@6676f6a0
```
모두 같은 인스턴스인 것을 확인할 수 있다.

알고보니 
```
AnnotationConfigApplicationContext
```
에 인수로 넘긴 값은 Spring Bean으로 등록되며  
AppConfig도 Spring Bean이 되는데,  
CGLIB라는 라이브러리를 통해 AppConfig class를 상속받은 다른 클래스를 생성하고, 그 클래스를 Spring Bean으로 등록한다.  

그 등록된 클래스는 새로운 Bean을 등록하는 요청이 올 때마다 기존에 존재하는지 확인하여  
없으면 생성하여 반환하고 있다면 기존의 것을 반환하여 Singleton을 보장해준다.  

### Main Point
@Configuration 어노테이션을 사용하지 않고  
@Bean만을 붙여서 확인해보면 알 수 있는데,  

**@Configuration 어노테이션이 CGLIB 기술을 사용하여 싱글톤을 보장해주는 것을 알 수 있다.**  
