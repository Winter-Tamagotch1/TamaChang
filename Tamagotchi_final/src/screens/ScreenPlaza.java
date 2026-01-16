package screens;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Container;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.SwingConstants;

import diarytamagotchiswing.AppState;
import diarytamagotchiswing.ScreenSwitcher;

public class ScreenPlaza extends JPanel {

    private static final long serialVersionUID = 1L;

    // [MOD]
    private final AppState state = AppState.getInstance(); // [MOD]

    private JLabel lblPlaza;
    private JLabel lblPet;
    private JTextArea textArea;
    private JButton btnHome;

    private ImageIcon resizeIconKeepRatio(String path, int labelW, int labelH) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("이미지 못 찾음: " + path);
            return null;
        }

        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage();

        int imgW = icon.getIconWidth();
        int imgH = icon.getIconHeight();

        double scaleW = (double) labelW / imgW;
        double scaleH = (double) labelH / imgH;
        double scale = Math.min(scaleW, scaleH);

        int newW = (int) (imgW * scale);
        int newH = (int) (imgH * scale);

        Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

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

    // [MOD] 무인자 생성자
    public ScreenPlaza() { // [MOD]
        setLayout(null);

        JLabel lblTitle = new JLabel("마을 광장");
        lblTitle.setBounds(222, 40, 56, 14);
        add(lblTitle);

        btnHome = new JButton("🏠");
        btnHome.setBounds(547, 10, 56, 46);
        add(btnHome);

        // [MOD] ScreenSwitcher 사용
        btnHome.addActionListener(e -> { // [MOD]
            Container top = getTopLevelAncestor(); // [MOD]
            if (top instanceof ScreenSwitcher switcher) { // [MOD]
                switcher.switchScreen("main"); // [MOD]
            }
        });

        lblPlaza = new JLabel("");
        lblPlaza.setBounds(27, 76, 435, 207);
        lblPlaza.setBorder(new LineBorder(Color.BLACK));
        lblPlaza.setOpaque(true);
        lblPlaza.setBackground(SystemColor.activeCaption);
        lblPlaza.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlaza.setVerticalAlignment(SwingConstants.CENTER);

        lblPlaza.setIcon(resizeIconKeepRatio("/image/Plaza.jpg", lblPlaza.getWidth(), lblPlaza.getHeight()));
        add(lblPlaza);

        lblPet = new JLabel("");
        lblPet.setBounds(180, 200, 60, 60);
        lblPet.setIcon(resizeIcon(state.getPetImagePath(), 60, 60)); // [MOD]
        add(lblPet);

        textArea = new JTextArea();
        textArea.setBounds(488, 76, 115, 203);
        textArea.setBorder(new LineBorder(Color.BLACK));
        add(textArea);
    }

    public void refreshPet() {
        if (lblPet != null) {
            lblPet.setIcon(resizeIcon(state.getPetImagePath(), 60, 60)); // [MOD]
        }
    }
}
