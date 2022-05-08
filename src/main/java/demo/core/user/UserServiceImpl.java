package demo.core.user;

public class UserServiceImpl implements UserService {
//  private final UserRepository userRepository = new MemoryUserRepository();
  private final UserRepository userRepository;

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
