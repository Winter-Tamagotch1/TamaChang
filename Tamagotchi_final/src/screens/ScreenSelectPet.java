package screens;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import diarytamagotchiswing.AppState;
import diarytamagotchiswing.ScreenSwitcher;

public class ScreenSelectPet extends JPanel {

    private static final long serialVersionUID = 1L;

    // [MOD] MainFrame에서 state를 안 넘기므로 싱글톤으로 가져오기
    private final AppState state = AppState.getInstance(); // [MOD]

    private JPanel previewPanel;

    private JButton btnpet1;
    private JButton btnpet2;
    private JButton btnpet3;

    private JButton btnSelect;

    private JLabel lblSelect;

    private int selectedPet = 0;

    private JTextField textPetName;
    private JTextField textUserName;

    private ImageIcon resizeIcon(String path, int w, int h) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("이미지 경로를 찾을 수 없음: " + path);
            return null;
        }
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // [MOD] 무인자 생성자(= MainFrame에서 new ScreenSelectPet() 가능)
    public ScreenSelectPet() { // [MOD]
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBounds(30, 30, 140, 200);
        previewPanel.setBorder(new LineBorder(Color.BLACK));
        add(previewPanel);

        lblSelect = new JLabel("", SwingConstants.CENTER);
        previewPanel.add(lblSelect, BorderLayout.CENTER);

        btnpet1 = new JButton();
        btnpet1.setBounds(198, 30, 88, 89);
        btnpet1.setIcon(resizeIcon("/image/image1.jpg", 88, 89));
        btnpet1.setFocusPainted(false);
        btnpet1.addActionListener(e -> {
            lblSelect.setIcon(resizeIcon("/image/image1.jpg", 120, 180));
            selectedPet = 1;
        });
        add(btnpet1);

        btnpet2 = new JButton();
        btnpet2.setBounds(296, 30, 88, 89);
        btnpet2.setIcon(resizeIcon("/image/image2.jpg", 88, 89));
        btnpet2.setFocusPainted(false);
        btnpet2.addActionListener(e -> {
            lblSelect.setIcon(resizeIcon("/image/image2.jpg", 120, 180));
            selectedPet = 2;
        });
        add(btnpet2);

        btnpet3 = new JButton();
        btnpet3.setBounds(394, 30, 88, 89);
        btnpet3.setIcon(resizeIcon("/image/image3.jpg", 88, 89));
        btnpet3.setFocusPainted(false);
        btnpet3.addActionListener(e -> {
            lblSelect.setIcon(resizeIcon("/image/image3.jpg", 120, 180));
            selectedPet = 3;
        });
        add(btnpet3);

        JPanel panel = new JPanel();
        panel.setBorder(null);
        panel.setBounds(180, 129, 298, 89);
        add(panel);
        panel.setLayout(null);

        JLabel lblPetNameTitle = new JLabel("이름을 지어주세요");
        lblPetNameTitle.setBounds(0, 10, 115, 14);
        panel.add(lblPetNameTitle);

        textPetName = new JTextField();
        textPetName.setBounds(114, 7, 184, 20);
        panel.add(textPetName);

        JLabel lblUserName = new JLabel("사용자이름");
        lblUserName.setBounds(24, 55, 66, 14);
        panel.add(lblUserName);

        textUserName = new JTextField();
        textUserName.setBounds(114, 52, 184, 20);
        panel.add(textUserName);

        btnSelect = new JButton("선택하기");
        btnSelect.setBounds(256, 224, 120, 35);
        add(btnSelect);

        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPet == 0) return;

                String petName = textPetName.getText();
                String userName = textUserName.getText();

                state.setSelectedPet(selectedPet);
                state.setPetName(petName);     // [MOD] 펫이름
                state.setUserName(userName);   // [MOD] 기존 버그 수정(펫이름을 두 번 set 하던 것 수정)

                // [MOD] MainFrame 직접 캐스팅 금지, ScreenSwitcher로만 전환
                Container top = getTopLevelAncestor(); // [MOD]
                if (top instanceof ScreenSwitcher switcher) { // [MOD]
                    switcher.switchScreen("main"); // [MOD]
                }
            }
        });
    }
}
