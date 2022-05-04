package prac.prac_spring.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import prac.prac_spring.domain.User;

public class JpaUserRepository implements UserRepository{
  private final EntityManager em;

  public JpaUserRepository(EntityManager em) {
    this.em = em;
  }

  @Override
  public User save(User user) {
    em.persist(user);
    return user;
  }

  @Override
  public Optional<User> findById(Long id) {
    User user = em.find(User.class, id);
    return Optional.ofNullable(user);
  }

  @Override
  public Optional<User> findByName(String name) {
//    User user = em.find(User.class, name);
    List<User> result = em.createQuery("select u from User u where u.name = :name", User.class)
        .setParameter("name", name)
        .getResultList();
    return result.stream().findAny();
  }

  @Override
  public List<User> findAll() {
    // return em.createQuery("select u from User as u", User.class).getResultList();
    return em.createQuery("select u from User u", User.class)
        .getResultList();
  }
}
