package tamagochi;

import java.sql.Timestamp;

//tamagochi 테이블의 데이터를 담는 객체(DTO)
public class Tamagochi {
	
	private int tamaId;
    private int userId;
    private String tamaName;
    private String charType;
    private String tamaImage;
    private int level;
    private int stress;
    private String status;
    private Timestamp runawayStartedAt;
    private Timestamp createdAt;

    public Tamagochi() {}

    public int getTamaId() {
        return tamaId;
    }

	public int getUserId() {
		return userId;
	}

	public String getTamaName() {
		return tamaName;
	}

	public String getCharType() {
		return charType;
	}

	public int getLevel() {
		return level;
	}

	public int getStress() {
		return stress;
	}

	public String getStatus() {
		return status;
	}

	public Timestamp getRunawayStartedAt() {
		return runawayStartedAt;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setTamaId(int tamaId) {
		this.tamaId = tamaId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setTamaName(String tamaName) {
		this.tamaName = tamaName;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setStress(int stress) {
		this.stress = stress;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setRunawayStartedAt(Timestamp runawayStartedAt) {
		this.runawayStartedAt = runawayStartedAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getTamaImage() {
		return tamaImage;
	}

	public void setTamaImage(String tamaImage) {
		this.tamaImage = tamaImage;
	}
    
	
	
}
