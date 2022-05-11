package demo.core.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Autowired // Auto DI (ac.getBean(UserRepository.class)과 동일하게 동작한다고 생각하면 됨.)
  public UserServiceImpl(UserRepository userRepository) { // 생성자로 어떤 구현체가 주입될 지 선택
    this.userRepository = userRepository;
  }

  @Override
  public void signUp(User user) {
    userRepository.save(user);
  }

  @Override
  public User findUser(Long userId) {
    return userRepository.findById(userId);
  }

}
