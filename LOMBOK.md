# Lombok
> 다양한 어노테이션을 통해 Java의 반복되는 코드를 줄여주는 라이브러리

반복되는 코드가 줄어들어 간편하고 수고가 줄어드는 반면,  
코드가 눈에 보이는 직관성을 좋아하는 개발자들은 선호하지 않는 경우도 있다. 

# 설정 in intellij
> 롬복 라이브러리 적용 방법

### 1. build.gradle 에 라이브러리 및 환경 추가

```
...

//lombok 설정 추가
configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}
dependencies {
    ...
    
    //lombok 라이브러리 추가
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    
    testCompileOnly 'org.projectlombok:lombok'
    testAnnotationProcessor 'org.projectlombok:lombok'
    
    ...
}

...
```

### 2. plugin lombok 설치
### 3. Preferences Annotation Processors - Enable annotation processing 활성화