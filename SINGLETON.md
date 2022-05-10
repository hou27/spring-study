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
