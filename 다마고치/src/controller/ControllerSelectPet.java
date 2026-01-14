package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import diarytamagotchiswing.MainFrame;
import screens.ScreenSelectPet;

public class ControllerSelectPet {
	private ScreenSwitcher switcher;
	private Model model;
	private ScreenSelectPet screanSP;
	private javax.swing.Icon Img;
	private MainFrame mainframe;
	
	public ControllerSelectPet(Model model, ScreenSelectPet screansSP) {
		this.model = model;
		this.screanSP =  screanSP;
		
		//이미지 바뀌는거 똑같아서 메소드로 만들면 좋을듯
		this.screanSP.getBtnpet1().addActionListener(e -> {
			this.Img = this.screanSP.getBtnpet1().getIcon();
			this.screanSP.getLblselect().setIcon(Img);
		});
		
		this.screanSP.getBtnpet2().addActionListener(e -> {
			this.Img = this.screanSP.getBtnpet1().getIcon();
			this.screanSP.getLblselect().setIcon(Img);
		});
		
		this.screanSP.getBtnpet3().addActionListener(e -> {
			this.Img = this.screanSP.getBtnpet1().getIcon();
			this.screanSP.getLblselect().setIcon(Img);
		});
		
		this.screanSP.getBtnSelect().addActionListener(e -> {
			//화면전환
			//이름받아서 DB에 캐릭터 추가
			//메인 캐릭터 이미지 큰화면에서 get해와서 DB에 저장
		});
	}
}
