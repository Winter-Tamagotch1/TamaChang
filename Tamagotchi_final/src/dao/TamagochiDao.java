package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tamagochi.DBConnector;
import tamagochi.Tamagochi;

public class TamagochiDao {

    private final DBConnector connector;

    public TamagochiDao(DBConnector connector) {
        this.connector = connector;
    }

    public Tamagochi findById(int tamaId) throws SQLException {
        String sql = "SELECT * FROM tamagochi WHERE tama_id=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tamaId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            return map(rs);
        }
    }

    public Tamagochi findActiveByUserId(int userId) throws SQLException {
        String sql =
                "SELECT * FROM tamagochi " +
                "WHERE user_id=? AND status='ACTIVE' " +
                "ORDER BY created_at DESC LIMIT 1";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            return map(rs);
        }
    }

    public boolean existsSameNameForUser(int userId, String tamaName) throws SQLException {
        String sql = "SELECT 1 FROM tamagochi WHERE user_id=? AND tama_name=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, tamaName);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public Tamagochi insert(Tamagochi t) throws SQLException {
        String sql =
                "INSERT INTO tamagochi(" +
                "user_id, tama_name, char_type, tama_image, level, stress, status, runaway_started_at, created_at" +
                ") VALUES(?,?,?,?,?,?,?,NULL,NOW())";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, t.getUserId());
            ps.setString(2, t.getTamaName());
            ps.setString(3, t.getCharType());
            ps.setString(4, t.getTamaImage());
            ps.setInt(5, t.getLevel());
            ps.setInt(6, t.getStress());
            ps.setString(7, t.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            t.setTamaId(rs.getInt(1));
            return t;
        }
    }

    public void updateStress(int tamaId, int newStress) throws SQLException {
        String sql = "UPDATE tamagochi SET stress=? WHERE tama_id=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newStress);
            ps.setInt(2, tamaId);
            ps.executeUpdate();
        }
    }

    public void updateLevel(int tamaId, int newLevel) throws SQLException {
        String sql = "UPDATE tamagochi SET level=? WHERE tama_id=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newLevel);
            ps.setInt(2, tamaId);
            ps.executeUpdate();
        }
    }

    public void markRunaway(int tamaId) throws SQLException {
        String sql =
                "UPDATE tamagochi " +
                "SET status='RUNAWAY', runaway_started_at=NOW(), stress=100 " +
                "WHERE tama_id=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tamaId);
            ps.executeUpdate();
        }
    }

    public void markGraduated(int tamaId) throws SQLException {
        String sql = "UPDATE tamagochi SET status='GRADUATED' WHERE tama_id=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tamaId);
            ps.executeUpdate();
        }
    }

    public void deleteById(int tamaId) throws SQLException {
        String sql = "DELETE FROM tamagochi WHERE tama_id=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tamaId);
            ps.executeUpdate();
        }
    }

    public boolean isRunawayOverDays(int tamaId, int days) throws SQLException {
        String sql =
                "SELECT 1 " +
                "FROM tamagochi " +
                "WHERE tama_id=? AND status='RUNAWAY' " +
                "AND runaway_started_at IS NOT NULL " +
                "AND runaway_started_at <= DATE_SUB(NOW(), INTERVAL ? DAY)";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tamaId);
            ps.setInt(2, days);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private Tamagochi map(ResultSet rs) throws SQLException {
        Tamagochi t = new Tamagochi();
        t.setTamaId(rs.getInt("tama_id"));
        t.setUserId(rs.getInt("user_id"));
        t.setTamaName(rs.getString("tama_name"));
        t.setCharType(rs.getString("char_type"));
        t.setTamaImage(rs.getString("tama_image"));
        t.setLevel(rs.getInt("level"));
        t.setStress(rs.getInt("stress"));
        t.setStatus(rs.getString("status"));
        t.setRunawayStartedAt(rs.getTimestamp("runaway_started_at"));
        t.setCreatedAt(rs.getTimestamp("created_at"));
        return t;
    }
}
