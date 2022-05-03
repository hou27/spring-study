package prac.prac_spring.dtos.UserDtos;

public class SignUpFrom {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    // Java의 빈 property 규약에 의하여 넘어온 name을 통해 setName이 호출된다.
    System.out.println("Set Name "+name);
    this.name = name;
  }

}
