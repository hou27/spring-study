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

## 컴포넌트 스캔 원리
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