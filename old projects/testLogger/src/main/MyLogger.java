package main;
import org.apache.logging.log4j.*;
import java.sql.*;

public class MyLogger {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		Logger lgr = LogManager.getRootLogger();
		lgr.info(new Exception("hello, me"));
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost/users?user=postgres&password=password&ssl=false");
		ResultSet rs = con.createStatement().executeQuery("select password from users where login='petrov'");
		rs.next();
		if(rs.getString("password").equals("password")) {
			System.out.println("login complete");
		}
		else {
			System.out.println("login failed");
		}
	}
}
