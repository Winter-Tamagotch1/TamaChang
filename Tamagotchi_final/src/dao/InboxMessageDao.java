package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tamagochi.DBConnector;
import tamagochi.InboxMessage;

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

            int updated = ps.executeUpdate();
            if (updated != 1) {
                throw new SQLException("Insert failed: affected rows = " + updated);
            }

            // 최소 수정 포인트: generatedKeys 비어있을 수 있으니 체크
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    m.setInboxId(rs.getInt(1));
                    return m;
        }
    }
         // generatedKeys가 비어있으면 fallback
            try (PreparedStatement ps2 = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                 ResultSet rs2 = ps2.executeQuery()) {

                if (rs2.next()) {
                    m.setInboxId(rs2.getInt(1));
                    return m;
                }
            }

            throw new SQLException("Insert succeeded but could not retrieve generated inbox_id.");
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
