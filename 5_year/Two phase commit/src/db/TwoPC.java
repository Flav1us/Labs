package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TwoPC {
	
	static String username = "postgres";
	static String password = "password";
	static String fly_trans_name = "insert_fly";
	static String hotel_trans_name = "insert_hotel";
	
	static { try { Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) { e.printStackTrace(); } }
	
	Connection c1 = getConnection("db1_fly");
	Connection c2 = getConnection("db2_hotel");

	public boolean create_trip(String name, String flight_number, String from, String to, String arrival_date,
			String hotel, String departure_date) throws SQLException {

		String prepare_fly = "BEGIN;"
				+ "insert into fly_booking(name, fly_number, fromm, toooo, date) "
				+ String.format("values('%s', '%s', '%s', '%s', '%s');", name, flight_number, from, to, arrival_date)
				+ "PREPARE TRANSACTION '"+fly_trans_name+"';";
				

		String prepare_hotel = "BEGIN;"
				+ "insert into hotel_booking(client_name, hotel_name, arrival, departure) "
				+ String.format("values('%s', '%s', '%s', '%s');", name, hotel, arrival_date, departure_date)
				+ "PREPARE TRANSACTION '"+hotel_trans_name+"';";
	
		
		Statement st1 = c1.createStatement();
		Statement st2 = c2.createStatement();

		try {
			st1.executeUpdate(prepare_fly);	
		} catch (SQLException e) {
			st1.executeUpdate("ROLLBACK PREPARED '" + fly_trans_name + "';");
			e.printStackTrace();
			return false;
		}
		
		try {
			st2.executeUpdate(prepare_hotel);	
		} catch (SQLException e) {
			st1.executeUpdate("ROLLBACK PREPARED '" + fly_trans_name + "';");
			st2.executeUpdate("ROLLBACK PREPARED '" + hotel_trans_name + "';");
			e.printStackTrace();
			return false;
		}
		
		st1.executeQuery("select * from pg_prepared_xacts where gid='insert_fly'");
		st2.executeQuery("select * from pg_prepared_xacts where gid='insert_hotel'");
		
		if (st1.getResultSet().next() && st2.getResultSet().next()) {
			try {
				st1.executeUpdate("COMMIT PREPARED '" + fly_trans_name + "';");
				st2.executeUpdate("COMMIT PREPARED '" + hotel_trans_name + "';");
			} catch (SQLException e) {
				System.out.println("commit failed!");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("rollback");
			st1.executeUpdate("ROLLBACK PREPARED 'insert_fly'");
			st1.executeUpdate("ROLLBACK PREPARED 'insert_hotel'");
		}
		c1.close();
		c2.close();
		return true;
	}
	
	public static Connection getConnection(String db) {
		String url = String.format("jdbc:postgresql://%s/%s", "127.0.0.1:5432", db);
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return conn;
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
