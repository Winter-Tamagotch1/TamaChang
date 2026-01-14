package tamagochi;

import java.sql.SQLException;

/*
  계획서 1번: 사용자 이름 입력으로 사용자 생성/조회

  규칙
  - username이 이미 존재하면 기존 사용자 반환
  - 없으면 users에 신규 생성 후 반환
*/
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getOrCreateUser(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("username이 비어있습니다.");
        }

        User found = userDao.findByUsername(username.trim());
        if (found != null) return found;

        return userDao.insert(username.trim());
    }
}

