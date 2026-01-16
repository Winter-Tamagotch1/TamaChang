package screens;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import diarytamagotchiswing.ScreenSwitcher;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Container;
import javax.swing.ImageIcon;

public class ScreenMemories extends JPanel {

    private static final long serialVersionUID = 1L;

    public ScreenMemories() {
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel.setBounds(28, 59, 92, 104);
        add(panel);
        panel.setLayout(null);

        JLabel lblPET1 = new JLabel("");
        lblPET1.setIcon(new ImageIcon(ScreenMemories.class.getResource("/image/EndingImage1.png")));
        lblPET1.setBounds(0, 0, 92, 104);
        panel.add(lblPET1);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBounds(160, 57, 92, 104);
        add(panel_1);
        panel_1.setLayout(null);

        JLabel lblPET2 = new JLabel("");
        lblPET2.setIcon(new ImageIcon(ScreenMemories.class.getResource("/image/EndingImage2.png")));
        lblPET2.setBounds(0, 0, 92, 104);
        panel_1.add(lblPET2);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_2.setBounds(289, 57, 92, 104);
        add(panel_2);
        panel_2.setLayout(null);

        JLabel lblPET3 = new JLabel("");
        lblPET3.setIcon(new ImageIcon(ScreenMemories.class.getResource("/image/EndingImage3.png")));
        lblPET3.setBounds(0, 0, 92, 104);
        panel_2.add(lblPET3);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_3.setBounds(28, 176, 92, 104);
        add(panel_3);
        panel_3.setLayout(null);

        JLabel lblPET4 = new JLabel("");
        lblPET4.setBounds(0, 0, 92, 104);
        panel_3.add(lblPET4);

        JPanel panel_4 = new JPanel();
        panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_4.setBounds(160, 176, 92, 104);
        add(panel_4);
        panel_4.setLayout(null);

        JLabel lblPET5 = new JLabel("");
        lblPET5.setBounds(0, 0, 92, 104);
        panel_4.add(lblPET5);

        JPanel panel_5 = new JPanel();
        panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_5.setBounds(289, 176, 92, 104);
        add(panel_5);
        panel_5.setLayout(null);

        JLabel lblPET6 = new JLabel("");
        lblPET6.setBounds(0, 0, 92, 104);
        panel_5.add(lblPET6);

        JLabel lblTitle = new JLabel("함께한 추억들");
        lblTitle.setBounds(160, 22, 83, 14);
        add(lblTitle);

        JButton btnNHome = new JButton("🏠");
        btnNHome.setBounds(380, 10, 45, 40);
        add(btnNHome);

        // [MOD] ScreenSwitcher 사용
        btnNHome.addActionListener(e -> { // [MOD]
            Container top = getTopLevelAncestor(); // [MOD]
            if (top instanceof ScreenSwitcher switcher) { // [MOD]
                switcher.switchScreen("main"); // [MOD]
            }
        });
    }
}
