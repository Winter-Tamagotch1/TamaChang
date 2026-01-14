package controller;

import javax.swing.ImageIcon;

import screens.ScreenMemories;

public class ControllerMemories {
	private ScreenMemories screenMM;
	
	
	public ControllerMemories(ScreenMemories screenMM) {
		this.screenMM = screenMM;
		
		//캐릭터 이미지 불러오기?? 캐릭터 이름 받아와서 이미지 설정?
		this.screenMM.getBtn().setIcon(new ImageIcon(ScreenMemories.class.getResource(//캐릭터 이미지"/image/image-1.jpg")))
		
		this.screenMM.getBtn().addActionListener(e -> {
			//화면 넘어가고 일기장에 DB불러오기?
		});

}
