package demo.core.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class User {
  private Long id;
  private String name;
  private Grade grade;

  /**
   * Lombok의
   * @RequiredArgsConstructor는
   * final이 붙은 (필수인) 필드들을 모아 생성자를 만들어준다.
   */
//  public User(Long id, String name, Grade grade) {
//    this.id = id;
//    this.name = name;
//    this.grade = grade;
//  }

  /**
   * Lombok의 
   * @Getter, @Setter로 대체
   */
//  public Long getId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  public Grade getGrade() {
//    return grade;
//  }
//
//  public void setGrade(Grade grade) {
//    this.grade = grade;
//  }
}
