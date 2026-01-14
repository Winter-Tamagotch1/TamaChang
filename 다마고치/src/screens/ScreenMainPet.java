package screens;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class ScreenMainPet extends JPanel {

	private static final long serialVersionUID = 1L;
	private JProgressBar Stress;
	private JLabel lblRoom;
	private JButton btnEat;
	private JButton btnPlay;
	private JButton btnWash;
	private JScrollPane scrollPane;
	private JButton btnWrite;
	private JLabel lblLevel;
	private JLabel lbltextStressTitle;
	private JButton btnDiary;

	/**
	 * Create the panel.
	 */
	public ScreenMainPet() {
		setLayout(null);
		
		lbltextStressTitle = new JLabel("스트레스 지수");
		lbltextStressTitle.setBounds(20, 54, 78, 14);
		add(lbltextStressTitle);
		
		lblRoom = new JLabel((String) null);
		lblRoom.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblRoom.setBounds(134, 78, 131, 163);
		add(lblRoom);
		
		Stress = new JProgressBar();
		Stress.setBounds(47, 78, 12, 163);
		Stress.setOrientation(SwingConstants.VERTICAL);
		Stress.setValue(60);
		add(Stress);
		
		lblLevel = new JLabel("LV.1");
		lblLevel.setBounds(134, 54, 93, 14);
		add(lblLevel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(153, 176, 56, 14);
		add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(130, 135, 144), 2, true));
		scrollPane.setBounds(299, 78, 101, 120);
		add(scrollPane);
		
		btnDiary = new JButton("📚");
		btnDiary.setBounds(386, 10, 54, 50);
		add(btnDiary);
		
		btnWrite = new JButton("✏️");
		btnWrite.setBounds(322, 208, 56, 22);
		add(btnWrite);
		
		btnEat = new JButton("밥 먹이기");
		btnEat.setBounds(20, 268, 94, 22);
		add(btnEat);
		
		btnPlay = new JButton("놀아주기");
		btnPlay.setBounds(171, 268, 94, 22);
		add(btnPlay);
		
		btnWash = new JButton("씻기기");
		btnWash.setBounds(306, 268, 94, 22);
		add(btnWash);

	}

	public JButton getBtnEat() {
		return btnEat;
	}

	public void setBtnEat(JButton btnEat) {
		this.btnEat = btnEat;
	}

	public JButton getBtnPlay() {
		return btnPlay;
	}

	public void setBtnPlay(JButton btnPlay) {
		this.btnPlay = btnPlay;
	}

	public JButton getBtnWash() {
		return btnWash;
	}

	public void setBtnWash(JButton btnWash) {
		this.btnWash = btnWash;
	}

	public JButton getBtnWrite() {
		return btnWrite;
	}

	public void setBtnWrite(JButton btnWrite) {
		this.btnWrite = btnWrite;
	}

	public JLabel getLblLevel() {
		return lblLevel;
	}

	public void setLblLevel(JLabel lblLevel) {
		this.lblLevel = lblLevel;
	}

	public JProgressBar getStress() {
		return Stress;
	}

	public void setStress(JProgressBar stress) {
		Stress = stress;
	}

	public JButton getBtnDiary() {
		return btnDiary;
	}

	public void setBtnDirary(JButton btnDiary) {
		this.btnDiary = btnDiary;
	}
}
