package tamagochi;

import java.sql.Timestamp;

//inbox_message 테이블 데이터를 담는 객체(DTO)
public class InboxMessage {
	
	private int inboxId;
    private int userId;
    private int tamaId;
    private String msgType;
    private String message;
    private Timestamp createdAt;
    private int isRead;        // 0 또는 1
    private Timestamp readAt;
    
    public InboxMessage() {}

    public int getInboxId() {
        return inboxId;
    }

	public int getUserId() {
		return userId;
	}

	public int getTamaId() {
		return tamaId;
	}

	public String getMsgType() {
		return msgType;
	}

	public String getMessage() {
		return message;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public int getIsRead() {
		return isRead;
	}

	public Timestamp getReadAt() {
		return readAt;
	}

	public void setInboxId(int inboxId) {
		this.inboxId = inboxId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setTamaId(int tamaId) {
		this.tamaId = tamaId;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public void setReadAt(Timestamp readAt) {
		this.readAt = readAt;
	}
    
    
    
}
