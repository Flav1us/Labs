package ua.com.testcms.dto;

 
public class SiteDto implements Comparable {
 private String url;
 private String date;
 private String description;
 private String descriptionList;
 private String login;
 private boolean deleted;
 
 public SiteDto(){}
 public SiteDto(String _url,String _date,String _description,String _login, boolean _deleted){
	 url=_url;
	 date=_date;
	 setDescription(_description);
	 login=_login;
	 deleted=_deleted;
  }
 
public int compareTo(Object o) {
	   String _url = ((SiteDto)o).url;
	   int compare=url.compareToIgnoreCase(_url);
	   if(compare==0 && ((SiteDto)o).deleted)
		   return -1;// добавляем новый сайт если он ранее был  удален.
	   return compare;
}

public boolean validateSite(){
	boolean valid=true;
	if(this.url==null || this.url.trim().length()==0)
		valid=false;
	if(this.date==null || this.date.trim().length()==0)
		valid=false;
	if(this.login==null || this.login.trim().length()==0)
		valid=false;
	
	return valid;
}

public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public boolean isDeleted() {
	return deleted;
}
public void setDeleted(boolean deleted) {
	this.deleted = deleted;
}
public String getDescription() {
	return description;
}
public String getDescriptionList() {
	if(description!=null){
		int i=description.indexOf('\n'); 
		if(i<20 && i>0)
		    return description.substring(0,i-1);
		else
			if(description.length()>20)
			 return description.substring(0,20);
			else
		      return description;
	}
 return "";
}
public void setDescription(String description) {
	this.description = description;
}
public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
 
}
