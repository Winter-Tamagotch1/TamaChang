package screens;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Chatting.NetworkManager;
import diarytamagotchiswing.MainFrame;

// NetworkManager가 다른 패키지에 있다면 import 필요
// import utils.NetworkManager; 

public class ScreenPlaza extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // UI 컴포넌트
    private JLabel lblPlaza;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JButton btnSend;
    private JButton btnHome;

    // 데이터 변수 (소켓, PW 등 통신 객체는 제거됨!)
    private String userName;
    private int keyCount = 0;
    private int backKeyCount = 0;

    // 생성자
    public ScreenPlaza(MainFrame mainFrame) {
        setLayout(null);

        // 1. 제목 라벨
        JLabel lblTitle = new JLabel("마을 광장");
        lblTitle.setBounds(109, 40, 100, 14);
        add(lblTitle);

        // 2. 홈 버튼
        btnHome = new JButton("🏠");
        btnHome.setBounds(379, 28, 45, 39);
        add(btnHome);
        
        btnHome.addActionListener(e -> {
            // 채팅 종료 로직은 이제 필요 없음 (연결은 유지하되 화면만 이동)
            // 필요하다면 리스너를 비우는 로직 추가 가능: NetworkManager.getInstance().setOnMessageReceived(null);
            mainFrame.showScreen("main"); 
        });

        // 3. 광장 이미지
        lblPlaza = new JLabel("");
        try {
            lblPlaza.setIcon(new ImageIcon(ScreenPlaza.class.getResource("/image/Plaza.jpg")));
        } catch (Exception e) {
            lblPlaza.setText("이미지 없음");
        }
        lblPlaza.setBounds(27, 76, 270, 189);
        lblPlaza.setOpaque(true);
        lblPlaza.setBackground(SystemColor.activeCaption);
        add(lblPlaza);

        // 4. 채팅 로그 영역
        scrollPane = new JScrollPane();
        scrollPane.setBounds(307, 77, 150, 188);
        add(scrollPane);

        textArea = new JTextArea();
        textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        scrollPane.setViewportView(textArea);

        // 5. 채팅 입력창
        inputField = new JTextField();
        inputField.setBounds(307, 270, 100, 25);
        add(inputField);

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                char keyCode = e.getKeyChar();
                
                if (keyCode == KeyEvent.VK_ENTER) {
                    sendMessage();
                    keyCount = 0;
                    backKeyCount = 0;
                } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                    backKeyCount++;
                } else {
                    keyCount++;
                }

                // 감정/상태 분석 로직
                if (backKeyCount >= 10) {
                    sendStatus(userName + "님이 썼다 지우기를 반복하며 망설이고 있어요...");
                    backKeyCount = 0;
                }
                if (keyCount >= 20) {
                    sendStatus(userName + "님이 열심히 입력 중입니다...");
                    keyCount = 0;
                }
            }
        });

        // 6. 전송 버튼
        btnSend = new JButton("전송");
        btnSend.setBounds(410, 270, 60, 25);
        btnSend.setFont(new Font("굴림", Font.PLAIN, 10));
        add(btnSend);
        
        btnSend.addActionListener(e -> sendMessage());
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(38, 192, 57, 62);
        add(lblNewLabel);
    }

    // ==========================================================
    //  채팅 기능 로직 (NetworkManager 사용으로 대폭 간소화)
    // ==========================================================

    /**
     * 화면이 전환될 때 MainFrame에서 호출해주는 메서드
     * Socket을 받을 필요 없이 이름만 받으면 됨
     */
    public void startChat(String userName) {
        this.userName = userName;
        textArea.setText(""); // 초기화
        textArea.append("[시스템] 광장에 입장했습니다.\n");

        // ★ 핵심: "메시지가 오면 여기다가 뿌려줘!" 하고 매니저에게 등록
        NetworkManager.getInstance().setOnMessageReceived(msg -> {
            // GUI 업데이트는 스레드 안전하게 SwingUtilities 사용
            SwingUtilities.invokeLater(() -> {
                textArea.append(msg + "\n");
                textArea.setCaretPosition(textArea.getDocument().getLength());
            });
        });
    }

    /**
     * 채팅 메시지 전송
     */
    private void sendMessage() {
        String message = inputField.getText();
        if (message.trim().isEmpty()) return;

        // ★ 핵심: 매니저를 통해 전송 (한 줄로 끝!)
        NetworkManager.getInstance().send("message:" + message);

        inputField.setText("");
        inputField.requestFocus();
    }

    /**
     * 상태 메시지 전송
     */
    private void sendStatus(String msg) {
        // ★ 핵심: 매니저를 통해 전송
        NetworkManager.getInstance().send("status:" + msg);
        inputField.requestFocus();
    }
}