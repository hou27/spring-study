package demo.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component가 붙은 클래스를 자동을으로 찾아 Spring Bean으로 등록한다.
@ComponentScan(
    basePackages = "demo.core", // 탐색할 package 시작 위치 지정.
    // 기존 코드 탓에 임의로 다른 파일에 @Configuration이 붙은 것을 스캔하지 않도록 설정.
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {}

/**
 * 1. @ComponentScan
 * @ComponentScan 은 @Component 가 붙은 모든 클래스를 스프링 빈으로 등록한다.
 * 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
 * 빈 이름 기본 전략: MemberServiceImpl 클래스 memberServiceImpl
 * 빈 이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면
 * @Component("memberService2") 이런식으로 이름을 부여하면 된다.
 *
 * 2. @Autowired 의존관계 자동 주입
 * 생성자에 @Autowired 를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
 * 이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.
 * getBean(MemberRepository.class) 와 동일하다고 이해하면 된다.
 * 생성자에 파라미터가 많아도 다 찾아서 자동으로 주입한다.
 * 탐색 위치와 기본 스캔 대상
 * 탐색할 패키지의 시작 위치 지정
 * 모든 자바 클래스를 다 컴포넌트 스캔하면 시간이 오래 걸린다. 그래서 꼭 필요한 위치부터 탐색하도록 시작
 * 위치를 지정할 수 있다.
 * @ComponentScan(
 *  basePackages = "demo.core",
 * }
 * basePackages : 탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두
 * 탐색한다.
 * basePackages = {"demo.core", "demo.service"} 이렇게 여러 시작 위치를 지정할 수도
 * 있다.
 * basePackageClasses : 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
 * 만약 지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
 *
 * 권장하는 방법
 * 개인적으로 즐겨 사용하는 방법은 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트
 * 최상단에 두는 것이다. 최근 스프링 부트도 이 방법을 기본으로 제공한다.
 * 예를 들어서 프로젝트가 다음과 같이 구조가 되어 있으면
 * com.demo
 * com.demo.serivce
 * com.demo.repository
 * com.demo 프로젝트 시작 루트, 여기에 AppConfig 같은 메인 설정 정보를 두고,
 * @ComponentScan 애노테이션을 붙이고, basePackages 지정은 생략한다.
 * 이렇게 하면 com.demo 를 포함한 하위는 모두 자동으로 컴포넌트 스캔의 대상이 된다. 그리고 프로젝트
 * 메인 설정 정보는 프로젝트를 대표하는 정보이기 때문에 프로젝트 시작 루트 위치에 두는 것이 좋다
 * 참고로 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 @SpringBootApplication 를 이
 * 프로젝트 시작 루트 위치에 두는 것이 관례이다. (그리고 이 설정안에 바로 @ComponentScan 이 들어있다!)
 *
 * 컴포넌트 스캔 기본 대상
 * 컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.
 * @Component : 컴포넌트 스캔에서 사용
 * @Controlller : 스프링 MVC 컨트롤러에서 사용
 * @Service : 스프링 비즈니스 로직에서 사용
 * @Repository : 스프링 데이터 접근 계층에서 사용
 * @Configuration : 스프링 설정 정보에서 사용
 * 해당 클래스의 소스 코드를 보면 @Component 를 포함하고 있는 것을 알 수 있다.
 * @Component
 * public @interface Controller {
 * }
 * @Component
 * public @interface Service {
 * }
 * @Component
 * public @interface Configuration {
 * }
 *
 * > 참고: 사실 애노테이션에는 상속관계라는 것이 없다. 그래서 이렇게 애노테이션이 특정 애노테이션을 들고
 * 있는 것을 인식할 수 있는 것은 자바 언어가 지원하는 기능은 아니고, 스프링이 지원하는 기능이다.
 *
 * 컴포넌트 스캔의 용도 뿐만 아니라 다음 애노테이션이 있으면 스프링은 부가 기능을 수행한다.
 * @Controller : 스프링 MVC 컨트롤러로 인식
 * @Repository : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환해준다.
 * @Configuration : 앞서 보았듯이 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가
 * 처리를 한다.
 * @Service : 사실 @Service 는 특별한 처리를 하지 않는다. 대신 개발자들이 핵심 비즈니스 로직이 여기에
 * 있겠구나 라고 비즈니스 계층을 인식하는데 도움이 된다.
 *
 * > 참고: useDefaultFilters 옵션은 기본으로 켜져있는데, 이 옵션을 끄면 기본 스캔 대상들이 제외된다.
 *
 * 필터
 * includeFilters : 컴포넌트 스캔 대상을 추가로 지정한다.
 * excludeFilters : 컴포넌트 스캔에서 제외할 대상을 지정한다.
 */
