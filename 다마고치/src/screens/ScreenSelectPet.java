package screens;

import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;

public class ScreenSelectPet extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel previewPanel;
	private JButton btnpet3;
	private JButton btnpet2;
	private JButton btnpet1;
	private JButton btnSelect;
	private JLabel lblselect;

	/**
	 * Create the panel.
	 */
	public ScreenSelectPet() {
		setLayout(null);
		
		previewPanel = new JPanel();
		previewPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		previewPanel.setBackground(SystemColor.activeCaption);
		previewPanel.setBounds(45, 27, 123, 196);
		add(previewPanel);
		previewPanel.setLayout(new BorderLayout(0, 0));
		
		lblselect = new JLabel("");
		previewPanel.add(lblselect);
		
		btnpet1 = new JButton("image1");
		btnpet1.setIcon(new ImageIcon(ScreenSelectPet.class.getResource("/image/image-1.jpg")));
		btnpet1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnpet1.setBounds(178, 86, 66, 66);
		add(btnpet1);
		
		btnpet2 = new JButton("image2");
		btnpet2.setIcon(new ImageIcon(ScreenSelectPet.class.getResource("/image/image-2.jpg")));
		btnpet2.setBounds(254, 86, 66, 66);
		add(btnpet2);
		
		btnpet3 = new JButton("image3");
		btnpet3.setIcon(new ImageIcon(ScreenSelectPet.class.getResource("/image/image-3.jpg")));
		btnpet3.setBounds(333, 86, 66, 66);
		add(btnpet3);
		
		btnSelect = new JButton("선택하기");
		btnSelect.setBounds(240, 182, 95, 31);
		add(btnSelect);

	}
	
	public JButton getBtnpet3() {
		return btnpet3;
	}

	public void setBtnpet3(JButton btnpet3) {
		this.btnpet3 = btnpet3;
	}

	public JButton getBtnpet2() {
		return btnpet2;
	}

	public void setBtnpet2(JButton btnpet2) {
		this.btnpet2 = btnpet2;
	}

	public JButton getBtnpet1() {
		return btnpet1;
	}

	public void setBtnpet1(JButton btnpet1) {
		this.btnpet1 = btnpet1;
	}

	public JButton getBtnSelect() {
		return btnSelect;
	}

	public void setBtnSelect(JButton btnSelect) {
		this.btnSelect = btnSelect;
	}

	public JLabel getLblselect() {
		return lblselect;
	}

	public void setLblselect(JLabel lblselect) {
		this.lblselect = lblselect;
	}
}
