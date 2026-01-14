package screens;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class ScreenDiary extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ScreenDiary() {
		setLayout(null);
		
		JTextField textDiar = new JTextField();
		textDiar.setText("일기장");
		textDiar.setBounds(197, 25, 65, 20);
		add(textDiar);
		textDiar.setColumns(10);
		
		JPanel DiaryLeft = new JPanel();
		DiaryLeft.setBorder(new LineBorder(new Color(0, 0, 0)));
		DiaryLeft.setBounds(48, 82, 161, 180);
		add(DiaryLeft);
		
		JPanel DiaryRight = new JPanel();
		DiaryRight.setBorder(new LineBorder(new Color(0, 0, 0)));
		DiaryRight.setBounds(253, 82, 161, 180);
		add(DiaryRight);

	}
}
