package tamagochi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
  diaries 테이블 DAO

  테이블(diaries)
  - diary_id    BIGINT AUTO_INCREMENT (PK)
  - tama_id     BIGINT (FK)
  - content     TEXT
  - written_at  DATETIME
  - day_key     DATE (하루 1회 제한용)

  DAO는 일기 존재 확인과 저장만 담당합니다.
*/
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

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            Diary d = new Diary();
            d.setDiaryId(rs.getInt(1));
            d.setTamaId(tamaId);
            d.setContent(content);
            d.setDayKey(dayKey);
            return d;
        }
    }
}
