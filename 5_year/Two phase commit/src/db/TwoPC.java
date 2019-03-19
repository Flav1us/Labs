package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TwoPC { //one class instance for a single commit
	
	static String username = "postgres";
	static String password = "password";
	static String fly_trans_name = "insert_fly";
	static String hotel_trans_name = "insert_hotel";
	
	static { try { Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) { e.printStackTrace(); } }
	
	Connection c1 = getConnection("db1_fly");
	Connection c2 = getConnection("db2_hotel");

	//throws exception if it cannot be handled: connection establishment failure, create statement failure etc.
	public boolean create_trip(String name, String flight_number, String from, String to,
			String arrival_date, String hotel, String departure_date) throws SQLException, InterruptedException {

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

		//voting
		
		boolean global_commit = true;
		
		try {
			st1.executeUpdate(prepare_fly);	
		} catch (SQLException e) {
			e.printStackTrace();
			global_commit = false;
		}
		
		try {
			st2.executeUpdate(prepare_hotel);	
		} catch (SQLException e) {
			e.printStackTrace();
			global_commit = false;
		}
		
		if(global_commit) {
			while(true) { // TC should wait until node 'resurrects'
				try {
					st1.executeUpdate("COMMIT PREPARED '" + fly_trans_name + "';");
					break;
				} catch(SQLException e) {
					System.out.println("commit fly failed");
					Thread.sleep(1000);
					continue;
				}
			}
			//throw new RuntimeException("Transaction Coordinator Failure");
			while(true) {
				try {
					st2.executeUpdate("COMMIT PREPARED '" + hotel_trans_name + "';");
					break;
				} catch(SQLException e) {
					System.out.println("commit hotel failed");
					Thread.sleep(1000);
					continue;
				}
			}
		}
		else {
			st1.executeUpdate("ROLLBACK PREPARED '" + fly_trans_name + "';");
			st2.executeUpdate("ROLLBACK PREPARED '" + hotel_trans_name + "';");
		}
		
		//not sure if its really needed
		/*st1.executeQuery("select * from pg_prepared_xacts where gid='"+fly_trans_name+"'");
		st2.executeQuery("select * from pg_prepared_xacts where gid='"+hotel_trans_name+"'");
		
		if (st1.getResultSet().next() && st2.getResultSet().next()) {
			try {
				st1.executeUpdate("COMMIT PREPARED '" + fly_trans_name + "';");
				st2.executeUpdate("COMMIT PREPARED '" + hotel_trans_name + "';");
			} catch (SQLException e) {
				System.out.println("commit failed!");
				e.printStackTrace();
			}
		}
		else { //prepare transaction failed: no entry id db's table of prepared transaction
			System.out.println("rollback");
			st1.executeUpdate("ROLLBACK PREPARED '" + fly_trans_name + "'");
			st1.executeUpdate("ROLLBACK PREPARED '" + hotel_trans_name + "'");
		}*/
		
		c1.close(); //one class instance -- one commit
		c2.close();
		System.out.println("2pc success");
		return global_commit;
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
