package tamagochi;

import java.sql.SQLException;
import java.util.List;

/*
  inbox_messages 기능 서비스

  계획서 연결
  - 2번: 10초 간격 랜덤 문장 생성 결과를 저장해두고, 사용자에게 보여줄 수 있음
  - 6번: 21시 프롬프트도 inbox에 저장 가능
*/
public class InboxMessageService {

    private final InboxMessageDao inboxMessageDao;

    public InboxMessageService(InboxMessageDao inboxMessageDao) {
        this.inboxMessageDao = inboxMessageDao;
    }

    public void saveMessage(int userId, int tamaId, String msgType, String message) throws SQLException {
        InboxMessage m = new InboxMessage();
        m.setUserId(userId);
        m.setTamaId(tamaId);
        m.setMsgType(msgType);
        m.setMessage(message);
        inboxMessageDao.insert(m);
    }

    public List<InboxMessage> getUnreadMessages(int userId) throws SQLException {
        return inboxMessageDao.findUnreadByUserId(userId);
    }

    public void readOne(int inboxId) throws SQLException {
        inboxMessageDao.markRead(inboxId);
    }

    public void readAll(int userId) throws SQLException {
        inboxMessageDao.markAllUnreadAsRead(userId);
    }
}