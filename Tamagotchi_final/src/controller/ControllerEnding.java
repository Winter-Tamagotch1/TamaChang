package controller;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;

import diarytamagotchiswing.ScreenSwitcher;
import screens.ScreenEnding;

public class ControllerEnding {

    private final ScreenEnding view;

    private final JLabel lblTitle;
    private final JLabel lblImage;
    private final JButton btnNext;

    public ControllerEnding(ScreenEnding view) {
        this.view = view;

        this.lblTitle = findFirst(view, JLabel.class, 0);
        this.lblImage = findFirst(view, JLabel.class, 1); 
        this.btnNext = findButtonByText(view, "새로 입장하기");

        bind();
    }

    private void bind() {
        if (btnNext == null) return;

        btnNext.addActionListener(e -> {
            Container top = view.getTopLevelAncestor();
            if (top instanceof ScreenSwitcher switcher) {
                switcher.switchScreen("select");
            }
        });
    }

    public void showEndingMessage(String msg) {
        if (lblTitle != null) lblTitle.setText(msg);
    }

    public void showImageText(String text) {
        if (lblImage != null) lblImage.setText(text);
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

    private static <T extends Component> T findFirst(Container root, Class<T> type, int index) {
        java.util.ArrayList<T> found = new java.util.ArrayList<>();
        collect(root, type, found);
        if (index < 0 || index >= found.size()) return null;
        return found.get(index);
    }

    private static <T extends Component> void collect(Container root, Class<T> type, java.util.List<T> out) {
        if (root == null) return;
        for (Component c : root.getComponents()) {
            if (type.isInstance(c)) out.add(type.cast(c));
            if (c instanceof Container child) collect(child, type, out);
        }
    }
}
