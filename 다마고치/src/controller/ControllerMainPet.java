package controller;

import screens.ScreenMainPet;


public class ControllerMainPet {
	private ScreenMainPet screenMP;
	
	
	public ControllerMainPet(ScreenMainPet screenMP) {
		this.screenMP = screenMP;
		
		//캐릭터 이미지 설정
		
		this.screenMP.getBtnEat().addActionListener(e -> {
			System.out.println(character.getName() + ": 냠냠, 쩝쩝! 맛있다~");
			//나중에 스트레스 DB연결
			this.screenMP.get캐릭터라벨().setIcon(캐릭터 종류 + "이미지String");
			javax.swing.Timer timer = new javax.swing.Timer(200, event -> {
		        // 시간이 지나면 원래 이미지로 복구
				this.screenMP.get캐릭터라벨().setIcon(DB에서 이미지 가져오기);
		});
		
		this.screenMP.getBtnPlay().addActionListener(e -> {
			System.out.println(chracter.getName() + ": 와아~ 신난다!");
			this.screenMP.getStress().setValue(this.screenMP.getStress().getValue() - 10);	
		});
		
		this.screenMP.getBtnWash().addActionListener(e -> {
			System.out.println(chracter.getName() + ": 반짝반짝! 기분이 좋아졌어요!");
			this.screenMP.getStress().setValue(this.screenMP.getStress().getValue() - 10);	
		});
		
		this.screenMP.getBtnWrite().addActionListener(e -> {
			
			//일기쓴거 DB에 저장	
		});
		
		this.screenMP.getBtn광장().addActionListener(e -> {
			//광장 설정, client들어가기
		});
	}
}
