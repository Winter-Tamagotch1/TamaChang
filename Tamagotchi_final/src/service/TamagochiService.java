package service;

import java.sql.SQLException;
import java.util.Random;

import dao.InboxMessageDao;
import dao.TamagochiDao;
import tamagochi.InboxMessage;
import tamagochi.Tamagochi;

/*
  1) 캐릭터 생성: user_id + 캐릭터 이름 + 타입/이미지
  2) 10초마다 랜덤 문장 생성: 이 서비스에서 문장 선택 가능
  3) 버튼 상호작용: stress -10
     방치: stress +10
  4) stress>=70이면 우는 이미지로 변경: 보통 UI에서 stress 값을 보고 이미지 선택
  5) stress==100이면 가출: status=RUNAWAY, runaway_started_at 기록
     가출 후 14일 지나면 영구 가출: 데이터 초기화
  7) 레벨 30이면 졸업: status=GRADUATED

*/
public class TamagochiService {

    private final TamagochiDao tamagochiDao;
    private final InboxMessageDao inboxMessageDao;
    private final Random random = new Random();

    public TamagochiService(TamagochiDao tamagochiDao, InboxMessageDao inboxMessageDao) {
        this.tamagochiDao = tamagochiDao;
        this.inboxMessageDao = inboxMessageDao;
    }

    public Tamagochi createCharacter(int userId, String tamaName, String charType, String tamaImage) throws SQLException {
        if (tamaName == null || tamaName.trim().isEmpty()) {
            throw new IllegalArgumentException("캐릭터 이름이 비어있습니다.");
        }

        if (tamagochiDao.existsSameNameForUser(userId, tamaName.trim())) {
            throw new IllegalStateException("같은 유저에게 동일한 캐릭터 이름이 이미 존재합니다.");
        }

        Tamagochi t = new Tamagochi();
        t.setUserId(userId);
        t.setTamaName(tamaName.trim());
        t.setCharType(charType);
        t.setTamaImage(tamaImage);

        t.setLevel(1);
        t.setStress(0);
        t.setStatus("ACTIVE");

        return tamagochiDao.insert(t);
    }

    public Tamagochi getActiveCharacter(int userId) throws SQLException {
        return tamagochiDao.findActiveByUserId(userId);
    }

    public String pickRandomNeedSentence() {
        int r = random.nextInt(3);
        if (r == 0) return "배고파요! 밥 주세요~";
        if (r == 1) return "심심해요, 놀아주세요!";
        return "몸이 근질근질...씻고싶어요";
    }

    public void tickStressIncrease(int tamaId) throws SQLException {
        Tamagochi t = tamagochiDao.findById(tamaId);
        if (t == null) throw new IllegalArgumentException("다마고치 없음");
        if (!"ACTIVE".equals(t.getStatus())) return;

        int next = t.getStress() + 10;
        if (next >= 100) {
            tamagochiDao.markRunaway(tamaId);
            return;
        }

        tamagochiDao.updateStress(tamaId, next);
    }

    public void relieveStressByCare(int tamaId) throws SQLException {
        Tamagochi t = tamagochiDao.findById(tamaId);
        if (t == null) throw new IllegalArgumentException("다마고치 없음");
        if (!"ACTIVE".equals(t.getStatus())) return;

        int next = t.getStress() - 10;
        if (next < 0) next = 0;

        tamagochiDao.updateStress(tamaId, next);
    }

    public void logRandomNeedMessage(int tamaId) throws SQLException {
        Tamagochi t = tamagochiDao.findById(tamaId);
        if (t == null) throw new IllegalArgumentException("다마고치 없음");
        if (!"ACTIVE".equals(t.getStatus())) return;

        InboxMessage msg = new InboxMessage();
        msg.setUserId(t.getUserId());
        msg.setTamaId(tamaId);
        msg.setMsgType("NEED");
        msg.setMessage(pickRandomNeedSentence());

        inboxMessageDao.insert(msg);
    }

    public boolean isCryingState(int tamaId) throws SQLException {
        Tamagochi t = tamagochiDao.findById(tamaId);
        if (t == null) throw new IllegalArgumentException("다마고치 없음");

        return "ACTIVE".equals(t.getStatus()) && t.getStress() >= 70;
    }

    public void permanentRunawayCleanupIfOver14Days(int tamaId) throws SQLException {
        if (tamagochiDao.isRunawayOverDays(tamaId, 14)) {
            tamagochiDao.deleteById(tamaId);
        }
    }
    public Tamagochi getTamagochiById(int tamaId) throws Exception {
        return tamagochiDao.findById(tamaId);
    }

}
