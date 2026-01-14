package tamagochi;

//User 테이블의 데이터를 담는 객체(DTO)
public class User {
	
	private int userId;
    private String username;

    public User() {}

    public User(String username) {
        this.username = username;
    }

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
