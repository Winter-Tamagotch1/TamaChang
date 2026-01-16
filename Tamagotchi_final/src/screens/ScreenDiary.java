package screens;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import diarytamagotchiswing.ScreenSwitcher;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Container;
import java.util.List;

public class ScreenDiary extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextArea leftTEXT;
    private JTextArea rightTEXT;

    public ScreenDiary() {
        setLayout(null);

        JLabel lblTitle = new JLabel("일기장");
        lblTitle.setBounds(212, 35, 56, 14);
        add(lblTitle);

        JButton btnHome = new JButton("🏠");
        btnHome.setBounds(376, 25, 46, 34);
        add(btnHome);

        // [MOD] MainFrame을 직접 캐스팅하지 않고 ScreenSwitcher로만 화면 전환
        btnHome.addActionListener(e -> { // [MOD]
            Container top = getTopLevelAncestor(); // [MOD]
            if (top instanceof ScreenSwitcher switcher) { // [MOD]
                switcher.switchScreen("main"); // [MOD]
            }
        });

        JScrollPane leftDiary = new JScrollPane();
        leftDiary.setBounds(43, 86, 171, 172);
        leftDiary.setBorder(new LineBorder(new Color(130, 135, 144), 2, true));
        add(leftDiary);

        leftTEXT = new JTextArea();
        leftTEXT.setEditable(false);
        leftTEXT.setLineWrap(true);
        leftTEXT.setWrapStyleWord(true);
        leftDiary.setViewportView(leftTEXT);

        JScrollPane rightDiary = new JScrollPane();
        rightDiary.setBounds(242, 86, 171, 172);
        rightDiary.setBorder(new LineBorder(new Color(130, 135, 144), 2, true));
        add(rightDiary);

        rightTEXT = new JTextArea();
        rightTEXT.setEditable(false);
        rightTEXT.setLineWrap(true);
        rightTEXT.setWrapStyleWord(true);
        rightDiary.setViewportView(rightTEXT);
    }

    // Controller/Service가 조회한 일기 목록을 그대로 주입받아 표시만 담당
    public void setDiaryEntries(List<String> list) {
        if (list == null) {
            leftTEXT.setText("");
            rightTEXT.setText("");
            return;
        }

        int n = list.size();
        int mid = (n + 1) / 2;

        StringBuilder left = new StringBuilder();
        for (int i = 0; i < mid; i++) {
            left.append("• ").append(list.get(i)).append("\n");
        }

        StringBuilder right = new StringBuilder();
        for (int i = mid; i < n; i++) {
            right.append("• ").append(list.get(i)).append("\n");
        }

        leftTEXT.setText(left.toString());
        rightTEXT.setText(right.toString());
    }
}
