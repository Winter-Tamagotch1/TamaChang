package 캐릭터;

public class Character {
	private String chaImg;
	private String name;
	
	public void Character(String name, String chaImg) {
		this.name = name;
		this.chaImg = chaImg;
	}
	
	public void eat(Character character) {
		System.out.println(character.getName() + ": 냠냠, 쩝쩝! 맛있다~");
		//스트레스바 낮추기
		//이미지 변경하기
	}
	
	public void play(Character chracter) {
		System.out.println(chracter.getName() + ": 와아~ 신난다!");
	}
	
	public void wash(Character chracter) {
		System.out.println(chracter.getName() + ": 반짝반짝! 기분이 좋아졌어요!");
	}

	public String getChaImg() {
		return chaImg;
	}

	public void setChaImg(String chaImg) {
		this.chaImg = chaImg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}



//이름을 받고 그이름으로 만들어
// 선택화면에서 get이미지 그이미지로 set
