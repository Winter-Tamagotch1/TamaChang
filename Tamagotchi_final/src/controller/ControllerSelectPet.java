package controller;

import java.awt.Component;
import java.awt.Container;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.InboxMessageDao;
import dao.TamagochiDao;
import dao.UserDao;
import diarytamagotchiswing.AppState;
import diarytamagotchiswing.ScreenSwitcher;
import screens.ScreenSelectPet;
import service.TamagochiService;
import service.UserService;
import tamagochi.DBConnector;
import tamagochi.User;

public class ControllerSelectPet {

    private final ScreenSelectPet view;
    private final AppState state = AppState.getInstance();

    private final UserService userService;
    private final TamagochiService tamagochiService;

    private final JButton btnSelect;
    private final JTextField petNameField;
    private final JTextField userNameField;

    public ControllerSelectPet(ScreenSelectPet view) {
        this.view = view;

        DBConnector connector = new DBConnector();
        UserDao userDao = new UserDao(connector);
        TamagochiDao tamagochiDao = new TamagochiDao(connector);
        InboxMessageDao inboxMessageDao = new InboxMessageDao(connector);

        this.userService = new UserService(userDao);
        this.tamagochiService = new TamagochiService(tamagochiDao, inboxMessageDao);

        this.btnSelect = findButtonByText(view, "선택하기");

        JTextField[] fields = findAllTextFields(view);
        this.petNameField = (fields.length > 0) ? fields[0] : null;
        this.userNameField = (fields.length > 1) ? fields[1] : null;

        bind();
    }

    private void bind() {
        if (btnSelect == null) return;

        // View에 원래 달린 리스너(화면만 넘김) 제거
        for (var al : btnSelect.getActionListeners()) {
            btnSelect.removeActionListener(al);
        }

        btnSelect.addActionListener(e -> onSelect());
    }

    private void onSelect() {
        int selectedPet = readSelectedPetFromView();
        String petName = (petNameField == null) ? "" : petNameField.getText();
        String userName = (userNameField == null) ? "" : userNameField.getText();

        if (selectedPet == 0) {
            JOptionPane.showMessageDialog(null, "펫을 선택해주세요.");
            return;
        }
        if (isBlank(petName)) {
            JOptionPane.showMessageDialog(null, "펫 이름을 입력해주세요.");
            return;
        }
        if (isBlank(userName)) {
            JOptionPane.showMessageDialog(null, "사용자 이름을 입력해주세요.");
            return;
        }

        try {
            // 1) 사용자 생성/조회
            User user = userService.getOrCreateUser(userName.trim());

            // 2) 캐릭터 생성
            // DB에는 charType/tamaImage를 일단 "image1/image2/image3"로 저장
            String charType = "image" + selectedPet;
            tamagochiService.createCharacter(
                    user.getUserId(),
                    petName.trim(),
                    charType,
                    charType
            );

            // 3) 전역 상태 저장(다른 화면이 참조)
            state.setSelectedPet(selectedPet);
            state.setPetName(petName.trim());
            state.setUserName(userName.trim());

            // 4) 메인 화면으로
            switchTo("main");

        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "DB 저장 오류\n" + ex.getMessage());
        }
    }

    private void switchTo(String screenName) {
        Container top = view.getTopLevelAncestor();
        if (top instanceof ScreenSwitcher switcher) {
            switcher.switchScreen(screenName);
        }
    }

    // ScreenSelectPet의 private selectedPet 읽기(뷰 수정 없이 컨트롤러만 수정하기 위한 방법)
    private int readSelectedPetFromView() {
        try {
            var f = view.getClass().getDeclaredField("selectedPet");
            f.setAccessible(true);
            return (int) f.get(view);
        } catch (Exception e) {
            return 0;
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
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

    private static JTextField[] findAllTextFields(Container root) {
        java.util.ArrayList<JTextField> out = new java.util.ArrayList<>();
        collectTextFields(root, out);
        return out.toArray(new JTextField[0]);
    }

    private static void collectTextFields(Container root, java.util.List<JTextField> out) {
        if (root == null) return;
        for (Component c : root.getComponents()) {
            if (c instanceof JTextField tf) out.add(tf);
            if (c instanceof Container child) collectTextFields(child, out);
        }
    }
}
