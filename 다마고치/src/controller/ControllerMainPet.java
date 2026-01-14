package controller;

import screens.ScreenMainPet;

public class ControllerMainPet {
	private ScreenMainPet screenMP;
	
	
	public ControllerMainPet(ScreenMainPet screenMP) {
		this.screenMP = screenMP;
		
		//캐릭터 이미지 설정
		
		this.screenMP.getBtnEat().addActionListener(e -> {
			//생성한 다마고치 불러와서 eat
			//나중에 스트레스 DB연결
			this.screenMP.getStress().setValue(this.screenMP.getStress().getValue() - 10);	
		});
		
		this.screenMP.getBtnPlay().addActionListener(e -> {
			
			this.screenMP.getStress().setValue(this.screenMP.getStress().getValue() - 10);	
		});
		
		this.screenMP.getBtnWash().addActionListener(e -> {
			
			this.screenMP.getStress().setValue(this.screenMP.getStress().getValue() - 10);	
		});
		
		this.screenMP.getBtnWrite().addActionListener(e -> {
			
			//화면 전환	
		});
		
		this.screenMP.getBtnDiary().addActionListener(e -> {
			
			//화면 전환	
		});
		
		this.screenMP.getBtn광장().addActionListener(e -> {
			
		});
	}
}
