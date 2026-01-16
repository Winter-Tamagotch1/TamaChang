package screens;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Container;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Image;
import javax.swing.JTextArea;

import diarytamagotchiswing.AppState;
import diarytamagotchiswing.ScreenSwitcher;

public class ScreenMainPet extends JPanel {

    private static final long serialVersionUID = 1L;

    private final AppState state = AppState.getInstance();

    private JProgressBar stressBar;
    private JLabel lblRoom;

    private JButton btnPlay;
    private JButton btnDiary;
    private JButton btnMembers;

    private JTextArea txtInput;
    private JButton btnSave;

    private JLabel lblPet;
    
    // [추가] 대화 로그를 보여줄 텍스트 영역
    private JTextArea txtTalkLog; 

    private ImageIcon resizeIcon(String path, int w, int h) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("이미지 못 찾음: " + path);
            return null;
        }
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public ScreenMainPet() {
        setLayout(null);

        JLabel lblStress = new JLabel("스트레스 지수");
        lblStress.setBounds(20, 71, 78, 14);
        add(lblStress);

        stressBar = new JProgressBar();
        stressBar.setBounds(47, 95, 24, 146);
        stressBar.setOrientation(SwingConstants.VERTICAL);
        stressBar.setValue(0);
        add(stressBar);

        lblRoom = new JLabel("");
        lblRoom.setBounds(134, 78, 131, 163);
        lblRoom.setBorder(new LineBorder(Color.BLACK));
        lblRoom.setIcon(resizeIcon("/image/Home.jpg", 131, 163));
        lblRoom.setLayout(null);
        add(lblRoom);

        lblPet = new JLabel("");
        lblPet.setBounds(35, 70, 60, 60);
        lblRoom.add(lblPet);

        refreshPetIcon();

        JLabel lblTitle = new JLabel("우리 집");
        lblTitle.setBounds(171, 39, 56, 14);
        add(lblTitle);

        JLabel lblLevel = new JLabel("LV.1");
        lblLevel.setOpaque(true);
        lblLevel.setBackground(SystemColor.activeCaption);
        lblLevel.setBounds(134, 79, 45, 14);
        add(lblLevel);

        // ==========================================
        // [수정] 대화 로그 영역 (우측 상단)
        // ==========================================
        JScrollPane scrollTalk = new JScrollPane();
        scrollTalk.setBounds(299, 78, 182, 112);
        scrollTalk.setBorder(new LineBorder(new Color(130,135,144),2,true));
        add(scrollTalk);
        
        // JTextArea 생성 및 설정
        txtTalkLog = new JTextArea();
        txtTalkLog.setEditable(false); // 사용자가 수정 못하게
        txtTalkLog.setLineWrap(true);  // 자동 줄바꿈
        scrollTalk.setViewportView(txtTalkLog); // 스크롤 안에 넣기
        // ==========================================

        btnPlay = new JButton();
        btnPlay.setBounds(285, 25, 45, 45);
        btnPlay.setIcon(resizeIcon("/image/Play.jpg", 45, 45));
        btnPlay.setMargin(new Insets(0,0,0,0));
        btnPlay.setBorderPainted(false);
        btnPlay.setContentAreaFilled(false);
        add(btnPlay);

        btnDiary = new JButton();
        btnDiary.setBounds(340, 25, 45, 45);
        btnDiary.setIcon(resizeIcon("/image/Diary.png", 45, 45));
        btnDiary.setMargin(new Insets(0,0,0,0));
        btnDiary.setBorderPainted(false);
        btnDiary.setContentAreaFilled(false);
        add(btnDiary);

        btnMembers = new JButton();
        btnMembers.setBounds(395, 25, 45, 45);
        btnMembers.setIcon(resizeIcon("/image/members.jpg", 45, 45));
        btnMembers.setMargin(new Insets(0,0,0,0));
        btnMembers.setBorderPainted(false);
        btnMembers.setContentAreaFilled(false);
        add(btnMembers);

        JButton btnEat = new JButton("밥 먹이기");
        btnEat.setBounds(20, 268, 94, 22);
        add(btnEat);

        JButton btnPlayPet = new JButton("놀아주기");
        btnPlayPet.setBounds(171, 268, 94, 22);
        add(btnPlayPet);

        JButton btnWash = new JButton("씻기기");
        btnWash.setBounds(306, 268, 94, 22);
        add(btnWash);

        JScrollPane scrollInput = new JScrollPane();
        scrollInput.setBounds(299, 197, 110, 60);
        scrollInput.setBorder(new LineBorder(new Color(130,135,144),2,true));
        add(scrollInput);

        txtInput = new JTextArea();
        txtInput.setLineWrap(true);
        txtInput.setWrapStyleWord(true);
        scrollInput.setViewportView(txtInput);

        btnSave = new JButton("저장");
        btnSave.setBounds(412, 200, 69, 57);
        add(btnSave);

        btnPlay.addActionListener(e -> switchScreen("plaza"));
        btnDiary.addActionListener(e -> switchScreen("diary"));
        btnMembers.addActionListener(e -> switchScreen("memories"));

        // Controller에서 로직 처리하므로 여기는 비워둠
        btnEat.addActionListener(e -> {}); 
        btnPlayPet.addActionListener(e -> {}); 
        btnWash.addActionListener(e -> {}); 

        btnSave.addActionListener(e -> {
            // txtInput.setText(""); // Controller가 처리
        });
    }

    public JButton getBtnSave() { return btnSave; }
    public String getDiaryInputText() { return txtInput.getText(); }
    public void clearDiaryInput() { txtInput.setText(""); }

    // [추가] 로그 추가 메서드 (Controller가 호출함)
    public void appendLog(String msg) {
        txtTalkLog.append(msg + "\n");
        // 스크롤 항상 맨 아래로
        txtTalkLog.setCaretPosition(txtTalkLog.getDocument().getLength());
    }

    private void refreshPetIcon() {
        String path = state.getPetImagePath();
        lblPet.setIcon(resizeIcon(path, 60, 60));
    }

    private void switchScreen(String name) {
        refreshPetIcon();
        Container top = getTopLevelAncestor();
        if (top instanceof ScreenSwitcher switcher) {
            switcher.switchScreen(name);
        }
    }
}