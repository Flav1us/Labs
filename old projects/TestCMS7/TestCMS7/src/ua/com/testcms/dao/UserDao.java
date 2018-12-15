package ua.com.testcms.dao;
import java.util.*;

public class UserDao {
 
 private static List users;
 static{
	users=new ArrayList();
	users.add(new String[]{"petrov","password"});
	users.add(new String[]{"ivanov","password"});
 }
 
 public static int isExistsUser(String login, String password){
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
 }
}
