package tamagochi;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

/*
  계획서 6,7번 담당 서비스

  6) 매일 밤 9시에 “오늘 하루는 어땠나요? 저에게 들려주세요.” 문장 표시
     구현 방식: inbox_messages에 DIARY_PROMPT 메시지 1건 저장

  일기 작성 규칙
  - 하루 1회: diaries.day_key로 제한
  - 작성 성공 시 level +1
  - level 30 도달 시 졸업(status='GRADUATED')
*/
public class DiaryService {

    private final DiaryDao diaryDao;
    private final TamagochiDao tamagochiDao;
    private final InboxMessageDao inboxMessageDao;

    public DiaryService(DiaryDao diaryDao, TamagochiDao tamagochiDao, InboxMessageDao inboxMessageDao) {
        this.diaryDao = diaryDao;
        this.tamagochiDao = tamagochiDao;
        this.inboxMessageDao = inboxMessageDao;
    }

    public void pushDiaryPrompt(int userId, int tamaId) throws SQLException {
        Tamagochi t = tamagochiDao.findById(tamaId);
        if (t == null) throw new IllegalArgumentException("다마고치 없음");
        if (t.getUserId() != userId) throw new IllegalStateException("소유 관계 불일치");
        if (!"ACTIVE".equals(t.getStatus())) return;

        InboxMessage msg = new InboxMessage();
        msg.setUserId(userId);
        msg.setTamaId(tamaId);
        msg.setMsgType("DIARY_PROMPT");
        msg.setMessage("오늘 하루는 어땠나요? 저에게 들려주세요.");

        inboxMessageDao.insert(msg);
    }

    public void writeDiary(int tamaId, String content) throws SQLException {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("일기 내용이 비어있습니다.");
        }

        Tamagochi t = tamagochiDao.findById(tamaId);
        if (t == null) throw new IllegalArgumentException("다마고치 없음");
        if (!"ACTIVE".equals(t.getStatus())) {
            throw new IllegalStateException("ACTIVE 상태에서만 일기를 작성할 수 있습니다.");
        }

        Date today = Date.valueOf(LocalDate.now());

        if (diaryDao.existsByTamaIdAndDayKey(tamaId, today)) {
            throw new IllegalStateException("오늘은 이미 일기를 작성했습니다.");
        }

        diaryDao.insert(tamaId, content.trim(), today);

        int newLevel = t.getLevel() + 1;
        tamagochiDao.updateLevel(tamaId, newLevel);

        if (newLevel >= 30) {
            tamagochiDao.markGraduated(tamaId);

            InboxMessage msg = new InboxMessage();
            msg.setUserId(t.getUserId());
            msg.setTamaId(tamaId);
            msg.setMsgType("GRADUATED");
            msg.setMessage("레벨 30을 달성하여 졸업했습니다.");
            inboxMessageDao.insert(msg);
        }
    }
}
