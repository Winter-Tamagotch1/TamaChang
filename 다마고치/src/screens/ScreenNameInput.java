package screens;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ScreenNameInput extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtName;

	/**
	 * Create the panel.
	 */
	public ScreenNameInput() {
		setLayout(null);
		
		JPanel Rename = new JPanel();
		Rename.setBorder(new LineBorder(new Color(0, 0, 0)));
		Rename.setBounds(82, 63, 293, 155);
		add(Rename);
		Rename.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("이름을 지어주세요");
		lblNewLabel.setBounds(99, 21, 111, 14);
		Rename.add(lblNewLabel);
		
		txtName = new JTextField();
		txtName.setBounds(44, 54, 208, 30);
		Rename.add(txtName);
		txtName.setColumns(10);
		
		JButton btnNameSelect = new JButton("입력하기");
		btnNameSelect.setBounds(101, 110, 94, 22);
		Rename.add(btnNameSelect);

	}
}
