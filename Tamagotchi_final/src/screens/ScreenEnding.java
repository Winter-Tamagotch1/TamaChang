package screens;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;

public class ScreenEnding extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblEndingImage;
	private JLabel lblEndingTitle;

	public ScreenEnding() {
		setLayout(null);
		
		lblEndingImage = new JLabel("Ending Image");
		lblEndingImage.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblEndingImage.setBounds(139, 75, 161, 155);
		add(lblEndingImage);
		
		lblEndingTitle = new JLabel("잊지 못할 거야, 안녕~");
		lblEndingTitle.setBounds(139, 39, 196, 14);
		add(lblEndingTitle);
		
		JButton btnNextPet = new JButton("새로 입장하기");
		btnNextPet.setBounds(285, 252, 111, 22);
		add(btnNextPet);

	}

	public void showGraduation(String tamaName, String tamaImage, int level) {
		// TODO Auto-generated method stub
		
	}

	public void showMessage(String string) {
		// TODO Auto-generated method stub
		
	}

}
