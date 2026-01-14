package controller;

import screens.ScreenEnding;
import screens.ScreenMainPet;

public class ControllerEnding {
	private ScreenSwitcher switcher;
	private ScreenEnding screenED;
	
	public ControllerEnding(ScreenEnding screenED) {
		this.screenED = screenED;
		
		//캐릭터 사진 넣기
		
		this.screenED.getBtnNextPet().addActionListener(e -> {			
			switcher.switchScreen("select");
		});
		}
}
