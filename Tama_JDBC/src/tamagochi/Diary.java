package tamagochi;

import java.sql.Date;
import java.sql.Timestamp;


//diary 테이블의 데이터를 담는 객체(DTO)
public class Diary {
	
	private int diaryId;
    private int tamaId;
    private String content;
    private Timestamp writtenAt;
    private Date dayKey;
    
    public Diary() {}

    public int getDiaryId() {
        return diaryId;
    }

	public int getTamaId() {
		return tamaId;
	}

	public String getContent() {
		return content;
	}

	public Timestamp getWrittenAt() {
		return writtenAt;
	}

	public Date getDayKey() {
		return dayKey;
	}

	public void setDiaryId(int diaryId) {
		this.diaryId = diaryId;
	}

	public void setTamaId(int tamaId) {
		this.tamaId = tamaId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setWrittenAt(Timestamp writtenAt) {
		this.writtenAt = writtenAt;
	}

	public void setDayKey(Date dayKey) {
		this.dayKey = dayKey;
	}
	
    

}
