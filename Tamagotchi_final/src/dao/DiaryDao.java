package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tamagochi.DBConnector;
import tamagochi.Diary;

public class DiaryDao {

    private final DBConnector connector;

    public DiaryDao(DBConnector connector) {
        this.connector = connector;
    }

    public boolean existsByTamaIdAndDayKey(int tamaId, Date dayKey) throws SQLException {
        String sql = "SELECT 1 FROM diaries WHERE tama_id=? AND day_key=?";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tamaId);
            ps.setDate(2, dayKey);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public Diary insert(int tamaId, String content, Date dayKey) throws SQLException {
        String sql =
                "INSERT INTO diaries(tama_id, content, written_at, day_key) " +
                "VALUES(?, ?, NOW(), ?)";

        try (Connection conn = connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, tamaId);
            ps.setString(2, content);
            ps.setDate(3, dayKey);

            int updated = ps.executeUpdate();
            if (updated != 1) {
                throw new SQLException("Insert failed: affected rows = " + updated);
            }

            // 최소 수정 포인트: generatedKeys가 비어있을 수 있으니 체크 + fallback
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs != null && rs.next()) {
                    Diary d = new Diary();
                    d.setDiaryId(rs.getInt(1));
                    d.setTamaId(tamaId);
                    d.setContent(content);
                    d.setDayKey(dayKey);
                    return d;
                }
            }

            try (PreparedStatement ps2 = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                 ResultSet rs2 = ps2.executeQuery()) {

                if (rs2.next()) {
                    Diary d = new Diary();
                    d.setDiaryId(rs2.getInt(1));
                    d.setTamaId(tamaId);
                    d.setContent(content);
                    d.setDayKey(dayKey);
                    return d;
                }
            }

            throw new SQLException("Insert succeeded but could not retrieve generated diary_id.");
        }
    }
}
