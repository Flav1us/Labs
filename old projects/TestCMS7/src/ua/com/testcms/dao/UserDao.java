package ua.com.testcms.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class UserDao {
	static Logger logger = LogManager.getRootLogger();
	static DataSource datasource = new DataSource();
	
 static{
	 PoolProperties p = new PoolProperties();
	 p.setUrl("jdbc:postgresql://localhost/");
	 p.setDriverClassName("org.postgresql.Driver");
	 p.setUsername("postgres");
	 p.setPassword("password");
	 p.setMaxActive(10);
	 p.setMaxIdle(3);
	 p.setMaxWait(100);
	 datasource.setPoolProperties(p);
 }
 
 /*public static int isExistsUser(String login, String password){
	int rez=0;
	for(int i=0;i<users.size();i++){
	  String user[]=(String[])users.get(i);
	  if(login.equals(user[0])){
		  if(password.equals(user[1])) rez=2;
		  else rez=1;
		break;  
	  }
	}
	return rez;
 }*/
 
 public static int isExistsUser(String login, String password) {
	 
	 // 0 -> login does not exist
	 // 1 -> login exists, wrong password
	 // default -> OK, log in
	 int exists = 2;
	 int res = 0;
		try {
			Connection con = datasource.getConnection();
			ResultSet rs = con.createStatement().executeQuery("select * from users where login = '" + login + "'");
			while (rs.next()) {
				logger.info(rs.getString(1) + "\t" + rs.getString(2));
				if (rs.getString("password").equals(password)) {
					return exists;
				} else if (rs.getString(1).equals(login)) {
					res = 1;
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return res;
 }
}
