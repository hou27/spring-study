# Variety Ways for Dependency Injection

## **의존관계 주입 방법의 종류**

**Spring에서 의존관계를 주입하는 방법은 크게 3가지로 나눌 수 있는데,**

**이는 다음과 같다.**

**1\. 생성자 주입**

**2\. 메서드 주입**

**3\. 필드 주입**

### **생성자 주입**

생성자 호출 시점(즉, 객체가 생성될 때)에 단 1번만 호출되어 불변성이 보증되는 방법이다.

가장 보편적으로 사용된다.

### **메서드 주입**

**클래스 내의 정의한 메서드를 통해 의존관계를 주입받는 방법이다.**

예시는 아래와 같다.

```
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  
  @Autowired
  public void injection(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
```

위와 같이 injection이라는 메서드를 정의하고,

**@Autowired** 어노테이션을 추가하여 Spring에게 의존 관계를 주입해달라고 명시하였다.

참고로, **setter 메서드**를 통해서

```
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  
  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
```

위와 같이 의존 관계를 주입받을 수 있다.

메서드 또는 setter를 통해 주입받게 되면 필드의 값을 변경할 수 있어

의존 관계를 변경할 가능성이 있을 때 유용하게 쓰인다.

### **필드 주입**

**마지막으로 필드에 바로 주입받는 방법이다.**

```
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepository;
  
  ...
}
```

_위의 예시에서 알 수 있듯이 매우 코드가 간단하고 간편한데,_

_외부에서 변경이 불가능하여 테스트가 매우 힘들며, 프레임워크 없이는 아무것도 할 수 없는 방식이라_

**추천하지 않는다.**

**예시 코드들에서 공통적으로 등장한 @Autowired 어노테이션은 당연하게도 Spring Bean에서만 동작한다.**

#### **\+ 마무리**

**대부분의 의존관계는 한번 주입하면 프로그램 종료 시까지 변경하는 경우가 드물며,**

**생성자 주입이 아닌 다른 방법들은 실수로 의존 관계에 영향을 미칠 수 있으며**

**사용 시 불변성을 보장할 수 있어 생성자 주입을 통해 의존 관계를 주입하는 것을 추천한다.**


> @Autowired 의 기본 동작은 주입할 대상이 없으면 오류가 발생한다. 주입할 대상이 없어도 동작하게
하려면 @Autowired(required = false) 로 지정하면 된다.

> 자바빈 프로퍼티, 자바에서는 과거부터 필드의 값을 직접 변경하지 않고, setXxx, getXxx 라는
메서드를 통해서 값을 읽거나 수정하는 규칙을 만들었는데, 그것이 자바빈 프로퍼티 규약이다.

> 순수한 자바 테스트 코드에는 당연히 @Autowired가 동작하지 않는다. @SpringBootTest 처럼
스프링 컨테이너를 테스트에 통합한 경우에만 가능하다.

