# Spring Boot

Study Spring Repository

## Building from Source

```
./gradlew build
```

```
cd build/libs
java -jar [Created jar file]
```
### Remove Build Folder

```
./gradlew clean
```
# Spring Bean 등록
## 1. 컴포넌트 스캔 원리
@Component annotation 이 있으면 스프링 빈으로 자동 등록됨.  

Component Scan 은 ComponentScan annotation 가 붙은  
package 의 하위 package 들을 대상으로 살핀다.  
(SpringBootApplication annotation 내부에 ComponentScan annotation 존재)

다음 annotation 들은 @Component annotation 가 등록되어있다.
```
@Controller  

@Service  

@Repository  
```
스프링은 spring container 에 spring bean 을 등록할 때, 기본으로 싱글톤으로 등록한다  
따라서 같은 스프링 빈이면 모두 같은 인스턴스다.  
(설정으로 싱글톤이 아니게 설정할 수 있긴 함.)

## 2. JAVA code로 직접 Spring Bean 등록
service와 repository의 @Service, @Repository, @Autowired annotation을 제거  

config 파일을 생성하여
```java
prac/prac_spring/SpringConfig.java

@Configuration
public class SpringConfig {

  @Bean
  public UserService userService() {
    return new UserService(userRepository());
  }

  @Bean
  public UserRepository userRepository() {
    return new MemoryUserRepository();
  }
}
```
위와 같이 직접 설정해줄 수 있다. (Controller는 예외)  

보통 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로
등록한다.  
현재 DB를 연결하지 않고 메모리를 통해 테스트 중이므로 후에 DB 연결 후 repository를 변경해야하는데,  
이렇게 설정을 통해 Bean으로 등록하게 되면 설정파일만 수정해주면 되므로 component scan 방식보다 간편하다.

### ex)
Before
```java
prac/prac_spring/SpringConfig.java

@Configuration
public class SpringConfig {

  ...

  @Bean
  public UserRepository userRepository() {
    return new MemoryUserRepository(); // Before
  }
}
```
After
```java
prac/prac_spring/SpringConfig.java

@Configuration
public class SpringConfig {

  ...

  @Bean
  public UserRepository userRepository() {
    return new DbUserRepository(); // Change Repository
  }
}
```
> 주의: @Autowired를 통한 DI는 Controller, Service 등과 같이 스프링이 관리하는 객체에서만 동작한다.

## 정리
anntation을 활용 시
1. Spring Container 동작 시 먼저 Component Scan
2. @Component anntation이 달린 class를 찾아 Bean으로 등록
3. 생성자 주입의 경우 생성되는 과정 속에서 @Autowired을 찾아 DI 수행

SpringConfig 통해 직접 설정할 경우
1. Component Scan 진행 - @Configuration에 @Component 포함되어 있음
2. SpringConfig를 통해 Service, Repository를 Bean으로 등록
3. 설정 내용으로 인해 Service와 Repository 간의 DI 수행
4. MemberController는 예외    

=> MemberController의 경우 MemberService가 빈으로 등록되어 있어도 @Autowired는 필요하다. (스프링 부트의 경우 생성자 주입에 대하여 편의를 위해 @Autowired를 생략할 수 있도록 구현되어 있긴 하다.) 스프링이 @Autowired를 보고 의존관계를 자동 주입(컨테이너에서 의존관계에 해당하는 빈을 찾아와서 주입)하기 때문이다.


