package prac.prac_spring.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import prac.prac_spring.domain.User;

public class JdbcUserRepository implements UserRepository {
  private final DataSource dataSource;

  public JdbcUserRepository(DataSource dataSource) { // spring을 통해서 dataSource를 주입받음.
    this.dataSource = dataSource;
  }

  @Override
  public User save(User user) {
    String sql = "insert into user(name) values(?)";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null; // 결과를 받는다.
    try {
      conn = getConnection(); // connection을 가져옴.
      pstmt = conn.prepareStatement(sql,
          Statement.RETURN_GENERATED_KEYS);
      // RETURN_GENERATED_KEYS : db에 insert하면 생성된 key를 받음
      pstmt.setString(1, user.getName());
      pstmt.executeUpdate(); // query가 날라감
      rs = pstmt.getGeneratedKeys(); // 생성된 key를 반환
      if (rs.next()) { // 
        user.setId(rs.getLong(1));
      } else {
        throw new SQLException("id 조회 실패");
      }
      return user;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs); // 연결 끊어줌
    }
  }
  @Override
  public Optional<User> findById(Long id) {
    String sql = "select * from user where id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setLong(1, id);
      rs = pstmt.executeQuery(); // 조회는 executeUpdate가 아니라 executeQuery
      if(rs.next()) { // 만약 있으면
        User user= new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        return Optional.of(user); // 반환
      } else {
        return Optional.empty();
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }
  @Override
  public List<User> findAll() {
    String sql = "select * from user";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      List<User> users = new ArrayList<>();
      while(rs.next()) {
        User user= new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        users.add(user);
      }
      return users;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }
  @Override
  public Optional<User> findByName(String name) {
    String sql = "select * from user where name = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, name);
      rs = pstmt.executeQuery();
      if(rs.next()) {
        User user= new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        return Optional.of(user);
      }
      return Optional.empty();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }
  private Connection getConnection() {
    // DataSourceUtils를 통해서 connection을 얻어야 이전의 transaction에 걸릴 수 있는데 그러면 같은 db connection을 유지해야하는데,
    // 이렇게 하면 유지해준다.
    return DataSourceUtils.getConnection(dataSource);
  }

  // resource 연결 close
  private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
  {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      if (pstmt != null) {
        pstmt.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      if (conn != null) {
        close(conn);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  private void close(Connection conn) throws SQLException {
    DataSourceUtils.releaseConnection(conn, dataSource);
  }
}
