# Spring Container
> Spring은 Bean을 생성하고 관리하는 컨테이너를 가지고 있다. 

ApplicationContext가 바로 스프링 컨테이너의 주체이다.    
기본적으로 ApplicationContext는 interface이다.  

ApplicationContext는 BeanFactory를 상속받고 있다.  
BeanFactory 또한 스프링에서 제공하는 스프링 컨테이너의 유형 중 하나이며,  
스프링 Config 파일에 등록된 Bean 객체를 생성 및 관리하는 기본적인 기능을 제공하는 친구이다.  

ApplicationContext는 BeanFactory가 제공하는 기능 외에도 여러 기능을 추가로 제공한다.  
주요 포인트는 컨테이너가 구동되는 시점에 객체(Bean)들을 생성한다.  

Java 코드만으로 작성할 땐 개발자가 AppConfig를 사용해서 직접 객체를 생성하고 DI를 했지만,  
이제부터는 스프링 컨테이너를 통해서 진행한다.

스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정(구성) 정보로 사용한다.  
여기서 @Bean이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다.  
이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다.  


스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다.  
이전에는 개발자가 필요한 객체를 AppConfig를 사용해서 직접 조회했지만, 이제부터는 스프링
컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야 한다.  
스프링 빈은 applicationContext.getBean()메서드를 사용해서 찾을 수 있다.  
기존에는 개발자가 직접 자바코드로 모든 것을 했다면 이제부터는 스프링 컨테이너에 객체를 스프링 빈으로
등록하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용하도록 변경되었다.

## Spring Container 생성

스프링 컨테이너는 XML을 기반으로 만들 수 있고, 애노테이션 기반의 자바 설정 클래스로 만들 수 있다.  
AppConfig를 사용했던 방식이 애노테이션 기반의 자바 설정 클래스로 스프링 컨테이너를 만든 것이다.  
자바 설정 클래스를 기반으로 스프링 컨테이너( ApplicationContext )를 만들 땐 아래와 같이 한다.  
```java
new AnnotationConfigApplicationContext(AppConfig.class);
```

이 클래스는 ApplicationContext 인터페이스의 구현체이다.
> ※ BeanFactory를 직접 사용하는 경우는 거의 없으므로 일반적으로 ApplicationContext를 스프링 컨테이너라 한다

### 과정
1. 스프링 컨테이너 생성  
    ```java
   // Create Spring Container
    new AnnotationConfigApplicationContext(AppConfig.class);
    ```
   스프링 컨테이너를 생성할 때는 구성 정보를 지정해주어야 한다.
   여기서는 AppConfig.class를 구성 정보로 지정했다.
2. 스프링 빈 등록  
   스프링 컨테이너는 파라미터로 넘어온 설정 클래스 정보를 사용해서 스프링 빈을 등록한다.  
   빈 이름은 메서드 이름을 사용한다.
   빈 이름을 직접 부여할 수 도 있다.  
    ```java
    @Bean(name="userServiceBlahBlah")
    ```
    > 주의: 빈 이름은 항상 다른 이름을 부여해야 한다. 같은 이름을 부여하면, 다른 빈이 무시되거나, 기존 빈을
    덮어버리거나 설정에 따라 오류가 발생한다.
3. 스프링 빈 의존관계 설정 - 준비
    ```java
    @Configuration
    public class AppConfig {
      @Bean
      public UserService userService() {
        return new UserServiceImpl(userRepository());
      }
    
      @Bean
      public OrderService orderService() {
        return new OrderServiceImpl(userRepository(), discountPolicy());
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
4. 스프링 빈 의존관계 설정 - 완료  
   스프링 컨테이너는 설정 정보를 참고하여 의존관계를 주입한다.  
   단순히 자바 코드를 호출하는 것 같지만, 차이가 있다. 이 차이는 싱글톤 컨테이너를 통해 설명한다.  
   >참고  
   스프링은 빈을 생성하고, 의존관계를 주입하는 단계가 나누어져 있다. 그런데 이렇게 자바 코드로 스프링
   빈을 등록하면 생성자를 호출하면서 의존관계 주입도 한번에 처리된다. 여기서는 이해를 돕기 위해
   개념적으로 나누어 설명했다. 자세한 내용은 의존관계 자동 주입에서 다시 확인하겠다.

## Spring Bean 조회
### 모든 빈 출력하기  
  
ac.getBeanDefinitionNames() : 스프링에 등록된 모든 빈 이름을 조회  
ac.getBean() : 빈 이름으로 빈 객체(인스턴스)를 조회한다.  

_Test file
```java
ApplicationContextInfoTest.java

@Test
@DisplayName("모든 Bean 확인")
void findAllBean() {
   String[] beanDefinitionNames = ac.getBeanDefinitionNames();
   for (String beanDefinitionName : beanDefinitionNames ) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("beanDefinitionName = " + beanDefinitionName + "object = " + bean);
   }
}
```

### 애플리케이션 빈 출력하기  
스프링이 내부에서 사용하는 빈은 제외하고, 내가 등록한 빈만 출력  
스프링이 내부에서 사용하는 빈은 getRole() 로 구분할 수 있다.  
ROLE_APPLICATION : 일반적으로 사용자가 정의한 빈  
ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈

_Test file
```java
ApplicationContextInfoTest.java

@Test
@DisplayName("Application Bean 확인")
void findApplicationBean() {
 String[] beanDefinitionNames = ac.getBeanDefinitionNames();
 for (String beanDefinitionName : beanDefinitionNames) {
   BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
   // Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
   // Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
   if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
     Object bean = ac.getBean(beanDefinitionName);
     System.out.println("name=" + beanDefinitionName + " object=" + bean);
   }
 }
}
```