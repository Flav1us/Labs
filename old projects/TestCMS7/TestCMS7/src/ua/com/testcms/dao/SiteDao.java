/**
 * 
 */
package ua.com.testcms.dao;

import java.util.*;

import ua.com.testcms.dto.*;
import java.text.SimpleDateFormat;

public class SiteDao {

	private static Set sites=new TreeSet();
	static final int COUNT_VIEW=10;
	
	static{
		// для теста
	/*	boolean deleted=false;
		sites.add(new SiteDto("http://127.0.0.1:8080/TestCMS/list","09.12.2006 21:57","fs sfsdf dfs dffdsf fsdfd dsfdsf dfsfdsf dfdsfdsfdsf","ivanov", deleted)  );
		sites.add(new SiteDto("http://www.sun.com/products/index.jsp","09.12.2006 08:57","","ivanov", deleted)  );
		sites.add(new SiteDto("http://www.firststeps.ru/","09.12.2006 19:57","","ivanov", deleted)  );
		sites.add(new SiteDto("http://www.viche.com.ua/","09.12.2006 10:57","","ivanov", deleted)  );
		sites.add(new SiteDto("http://www.quest.com/","09.12.2006 11:57","","ivanov", deleted)  );
		sites.add(new SiteDto("http://www.msi77.narod.ru/","09.12.2006 07:00","","ivanov", deleted)  );
		sites.add(new SiteDto("http://www.ebay.com/","09.12.2006 12:50","","ivanov", deleted)  );
		sites.add(new SiteDto("http://www.windowsmedia.com","08.12.2006 08:00","","ivanov", deleted)  );
		sites.add(new SiteDto("http://lafox.net/","07.12.2006 06:37","","ivanov", deleted)  );
		sites.add(new SiteDto("http://chat.vostok.net/cgi-bin/","07.12.2006 02:27","","ivanov", deleted)  );
		sites.add(new SiteDto("http://www.playhard.ru","11.12.2006 03:11","","ivanov", deleted)  );
		sites.add(new SiteDto("http://www.corbadev.kiev.ua","20.12.2006 01:57","","ivanov", deleted)  );
		*/
	}
	
	
	public static Set getSites(){
		// список сайтов всегда передается с сортировкой по url
		Set _sites=new TreeSet();
		Iterator iter = sites.iterator();
		int index=0;
		 while(iter.hasNext()){
		   SiteDto site=(SiteDto)iter.next();	 
		   if(!site.isDeleted()){ 
			 _sites.add(site);
			 if((++index)==COUNT_VIEW)
			 break;
			} 
		 }
	 return _sites;	 
 	}

	public static SiteDto getSites(SiteDto _site){
		Iterator iter = sites.iterator();
		 while(iter.hasNext()){
			 SiteDto site=(SiteDto)iter.next();	 
			 if(site.compareTo(_site)==0){
				 return site; 
			 }
		 }
		return null; 
 	}

	public static Set getSites(SiteDto _site, String what,int step){
		// параметр step позволяет включать первый или последний сайт в наборе
		Set _sites=new TreeSet();
		Object o[] = sites.toArray();
		// если не удаленных сайтов меньше чем COUNT_VIEW - возвращаем все сайты
		int countEnabled=0;
        for(int i=0;i<o.length;i++){
   		   if(!((SiteDto) o[i]).isDeleted()){
   			countEnabled++;   
   		   }
        }
		if(countEnabled<COUNT_VIEW)
			return getSites();
		
        for(int i=0;i<o.length;i++){
       	 if(((SiteDto)o[i]).compareTo(_site)==0){
     		int index=0;
       		if("prev".equals(what)){
       		 for(int j=(i-step);j>=0;j--){
      		   if(!((SiteDto) o[j]).isDeleted()){ 
      			   if(o[j]!=null && (++index)<=COUNT_VIEW ){
      				   _sites.add(o[j]);	   
      			   }else
      				   break;
      		   	}
       		 }
       		}else
       			if("next".equals(what)){
       				for(int j=(i+step);j<o.length;j++){
       	      		   if(!((SiteDto) o[j]).isDeleted()){ 
                         if(o[j]!=null && (++index)<=COUNT_VIEW ){
                      	   _sites.add(o[j]);	   
                         }else
                      	   break;
       	      		   }  
       				}
       			
       			}
       		break;		
       	 }
        }
		return _sites; 
 	}

	public static boolean addSite(SiteDto site) throws Exception{
		return sites.add(site); 
 	}

	public static void deleteSite(ArrayList listSites) throws Exception{
		for(int i=0;i<listSites.size();i++){
			SiteDto _site=(SiteDto)listSites.get(i);
			Iterator iter = sites.iterator();
			 while(iter.hasNext()){
			  SiteDto site=(SiteDto)iter.next();	 
	          if(site.getUrl().equals(_site.getUrl()) && !site.isDeleted()){
	        	  site.setDeleted(true);
	        	  break;
	          }
			 }
		}
 	}
	
/*	public static boolean updateSite(SiteDto _site) throws Exception{
		Iterator iter = sites.iterator();
		 while(iter.hasNext()){
		  SiteDto site=(SiteDto)iter.next();	 
          if(site.compareTo(_site)==0){
        	  sites.remove(iter.next());
         	  break;
          }
		 }
		return sites.add(_site); 
 	} */
	
	public static boolean updateSite(SiteDto _site) throws Exception{
		Iterator iter = sites.iterator();
		 while(iter.hasNext()){
		  SiteDto site=(SiteDto)iter.next();	 
          if(site.compareTo(_site)==0){
        	  site.setDescription(_site.getDescription());
        	  site.setLogin(_site.getLogin());
         	  return true;
          }
		 }
	 return false;	 
  	}
	
	public static int[] isPrevNextSites(Set _sites){
	 // метод проверяет есть ли в базовом списке предыдущие и следующие сайты
	//	относительно того списка, что будет отражаться на экране	
	 int i[] = new int[]{0,0};
	 Object o[] = sites.toArray();
	 Object o1[] = _sites.toArray();
	 
	 if(sites.size()>0 && _sites.size()>0){
		for(int j=0;j<sites.size();j++){
			 if(((SiteDto)o[j]).compareTo((SiteDto)o1[0])!=0 && !((SiteDto)o[j]).isDeleted()){
				 i[0]=1; break;
			 }else{
				 if(((SiteDto)o[j]).compareTo((SiteDto)o1[0])==0)
					 break;
			 }
		}
		for(int j=(sites.size()-1);j>=0;j--){
			 if(((SiteDto)o[j]).compareTo((SiteDto)o1[_sites.size()-1])!=0 && !((SiteDto)o[j]).isDeleted()){
				 i[1]=1; break;
			 }else{
				 if(((SiteDto)o[j]).compareTo((SiteDto)o1[_sites.size()-1])==0)
					 break;
			 }
		}
	 }else{
		 if(sites.size()>0){
   		   for(int j=0;j<sites.size();j++){
   			if(!((SiteDto)o[j]).isDeleted()){
   				i[0]=1; break;
   			}
   		   }

		 }
	 }
	 
	 return i;
	}
	
}
