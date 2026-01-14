package tamagochi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
  inbox_messages 테이블 DAO

  테이블(inbox_messages)
  - inbox_id   BIGINT AUTO_INCREMENT (PK)
  - user_id    BIGINT (FK)
  - tama_id    BIGINT (FK)
  - msg_type   VARCHAR(50)
  - message    VARCHAR(50)
  - created_at DATETIME
  - is_read    TINYINT (0/1)
  - read_at    DATETIME NULL

  DAO는 메시지 저장/조회/읽음처리만 담당합니다.
*/
public class InboxMessageDao {

    private final DBConnector connector;

    public InboxMessageDao(DBConnector connector) {
        this.connector = connector;
    }

    public InboxMessage insert(InboxMessage m) throws SQLException {
        String sql =
                "INSERT INTO inbox_messages(user_id, tama_id, msg_type, message, created_at, is_read, read_at) " +
                "VALUES(?,?,?,?,NOW(),0,NULL)";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, m.getUserId());
            ps.setInt(2, m.getTamaId());
            ps.setString(3, m.getMsgType());
            ps.setString(4, m.getMessage());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            m.setInboxId(rs.getInt(1));
            return m;
        }
    }

    public List<InboxMessage> findUnreadByUserId(int userId) throws SQLException {
        String sql =
                "SELECT * FROM inbox_messages " +
                "WHERE user_id=? AND is_read=0 " +
                "ORDER BY created_at ASC";

        List<InboxMessage> list = new ArrayList<>();

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InboxMessage m = new InboxMessage();
                m.setInboxId(rs.getInt("inbox_id"));
                m.setUserId(rs.getInt("user_id"));
                m.setTamaId(rs.getInt("tama_id"));
                m.setMsgType(rs.getString("msg_type"));
                m.setMessage(rs.getString("message"));
                m.setCreatedAt(rs.getTimestamp("created_at"));
                m.setIsRead(rs.getInt("is_read"));
                m.setReadAt(rs.getTimestamp("read_at"));
                list.add(m);
            }
        }
        return list;
    }

    public void markRead(int inboxId) throws SQLException {
        String sql =
                "UPDATE inbox_messages " +
                "SET is_read=1, read_at=NOW() " +
                "WHERE inbox_id=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, inboxId);
            ps.executeUpdate();
        }
    }

    public void markAllUnreadAsRead(int userId) throws SQLException {
        String sql =
                "UPDATE inbox_messages " +
                "SET is_read=1, read_at=NOW() " +
                "WHERE user_id=? AND is_read=0";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
