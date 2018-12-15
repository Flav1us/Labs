package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersDatabase {
	
	static {
		//JDBC pool в качестве альтернативы: http://people.apache.org/~fhanik/jdbc-pool/jdbc-pool.html
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e) {
			//TODO logger
			e.printStackTrace();
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
			//TODO logger
			e.printStackTrace();
		}
		finally {
			if(st != null) {
				try {
					st.close();
				}
				catch (SQLException e) {
					//TODO logger
					e.printStackTrace();
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
			// TODO logger
			e.printStackTrace();
		}
		return con;
	}
	
	public static void closeConnection(Connection con) {	
		if(con != null) {
			try {
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
				//TODO logger
			}
			finally {
				con = null;
			}
		}
	}
	
	
	
}
