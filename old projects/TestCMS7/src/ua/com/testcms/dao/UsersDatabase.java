package ua.com.testcms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.*;

public class UsersDatabase {
	static Logger logger = LogManager.getRootLogger();
	
	static {
		//JDBC pool в качестве альтернативы: http://people.apache.org/~fhanik/jdbc-pool/jdbc-pool.html
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e) {
			logger.error(e);
		}
	}
	
	public static ResultSet executeStatement(String sqlStatement) {
		ResultSet rs = null;
		Statement st = null;
		Connection con = openConnection();
		try {	
			st = con.createStatement();
			rs = st.executeQuery(sqlStatement);
		}
		catch (SQLException e) {
			logger.error(e);
		}
		finally {
			if(st != null) {
				try {
					st.close();
				}
				catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		closeConnection(con);
		return rs;
	}
	
	public static Connection openConnection(String ... credentials) { 
		 //database name, username, password
		String url = "jdbc:postgresql://localhost/users?user=postgres&password=password&ssl=false";
		/*if (credentials[0] == null || credentials[1] == null || credentials[2] == null) {
			url = "jdbc:postgresql://localhost/users?user=postgres&password=password&ssl=false";
		} else {
			url = "jdbc:postgresql://localhost/" + credentials[0] + "?user=" + credentials[1] + "&password=" + credentials[2] + "&ssl=false";
		}*/
		if(credentials.length == 3) {
			if(credentials[0] != null && credentials[1] != null && credentials[2] != null) {
				url = "jdbc:postgresql://localhost/" + credentials[0] + "?user=" + credentials[1] + "&password=" + credentials[2] + "&ssl=false";
			}
		}
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
		} catch (SQLException e) {
			logger.error(e);
		}
		return con;
	}
	
	public static void closeConnection(Connection con) {	
		if(con != null) {
			try {
				con.close();
			}
			catch (SQLException e) {
				logger.error(e);
			}
			finally {
				con = null;
			}
		}
	}
	
	
	
}
