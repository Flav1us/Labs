package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.TwoPC;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		TwoPC twopc = new TwoPC();
		Connection c1 = twopc.getConnection("db1_fly");
		Connection c2 = twopc.getConnection("db2_hotel");
		c1.setAutoCommit(false);
		c2.setAutoCommit(false);
		
		String sql = "BEGIN;"
				+ "update fly_booking set name='anton' where name='ton';"
				+ "PREPARE TRANSACTION 'updname';"
				+ "COMMIT PREPARED 'updname';";
		
		c1.createStatement().execute(sql );
	}


	public static void testConnection() throws SQLException {
		String db1 = "db1_fly";
		TwoPC twopc = new TwoPC();
		Connection conn_fly = twopc.getConnection(db1);
		String query = "select * from fly_booking";
		ResultSet rs = conn_fly.createStatement().executeQuery(query );
		
		for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
			System.out.print(rs.getMetaData().getColumnLabel(i) + "\t");
		} System.out.println();

		while(rs.next()) {
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				System.out.print(rs.getString(i) + "\t");
			} System.out.println();
		}
	}

	private static void createTables(Connection conn1, Connection conn2) throws SQLException {
		/*String query1 = "CREATE TABLE fly_booking " + 
        	"(id INTEGER not NULL, " + 
        	" name VARCHAR(255), " +  
        	" fly_number VARCHAR(255), " +  
        	" fromm varchar(255), " +
        	" toooo varchar(255), " +
        	" date date, " +
        	" PRIMARY KEY ( id ))";
		conn1.createStatement().executeUpdate(query1);
		System.out.println("table fly_booking created");
		
		String query2 = "CREATE TABLE hotel_booking " + 
    		"(id INTEGER not NULL, " + 
    		" client_name VARCHAR(255), " +  
    		" hotel_name VARCHAR(255), " +  
    		" arrival date, " +
    		" departure date, " +
    		" PRIMARY KEY ( id ))";
		conn2.createStatement().executeUpdate(query2);
		System.out.println("table hotel_booking created");*/
	}
	
	private static void createTestTable(Connection conn) throws SQLException {
		String query = "CREATE TABLE   REGISTRATION " + 
	            "(id INTEGER not NULL, " + 
	            " first VARCHAR(255), " +  
	            " last VARCHAR(255), " +  
	            " age INTEGER, " +  
	            " PRIMARY KEY ( id ))";
		conn.createStatement().executeUpdate(query );
		Statement stmt;
		stmt = conn.createStatement();  
        String sql = "INSERT INTO Registration " + "VALUES (100, 'Zara', 'Ali', 18)"; 
        stmt.executeUpdate(sql); 
        sql = "INSERT INTO Registration " + "VALUES (101, 'Mahnaz', 'Fatma', 25)";  
        stmt.executeUpdate(sql); 
        sql = "INSERT INTO Registration " + "VALUES (102, 'Zaid', 'Khan', 30)"; 
        stmt.executeUpdate(sql); 
        sql = "INSERT INTO Registration " + "VALUES(103, 'Sumit', 'Mittal', 28)"; 
        stmt.executeUpdate(sql); 
	}

	private static void fill_tables() throws SQLException {
		String db1 = "db1_fly";
		String db2 = "db2_hotel";
		TwoPC twopc = new TwoPC();
		Connection conn_fly = twopc.getConnection(db1);
		Connection conn_hotel = twopc.getConnection(db2);
		conn_fly.setAutoCommit(false);
		conn_hotel.setAutoCommit(false);
		
		String update_q = String.format("insert into fly_booking values (%d, %s, %s, %s, %s, %s)",
										1, "'ton'", "'123'", "'kyiv'", "'US'", "'2019-02-28'");
		conn_fly.createStatement().executeUpdate(update_q);
		//conn_fly.createStatement().; 
		//https://www.ibm.com/support/knowledgecenter/en/ssw_ibm_i_71/rzaha/distrans.htm
		conn_fly.commit();
		conn_hotel.close();
		conn_fly.close();
	}
	

}
