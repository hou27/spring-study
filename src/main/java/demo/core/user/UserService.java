package demo.core.user;

public interface UserService {
  void signUp(User user);

  User findUser(Long userId);

}
