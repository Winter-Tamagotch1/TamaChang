package controller;

import java.awt.Component;
import java.awt.Container;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List; // List import 추가

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.Timer;

import dao.DiaryDao;
import dao.InboxMessageDao;
import dao.TamagochiDao;
import dao.UserDao;
import diarytamagotchiswing.AppState;
import diarytamagotchiswing.ScreenSwitcher;
import screens.ScreenMainPet;
import service.DiaryService;
import service.InboxMessageService;
import service.TamagochiService;
import service.UserService;
import tamagochi.DBConnector;
import tamagochi.InboxMessage; // DTO import 추가
import tamagochi.Tamagochi;
import tamagochi.User;

public class ControllerMainPet {

    private final ScreenMainPet view;
    private final AppState state = AppState.getInstance();

    private final UserService userService;
    private final TamagochiService tamagochiService;
    private final InboxMessageService inboxMessageService; 
    private final DiaryService diaryService;

    private final JProgressBar stressBar;
    private final JButton btnEat;
    private final JButton btnPlayPet;
    private final JButton btnWash;
    private final JButton btnSaveDiary;
    private final JTextArea diaryInput;

    // 10초 규칙(랜덤 문장 + 방치 스트레스)
    private Timer tick2s;

    // 21:00 일기 프롬프트(중복 방지용)
    private LocalDate lastDiaryPromptDate = null;

    public ControllerMainPet(ScreenMainPet view) {
        this.view = view;

        DBConnector connector = new DBConnector();
        UserDao userDao = new UserDao(connector);
        TamagochiDao tamagochiDao = new TamagochiDao(connector);
        InboxMessageDao inboxMessageDao = new InboxMessageDao(connector);
        DiaryDao diaryDao = new DiaryDao(connector);

        this.userService = new UserService(userDao);
        this.tamagochiService = new TamagochiService(tamagochiDao, inboxMessageDao);
        this.inboxMessageService = new InboxMessageService(inboxMessageDao);
        this.diaryService = new DiaryService(diaryDao, tamagochiDao, inboxMessageDao);

        this.stressBar = findFirst(view, JProgressBar.class);
        this.btnEat = findButtonByText(view, "밥 먹이기");
        this.btnPlayPet = findButtonByText(view, "놀아주기");
        this.btnWash = findButtonByText(view, "씻기기");
        this.btnSaveDiary = view.getBtnSave();
        this.diaryInput = findFirst(view, JTextArea.class);

        initStressBar();
        bind();
        refreshFromDB();
        startTimers();
    }

    private void initStressBar() {
        if (stressBar == null) return;
        stressBar.setMinimum(0);
        stressBar.setMaximum(100);
        stressBar.setValue(0);
        stressBar.setIndeterminate(false);
        stressBar.setStringPainted(false);
    }

    private void bind() {
        if (btnEat != null) btnEat.addActionListener(e -> careOnce());
        if (btnPlayPet != null) btnPlayPet.addActionListener(e -> careOnce());
        if (btnWash != null) btnWash.addActionListener(e -> careOnce());

        if (btnSaveDiary != null) btnSaveDiary.addActionListener(e -> saveDiary());
    }

