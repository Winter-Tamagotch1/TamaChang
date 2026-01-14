package tamagochi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
  users 테이블 DAO

  테이블(users)
  - user_id   BIGINT AUTO_INCREMENT (PK)
  - username  VARCHAR(50)

  역할
  - username으로 사용자 조회
  - username으로 사용자 생성
*/
public class UserDao {

    private final DBConnector connector;

    public UserDao(DBConnector connector) {
        this.connector = connector;
    }

    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT user_id, username FROM users WHERE username=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            User u = new User();
            u.setUserId(rs.getInt("user_id"));
            u.setUsername(rs.getString("username"));
            return u;
        }
    }

    public User insert(String username) throws SQLException {
        String sql = "INSERT INTO users(username) VALUES(?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, username);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            User u = new User();
            u.setUserId(rs.getInt(1));
            u.setUsername(username);
            return u;
        }
    }
}
