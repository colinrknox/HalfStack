package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://" + System.getenv("AWS_DB_ENDPOINT") + "/projectZero", System.getenv("AWS_USERNAME"), System.getenv("AWS_PASSWORD"));
	}
}