    private void startTimers() {
        // 10초마다: NEED 메시지 저장 + stress +10, 상태 갱신
        tick2s = new Timer(2_000, e -> {
            try {
                Tamagochi t = getMyActiveTamagochi();
                if (t == null) return;

                // 1. 랜덤 메시지 생성 및 DB 저장 (기존 로직)
                tamagochiService.logRandomNeedMessage(t.getTamaId());
                
                // 2. 스트레스 증가 (기존 로직)
                tamagochiService.tickStressIncrease(t.getTamaId());

                // 3. 상태 갱신 (스트레스바 등)
                refreshFromDB();

                // 4. [추가] DB에 쌓인 메시지를 가져와서 화면(Log)에 출력
                fetchAndShowMessages(t.getUserId());

                Tamagochi after = tamagochiService.getTamagochiById(t.getTamaId());
                if (after != null && "RUNAWAY".equals(after.getStatus())) {
                    // 가출 14일 지나면 삭제(서비스 규칙)
                    tamagochiService.permanentRunawayCleanupIfOver14Days(after.getTamaId());
                    tick2s.stop();
                    switchTo("ending"); 
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // 21:00 프롬프트 체크(10초 타이머 안에서 같이 처리)
            pushDiaryPromptIfNeeded();
        });
        tick2s.start();
    }
    
    // [추가] 메시지 가져와서 화면에 뿌리는 메서드
    private void fetchAndShowMessages(int userId) {
        try {
            // 안 읽은 메시지 가져오기
            List<InboxMessage> msgs = inboxMessageService.getUnreadMessages(userId);
            
            for (InboxMessage m : msgs) {
                String sender = state.getPetName(); // 펫 이름
                String text = m.getMessage();
                
                // 화면에 추가 (예: "뽀삐: 배고파요!")
                view.appendLog(sender + ": " + text);
                
                // 읽음 처리 (다시 안 뜨게)
                inboxMessageService.readOne(m.getInboxId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void pushDiaryPromptIfNeeded() {
        try {
            Tamagochi t = getMyActiveTamagochi();
            if (t == null) return;

            LocalTime now = LocalTime.now();
            LocalDate today = LocalDate.now();

            // 21:00 ~ 21:01 사이에 1번만
            boolean isNinePM = (now.getHour() == 21 && now.getMinute() == 0);
            if (!isNinePM) return;

            if (lastDiaryPromptDate != null && lastDiaryPromptDate.equals(today)) return;

            User u = userService.getOrCreateUser(state.getUserName());
            diaryService.pushDiaryPrompt(u.getUserId(), t.getTamaId());
            lastDiaryPromptDate = today;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void careOnce() {
        try {
            Tamagochi t = getMyActiveTamagochi();
            if (t == null) return;

            tamagochiService.relieveStressByCare(t.getTamaId());
            refreshFromDB();
            
            // [추가] 케어했을 때 반응 메시지도 바로 화면에 띄우고 싶다면?
            // 여기서는 간단히 스트레스만 줄이지만, 필요하면 inboxService.saveMessage(...) 호출 가능

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void saveDiary() {
        try {
            Tamagochi t = getMyActiveTamagochi();
            if (t == null) return;

            String content = (diaryInput == null) ? "" : diaryInput.getText();
            diaryService.writeDiary(t.getTamaId(), content);
            
            // 일기 썼다는 로그 추가
            view.appendLog("[시스템] 일기가 저장되었습니다.");

            view.clearDiaryInput();
            refreshFromDB();
            
            // 졸업 체크 후 메시지 표시를 위해 한 번 더 fetch
            fetchAndShowMessages(t.getUserId());

            // 졸업이면 ending으로 전환
            Tamagochi after = tamagochiService.getTamagochiById(t.getTamaId());
            if (after != null && "GRADUATED".equals(after.getStatus())) {
                if (tick2s != null) tick2s.stop();
                switchTo("ending");
            }

        } catch (IllegalArgumentException | IllegalStateException ex) {
            // [추가] 에러 메시지도 로그창에 띄우기
            view.appendLog("[오류] " + ex.getMessage());
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void refreshFromDB() {
        try {
            Tamagochi t = getMyActiveTamagochi();
            if (t == null) {
                switchTo("select");
                return;
            }

            if (stressBar != null) stressBar.setValue(t.getStress());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Tamagochi getMyActiveTamagochi() throws SQLException {
        String username = state.getUserName();
        if (username == null || username.trim().isEmpty()) return null;

        User u = userService.getOrCreateUser(username.trim());
        return tamagochiService.getActiveCharacter(u.getUserId());
    }

    private void switchTo(String screenName) {
        Container top = view.getTopLevelAncestor();
        if (top instanceof ScreenSwitcher switcher) {
            switcher.switchScreen(screenName);
        }
    }

    private static JButton findButtonByText(Container root, String text) {
        if (root == null) return null;
        for (Component c : root.getComponents()) {
            if (c instanceof JButton b && text.equals(b.getText())) return b;
            if (c instanceof Container child) {
                JButton found = findButtonByText(child, text);
                if (found != null) return found;
            }
        }
        return null;
    }

    private static <T extends Component> T findFirst(Container root, Class<T> type) {
        if (root == null) return null;
        for (Component c : root.getComponents()) {
            if (type.isInstance(c)) return type.cast(c);
            if (c instanceof Container child) {
                T found = findFirst(child, type);
                if (found != null) return found;
            }
        }
        return null;
    }
}