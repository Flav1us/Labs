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
	static String amount_trans_name = "insert_amount";
	
	static { try { Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) { e.printStackTrace(); } }
	
	Connection c1 = getConnection("db1_fly");
	Connection c2 = getConnection("db2_hotel");
	Connection c3 = getConnection("db3_amount");

		//throws exception if it cannot be handled: connection establishment failure, create statement failure etc.
	public boolean create_trip(String name, String flight_number, String from, String to,
			String arrival_date, String hotel, String departure_date, int price) throws SQLException{

		System.out.println(1);
		String prepare_fly = "BEGIN;"
				+ "insert into fly_booking(name, fly_number, fromm, toooo, date) "
				+ String.format("values('%s', '%s', '%s', '%s', '%s');", name, flight_number, from, to, arrival_date)
				+ "PREPARE TRANSACTION '"+fly_trans_name+"';";
				

		String prepare_hotel = "BEGIN;"
				+ "insert into hotel_booking(client_name, hotel_name, arrival, departure) "
				+ String.format("values('%s', '%s', '%s', '%s');", name, hotel, arrival_date, departure_date)
				+ "PREPARE TRANSACTION '"+hotel_trans_name+"';";
	
		int id = 1;
		String prepare_amount = "BEGIN;"
				+ "UPDATE amount SET amount=amount-"+price+" where id = "+id +";"
				+ "PREPARE TRANSACTION '"+amount_trans_name+"';";
		
		/*
	UPDATE public.amount
	SET amount=amount-10000
	WHERE id=1;
		 */
		
		Statement st1 = c1.createStatement();
		Statement st2 = c2.createStatement();
		Statement st3 = c3.createStatement();
		System.out.println(2);
		//voting
		
		boolean global_commit = true;
		
		try {
			st1.executeUpdate(prepare_fly);	
		} catch (SQLException e) {
			System.out.println("err1");
			e.printStackTrace();
			global_commit = false;
		}
		System.out.println(2);
		try {
			st2.executeUpdate(prepare_hotel);	
		} catch (SQLException e) {
			System.out.println("err2");
			e.printStackTrace();
			global_commit = false;
		}
		System.out.println(2);
		
		try {
			System.out.println("try");
			st3.execute(prepare_amount);
			System.out.println("tried");
		} catch (SQLException e) {
			System.out.println("err3");
			e.printStackTrace();
			global_commit = false;
		}
		
		System.out.println(3);
		
		if(global_commit) {
			st1.executeUpdate("COMMIT PREPARED '" + fly_trans_name + "';");
			st2.executeUpdate("COMMIT PREPARED '" + hotel_trans_name + "';");
			st3.executeUpdate("COMMIT PREPARED '" + amount_trans_name + "';");
		}
		else {
			st1.executeUpdate("ROLLBACK PREPARED '" + fly_trans_name + "';");
			st2.executeUpdate("ROLLBACK PREPARED '" + hotel_trans_name + "';");
			st3.executeUpdate("ROLLBACK PREPARED '" + amount_trans_name + "';");
		}
		System.out.println(4);
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
		c3.close();
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
