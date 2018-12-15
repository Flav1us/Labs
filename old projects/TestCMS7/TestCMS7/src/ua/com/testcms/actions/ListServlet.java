package ua.com.testcms.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

import ua.com.testcms.dao.*;
import ua.com.testcms.dto.SiteDto;

public class ListServlet extends BaseServlet {
	
	 static String FORWARD = "/list.jsp";
	 private String FORWARD_NEXT = "/listNext.jsp";
	 static String FORWARD_ADD_SITE = "/addSite.jsp";
	 static String FORWARD_VIEW_SITE = "/viewSite.jsp";
	 static String FORWARD_EDIT_SITE = "/editSite.jsp";
	 
	 private static final int ACTION_INIT = 1;
     private static final int ACTION_NEXT = 2;
     private static final int ACTION_ADD_SITE = 3;
     private static final int ACTION_VIEW_SITE = 4;
     private static final int ACTION_EDIT_SITE = 5;

	 static {
	        ACTIONS_MAP.put("initList", new Integer(ACTION_INIT));
	        ACTIONS_MAP.put("nextList", new Integer(ACTION_NEXT));
	        ACTIONS_MAP.put("addSite", new Integer(ACTION_ADD_SITE));
	        ACTIONS_MAP.put("viewSite", new Integer(ACTION_VIEW_SITE));
	        ACTIONS_MAP.put("editSite", new Integer(ACTION_EDIT_SITE));
	        }

	 public void doExecute ( HttpServletRequest request, HttpServletResponse response)
		      throws IOException, ServletException {
		    try {
	            String actionName = request.getParameter(REQUEST_PARAM_ACTION);
	            int action = getAction(actionName);
	            switch (action) {
	                case ACTION_INIT:
	                    this.doInitListAction(request, response);
	    		    	forward(request,response,FORWARD);
	                    break;
	                case ACTION_NEXT:
	                    this.doNextListAction(request, response);
	    		    	forward(request,response,FORWARD_NEXT);
	                    break;
	                case ACTION_ADD_SITE:
	    		    	forward(request,response,FORWARD_ADD_SITE);
	                    break;
	                case ACTION_VIEW_SITE:
	                    this.doViewSiteAction(request, response);
	    		    	forward(request,response,FORWARD_VIEW_SITE);
	                    break;
	                case ACTION_EDIT_SITE:
	                    this.doViewSiteAction(request, response);
	    		    	forward(request,response,FORWARD_EDIT_SITE);
	                    break;
	                default:
	                    this.doInitListAction(request, response);
	                    this.forward(request, response, FORWARD);
	            }
		    	
		    } catch (Exception e) {
			e.printStackTrace();			
		     processError(request,response,e);
		    }
 	  }
	 
	 
     private void doInitListAction ( HttpServletRequest request,
             HttpServletResponse response) throws Exception {
    	 Set sites=null; 
        if(request.getParameter("prev_site")==null || request.getParameter("prev_site").trim().length()==0){
       	 sites=SiteDao.getSites();         
        }else{
        	// если переход на список выполняется с экранов Добавление сайта, Редактирование или Просмотр
        	// необходимо воспроизвести предыдущее состояние списка
        	// для этого передаются из экрана в экран параметры: первый и последний сайт, тип сортировки
        	
            SiteDto site= new SiteDto();
            site.setUrl(request.getParameter("prev_site"));
       	    sites=SiteDao.getSites(site,"next",0);
        }
        request.setAttribute("sites", sites);
        request.setAttribute("prevnext", SiteDao.isPrevNextSites(sites));
     }
     
     private void doNextListAction ( HttpServletRequest request,
             HttpServletResponse response) throws Exception {
         String siteUrl = request.getParameter("siteUrl");
         String what = request.getParameter("what");
         if(siteUrl!=null && what!=null){
             SiteDto site= new SiteDto();
             site.setUrl(siteUrl);
        	 Set sites=SiteDao.getSites(site,what,1);
        	 request.setAttribute("sites", sites);
             request.setAttribute("prevnext", SiteDao.isPrevNextSites(sites));
         }
     }


     private void doViewSiteAction ( HttpServletRequest request,
             HttpServletResponse response) throws Exception {
    	 SiteDto _site= new SiteDto();
         String par=request.getParameter("pkID");
         SiteDto site = null;
         if (par!=null && (par.trim()).length()>0){
          _site.setUrl(par);
          site = SiteDao.getSites(_site);
         }
         if(site == null) {
        	 throw new Exception("Неизвестный сайт"); 
         }
        
         request.setAttribute("site", site);
     }


}
