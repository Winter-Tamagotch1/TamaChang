package tamagochi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DB에 연결만
public class DBConnector {
	private static final String URL ="jdbc:mysql://localhost:3306/tamagochi";
	private static final String USER = "root";
	private static final String PASS = "7022";

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	    }
}