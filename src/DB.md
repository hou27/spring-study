# 스프링 DB 접근 기술
- Jdbc
- JdbcTemplate
- JPA
- 스프링 데이터 JPA

### H2 데이터베이스

보통 개발이나 테스트 용도로 가볍고 편리한 DB로 H2 DB를 사용한다.  

## JDBC
> Java DataBase Connectivity  

JDBC는 JAVA에서 Database에 접속할 수 있도록 해주는 API이다.  

JDBC는 각 DBMS에 종속되지 않는 API를 제공하며 

JAVA application - JDBC API - JDBC driver - DB  와 같은 구성으로 동작하는 것이 기본이다.  
여기서 JDBC driver는 각 DB에 맞는 client를 뜻한다.

### JdbcTemplate
> jdbc를 위한 템플릿

1. 기존의 JDBC는 connection으로 연결하고,  
2. query를 작성하여 실행하고,  
3. resultSet을 이용하여 처리한 후  
4. connection을 닫아주는 기본적인 과정이 필요했었다.  

그러나 위의 과정은 생산성은 물론이고 정신건강에도 좋지 않다.  

JdbcTemplate을 통해 구조적인 반복을 생략할 수 있으며  
가독성이 높은 코드를 작성할 수 있게 된다.  

JdbcTemplate은  
JDBC와 마찬가지로 DB를 위한 API인데,

가장 중요한 점은 Mybatis와 마찬가지로 SQL Mapper라는 점이다.  

# Persistence Framework

## SQL Mappper
- 객체와 SQL 문을 매핑하여 데이터를 객체화
- 객체와 관계를 매핑해주는 ORM과 달리 'SQL Query 결과'와 '객체'를 매핑.
- SQL 문을 직접 작성하여 DB를 다룸
- SQL 문법을 그대로 사용


## ORM
- Object-Relational Mapping(객체 관계 매핑)
- 객체와 데이터베이스의 데이터를 매핑하여 데이터를 객체화
- 객체는 객체대로 설계하고, 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 이 둘을 중간에서 매핑해줌
- 객체 간의 관계를 코드로 작성하면 이를 바탕으로 SQL 문을 자동으로 생성해줌

# JPA
> Java Persistence API. 대표적인 자바 ORM   
> Java 애플리케이션과 JDBC API 사이에서 동작한다.

SQL이 아닌 객체 중심적인 개발이 가능하게 해주며,  
JPA 자체는 interface(의 모음)이므로 이를 구현해주는 구현체가 존재한다.  

### 대표적인 JPA의 구현체
1. Hibernate
2. EclipseLink
3. DataNucleus

난 이 중 Hibernate를 사용함.  

Java 코드만 작성해도 자동으로 CRUD를 생성해주며, 변경 시에도 JPA가 처리한다.  
JPA는 인터페이스이기 때문에 특정 DBMS를 사용한다고 알려주는 코드만 수정하면 되기 때문에 특정 DBMS에 종속적이지 않다.  
즉, 유지보수 등의 면에서 생산성이 증가한다.  


이처럼 ORM을 통해 SQL Mapper의 단점들을 개선할 수 있지만, 단점이 존재한다.  
  
1. 러닝 커브가 존재한다.  
JPQL 같은 문법이 기존의 SQL 문법과 비슷하지만 조금 다르다.  
동작원리 등을 제대로 이해하지 못한 채로 사용한다면 성능 문제가 발생할 수 있다. (N + 1 문제)
2. 복잡한 쿼리를 사용하기 어렵다.   
Native SQL, JPQL, Query Dsl 등을 통해 해결
 
## Spring Data JPA
> Spring에서 JPA를 편하게 사용할 수 있도록 지원해주는 모듈

JPA를 한단계 추상화한 Repository를 제공하여 JPA를 보다 쉽고 편리하게 사용할 수 있다.