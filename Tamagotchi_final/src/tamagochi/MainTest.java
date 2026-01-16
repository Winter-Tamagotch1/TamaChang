package tamagochi;

import dao.DiaryDao;
import dao.InboxMessageDao;
import dao.TamagochiDao;
import dao.UserDao;
import service.DiaryService;
import service.InboxMessageService;
import service.TamagochiService;
import service.UserService;
import tamagochi.DBConnector;
import tamagochi.InboxMessage;
import tamagochi.Tamagochi;
import tamagochi.User;

import java.sql.SQLException;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {

        DBConnector connector = new DBConnector();

        UserDao userDao = new UserDao(connector);
        TamagochiDao tamagochiDao = new TamagochiDao(connector);
        InboxMessageDao inboxMessageDao = new InboxMessageDao(connector);
        DiaryDao diaryDao = new DiaryDao(connector);

        UserService userService = new UserService(userDao);
        TamagochiService tamagochiService = new TamagochiService(tamagochiDao, inboxMessageDao);
        InboxMessageService inboxMessageService = new InboxMessageService(inboxMessageDao);
        DiaryService diaryService = new DiaryService(diaryDao, tamagochiDao, inboxMessageDao);

        String uniq = String.valueOf(System.currentTimeMillis());

        try {
            System.out.println("1) USER: getOrCreateUser 테스트");
            User u = userService.getOrCreateUser("test_user_" + uniq);
            int userId = u.getUserId();
            System.out.println("USER OK  userId=" + userId + "  username=" + u.getUsername());

            System.out.println("\n2) TAMAGOCHI: createCharacter 테스트");
            Tamagochi t = tamagochiService.createCharacter(
                    userId,
                    "test_tama_" + uniq,
                    "DEFAULT",
                    "default.png"
            );
            int tamaId = t.getTamaId();
            System.out.println("TAMAGOCHI OK  tamaId=" + tamaId + "  tamaName=" + t.getTamaName());

            System.out.println("\n3) INBOX: 랜덤 need 메시지 저장 테스트");
            tamagochiService.logRandomNeedMessage(tamaId);

            List<InboxMessage> unread = inboxMessageService.getUnreadMessages(userId);
            System.out.println("INBOX OK  unreadCount=" + unread.size());
            if (!unread.isEmpty()) {
                InboxMessage sample = unread.get(0);
                System.out.println("INBOX SAMPLE  type=" + sample.getMsgType() + "  msg=" + sample.getMessage());
            }

            System.out.println("\n4) DIARY: 21시 프롬프트 저장 테스트");
            diaryService.pushDiaryPrompt(userId, tamaId);

            List<InboxMessage> unread2 = inboxMessageService.getUnreadMessages(userId);
            System.out.println("DIARY PROMPT OK  unreadCount=" + unread2.size());

            System.out.println("\n5) DIARY: writeDiary 테스트 (레벨 +1, 30이면 졸업 처리)");
            Tamagochi before = tamagochiService.getTamagochiById(tamaId);
            int beforeLevel = before.getLevel();
            System.out.println("BEFORE  level=" + beforeLevel + "  status=" + before.getStatus());

            diaryService.writeDiary(tamaId, "DB 연결 테스트 일기 " + uniq);

            Tamagochi after = tamagochiService.getTamagochiById(tamaId);
            System.out.println("AFTER   level=" + after.getLevel() + "  status=" + after.getStatus());

            System.out.println("\n6) STRESS: tickStressIncrease / relieveStressByCare 테스트");
            tamagochiService.tickStressIncrease(tamaId);
            Tamagochi s1 = tamagochiService.getTamagochiById(tamaId);
            System.out.println("STRESS +10 OK  stress=" + s1.getStress());

            tamagochiService.relieveStressByCare(tamaId);
            Tamagochi s2 = tamagochiService.getTamagochiById(tamaId);
            System.out.println("STRESS -10 OK  stress=" + s2.getStress());

            boolean crying = tamagochiService.isCryingState(tamaId);
            System.out.println("CRYING STATE  isCrying=" + crying);

            System.out.println("\nALL DB CONNECTION TEST PASSED");
            
            System.out.println("\n=== DIRECT QUERY CHECK (SERVICE 기반 재조회) ===");

            List<InboxMessage> msgs = inboxMessageService.getUnreadMessages(userId);
            System.out.println("Unread inbox count = " + msgs.size());

            if (!msgs.isEmpty()) {
                InboxMessage m = msgs.get(0);
                System.out.println(
                    "Inbox sample → id=" + m.getInboxId() +
                    ", type=" + m.getMsgType() +
                    ", msg=" + m.getMessage()
                );
            }

            Tamagochi tCheck = tamagochiService.getTamagochiById(tamaId);
            System.out.println(
                "Tamagochi state → level=" + tCheck.getLevel() +
                ", stress=" + tCheck.getStress() +
                ", status=" + tCheck.getStatus()
            );


        } catch (IllegalStateException e) {
            System.out.println("\nTEST STOPPED (IllegalStateException)");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("\nTEST FAILED (SQLException)");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("\nTEST FAILED (Exception)");
            e.printStackTrace();
        }
    }
}
