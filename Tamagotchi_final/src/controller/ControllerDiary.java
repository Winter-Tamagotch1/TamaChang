package controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import dao.DiaryDao;
import dao.InboxMessageDao;
import dao.TamagochiDao;
import dao.UserDao;
import diarytamagotchiswing.AppState;
import screens.ScreenDiary;
import service.DiaryService;
import service.TamagochiService;
import service.UserService;
import tamagochi.DBConnector;
import tamagochi.Tamagochi;
import tamagochi.User;

public class ControllerDiary {

    private final ScreenDiary view;
    private final AppState state = AppState.getInstance();

    private final UserService userService;
    private final TamagochiService tamagochiService;
    @SuppressWarnings("unused")
    private final DiaryService diaryService;

    @SuppressWarnings("unused")
    private final DiaryDao diaryDao;

    public ControllerDiary(ScreenDiary view) {
        this.view = view;

        DBConnector connector = new DBConnector();
        UserDao userDao = new UserDao(connector);
        TamagochiDao tamagochiDao = new TamagochiDao(connector);
        InboxMessageDao inboxMessageDao = new InboxMessageDao(connector);
        this.diaryDao = new DiaryDao(connector);

        this.userService = new UserService(userDao);
        this.tamagochiService = new TamagochiService(tamagochiDao, inboxMessageDao);
        this.diaryService = new DiaryService(diaryDao, tamagochiDao, inboxMessageDao);

        loadAndRender();
    }

    public void loadAndRender() {
        try {
            Tamagochi t = getMyActiveTamagochi();
            if (t == null) {
                view.setDiaryEntries(Collections.emptyList());
                return;
            }

            List<String> entries = Collections.emptyList();

            view.setDiaryEntries(entries);

        } catch (SQLException ex) {
            ex.printStackTrace();
            view.setDiaryEntries(Collections.emptyList());
        }
    }

    private Tamagochi getMyActiveTamagochi() throws SQLException {
        String username = state.getUserName();
        if (username == null || username.trim().isEmpty()) return null;

        User u = userService.getOrCreateUser(username.trim());
        return tamagochiService.getActiveCharacter(u.getUserId());
    }
}
