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