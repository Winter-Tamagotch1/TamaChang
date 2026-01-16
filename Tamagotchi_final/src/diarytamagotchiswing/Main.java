package diarytamagotchiswing;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.ControllerDiary;
import controller.ControllerEnding;
import controller.ControllerMainPet;
import controller.ControllerMemories;
import controller.ControllerSelectPet;

import screens.ScreenDiary;
import screens.ScreenEnding;
import screens.ScreenMainPet;
import screens.ScreenMemories;
import screens.ScreenPlaza;
import screens.ScreenSelectPet;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            AppState.getInstance();

            MainFrame frame = new MainFrame();

            JPanel cardPanel = getPrivateField(frame, "cardPanel", JPanel.class);

            ScreenSelectPet screenSelect = findScreen(cardPanel, ScreenSelectPet.class);
            ScreenMainPet screenMain = findScreen(cardPanel, ScreenMainPet.class);
            ScreenDiary screenDiary = findScreen(cardPanel, ScreenDiary.class);
            ScreenMemories screenMemories = findScreen(cardPanel, ScreenMemories.class);
            ScreenPlaza screenPlaza = findScreen(cardPanel, ScreenPlaza.class);

            new ControllerSelectPet(screenSelect);
            new ControllerMainPet(screenMain);
            new ControllerDiary(screenDiary);
            new ControllerMemories(screenMemories);


            ScreenEnding screenEnding = findScreen(cardPanel, ScreenEnding.class);
            if (screenEnding != null) new ControllerEnding(screenEnding);

            frame.switchScreen("select");
            frame.setVisible(true);
        });
    }

    private static <T> T getPrivateField(Object target, String fieldName, Class<T> type) {
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            Object v = f.get(target);
            return type.cast(v);
        } catch (Exception e) {
            throw new RuntimeException("MainFrame에서 " + fieldName + " 필드 접근 실패", e);
        }
    }

    private static <T extends Component> T findScreen(JPanel cardPanel, Class<T> clazz) {
        if (cardPanel == null) return null;
        for (Component c : cardPanel.getComponents()) {
            if (clazz.isInstance(c)) return clazz.cast(c);
        }
        return null;
    }
}
