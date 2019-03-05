package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TwoPC {
	
	String ip = "127.0.0.1:5432";
	String db = "test1";
	String username = "postgres";
	String password = "password";

	public TwoPC() {
		setUp();
	}
	
	public void setUp() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection(String db) throws SQLException {
		String url = "jdbc:postgresql://"+ip+"/"+db;
		return DriverManager.getConnection(url, username, password);
	}
	
}

/*package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TwoPC {
	
	//String ip = "192.168.88.203:5432/tcp";
	String db = "test1";

	public TwoPC() throws ClassNotFoundException {
		setUp();
	}
	
	public void setUp() throws ClassNotFoundException {
		Class.forName("org.h2.Driver");
	}
	
	public Connection getH2Connection(String db_name) throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:h2:~/"+db_name, "sa", "");
		return con;
	}
	
	public Connection getH2Connection() throws SQLException {
		return getH2Connection(db);
	}
	

}*/
