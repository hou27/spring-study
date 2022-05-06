package prac.prac_spring.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import prac.prac_spring.domain.User;

/**
 *  JpaRepository<Entity, 식별자(pk) type>
 *  interface는 다중 상속 가능
 *  
 *  Spring Data JPA가
 *  JpaRepository를 받고 있으면 구현체를 자동으로 만들어 Spring Bean에 등록해준다.
 *
 *  JpaRepository는 SimpleJpaRepository클래스를 구현하는데, 해당 클래스가 스프링 빈으로 등록된다.
 *
 *  @Autowired가 붙은 곳에 스프링 컨테이너가 같은 타입의 빈을 주입해주도록 동작한다.
 *
 *  @Bean이 붙거나, @Configuration, @Component, @Controller,Service,Repository 가 붙어야 빈으로 등록된다.
 */

public interface SpringDataJpaUserRepository extends JpaRepository<User, Long>, UserRepository {

  /**
   * 규칙에 맞게 메소드명을 작성해주면
   * JPQL을 아래와 같이 자동을 짜준다.
   * select u from User u where u.name = ?
   *
   * 즉, interface에 이름만 지정해줘도 기능 개발이 끝나는 것이다.
   *
   * @param name
   * @return Optional<User>
   */
  @Override
  Optional<User> findByName(String name);

//  @Override
//  Optional<User> findByNameAndId(String name, Long id);

}

/**
 * Spring Data Jpa가 기본적으로 제공하는 CRUD 기능을 사용하며
 * 일반적(공통적)이지 않은 기능은 위의 findByName처럼 규칙에 맞는 메서드명만으로도 해당 기능을 제공할 수 있다.
 */
