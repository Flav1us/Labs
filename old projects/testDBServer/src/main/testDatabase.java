package main;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

//import ua.com.testcms.dao.UsersDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

	public class testDatabase {
	private static final String driverName = "org.postgresql.Driver"; //драйвер postgres базы данных имен-паролей
	private static final String[] databaseCredentials = {"users", "postgres", "password"}; //название базы данных, логин и пароль для доступа к ней
	private static DataSource datasource = new DataSource();
	
	public static void main(String[] args) throws SQLException {
		PoolProperties p = new PoolProperties();
		 p.setUrl("jdbc:postgresql://localhost/");
		 p.setDriverClassName("org.postgresql.Driver");
		 p.setUsername("postgres");
		 p.setPassword("password");
		 p.setMaxActive(10);
		 p.setMaxIdle(3);
		 p.setMaxWait(100);
		 datasource.setPoolProperties(p);
		// TODO Auto-generated method stub
		Connection con = datasource.getConnection();
		String res = "";
		 Statement st = con.createStatement();
		 ResultSet rs = st.executeQuery("select * from users where login='ivanov';");
		 while (rs.next()){
			 res+=rs.getString(1);
			 res+="\t"+rs.getString(2);
			 res+="\n";
		 }
		 /*rs.next();
		 res+=rs.getString(1);*/
		 System.out.println(res);
		 System.out.println("success!");
		 con.close();
		 rs.close();
	}
	
	

}
	
	
