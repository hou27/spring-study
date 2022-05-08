# AOP

## AOP란?
> Aspect Oriented Programming

- 공통 관심 사항(cross-cutting concern)과 핵심 관심 사항(core concern) 분리한다.

여러 군데에 공통적으로 적용되는 로직을 각각의 핵심 비즈니스 로직과 함께 두는 것이 아닌  
별도의 공통 로직으로 구성하여 필요한 부분에 적용해주는 방식이다.  

## Spring에서의 동작 방식
Spring은 AOP Bean이 있으면 적용되는 로직에 해당하는 가짜 Bean(Proxy 용도)을 만들어,  
스프링 컨테이너에 진짜 Bean 앞에 가짜 Bean을 앞세워 그 가짜가 끝나면  
joinPoint.proceed()를 통해 실제 로직을 실행한다.  

즉, target에 대한 proxy를 만들어 제공하고, 그 proxy를 통해 target을 감싸  
실행 전, 후에 적절한 동작을 하게 되는 것이다.
> 주의 : Spring Bean에만 적용이 가능하다.

### 간단한 확인
UserController line:20을 보면
```java
@Autowired
public UserController(UserService userService) {
    this.userService = userService;
    System.out.println("user service : " + userService.getClass());
    // Proxy를 붙이기 위해 해당 서비스가 조작된 것을 확인 (AOP 적용)
}
```
위와 같이 출력문을 삽입해두었는데,  
실제로 실행해보면
```shell
user service : class prac.prac_spring.service.UserService$$EnhancerBySpringCGLIB$$f9a33d2d
```
이렇게 userService를 복제해서 코드를 조작하는 기술(EnhancerBySpringCGLIB)을 사용하여  
Spring Container가 AOP가 적용된 것을 확인하면 Proxy를 적용한 것을 확인할 수 있다.