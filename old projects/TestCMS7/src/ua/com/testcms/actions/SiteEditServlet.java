package ua.com.testcms.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.regexp.RE;

import ua.com.testcms.dao.SiteDao;
import ua.com.testcms.dto.SiteDto;

public class SiteEditServlet extends BaseServlet {

     private String FORWARD_ADD = "/addSite.jsp";
     private String FORWARD_LIST = "/list?actionId=initList";
     
	 private static final int ACTION_ADD = 1;
     private static final int ACTION_EDIT = 2;
     private static final int ACTION_DELETE = 3;
     
	 static {
	        ACTIONS_MAP.put("add", new Integer(ACTION_ADD));
	        ACTIONS_MAP.put("edit", new Integer(ACTION_EDIT));
	        ACTIONS_MAP.put("delete", new Integer(ACTION_DELETE));
	        }

	  public void doExecute (
		      HttpServletRequest request, HttpServletResponse response)
		      throws IOException, ServletException {
		    try {
	            String actionName = request.getParameter(REQUEST_PARAM_ACTION);
	            int action = getAction(actionName);
                List errors = new LinkedList ();
    	        request.setAttribute (REQUEST_ATTR_ERRORS, errors);
    	        
	            switch (action) {
	                case ACTION_ADD:

	                    this.doAddSiteAction(request, response,errors);
	                    if(errors.size()==0)
	    		    	  forward(request,response,ListServlet.FORWARD_VIEW_SITE);
	                    else
	                    	this.forward(request, response, ListServlet.FORWARD_ADD_SITE);
	                    break;
	                case ACTION_EDIT:
	                    this.doEditSiteAction(request, response,errors);
	    		    	forward(request,response,ListServlet.FORWARD_VIEW_SITE);
	                    break;
	                case ACTION_DELETE:
	                    this.doDeleteSiteAction(request, response,errors);
	    		    	forward(request,response,FORWARD_LIST);
	                    break;
	                default:
	                    this.forward(request, response, FORWARD_LIST);
	            }
			 
	
		    } catch (Exception e) {
		     processError(request,response,e);
		    }

	  }
	  
	     private void doAddSiteAction ( HttpServletRequest request,
	             HttpServletResponse response,List errors) throws Exception {
	    	 SiteDto site= fillBean(request,errors);
	    	 request.setAttribute("site", site); 
             if(errors.size()==0){
            	 if(!SiteDao.addSite(site)){
            		 errors.add ("siteExists"); 
            	 }
             }

	     }

	     private void doEditSiteAction ( HttpServletRequest request,
	             HttpServletResponse response,List errors) throws Exception {
	    	 SiteDto site= fillBean(request,errors);
	    	 request.setAttribute("site", site); 
             if(errors.size()==0){
            	 if(!SiteDao.updateSite(site)){
            		 errors.add ("uknownSite"); 
            	 }
             }
	     }

	     private void doDeleteSiteAction ( HttpServletRequest request,
	             HttpServletResponse response,List errors) throws Exception {

	    	 String par=request.getParameter("pkID");
	    	 StringTokenizer str=new StringTokenizer(par,"^",false);
	    	 ArrayList listSites=new ArrayList(); 
	    	 while(str.hasMoreElements()){
	    		 listSites.add( new SiteDto((String)str.nextElement(),"","","", true)); 
	    	 }
	    	 
             if(listSites.size()>=0){
            	 SiteDao.deleteSite(listSites);
             }else
            	 errors.add("noSitesForDelete");
	    	 
	     }
	     
	     private static SiteDto fillBean (HttpServletRequest request, List errors)
	             throws Exception {
             SiteDto site= new SiteDto();
             String par="";  
             if("add".equals(request.getParameter(REQUEST_PARAM_ACTION))){
                 par=request.getParameter("url");
                 RE url = new RE("^http://.+");
                 if(!url.match(par))
                   errors.add ("siteUrl");
                 site.setDate(new java.text.SimpleDateFormat("hh:mm dd.MM.yyyy").format(new Date()));
             }else
                 if("edit".equals(request.getParameter(REQUEST_PARAM_ACTION))){
                     par=request.getParameter("pkID");
                 }
             if (par!=null && (par.trim()).length()>0)
              site.setUrl(par);
             else errors.add ("emptyUrl");

             par=request.getParameter("description");
             if (par!=null && (par.trim()).length()>0)
              site.setDescription(par);
             
                 
             site.setLogin((String) request.getSession().getAttribute (SESSION_ATTR_USERLOGIN));
             
             return site;
	     }


}
