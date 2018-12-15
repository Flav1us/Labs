package ua.com.testcms.actions;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.*;

import ua.com.testcms.dao.*;

public class LoginServlet extends HttpServlet {

 	private static final String REQUEST_PARAM_PASSWORD = "password";
	private static final String REQUEST_PARAM_COUNTRYID = "countryId";
	private static final String REQUEST_PARAM_LOGIN = "login";
	private static final String REQUEST_ATTR_ERRORS = "errors";
	private String FORWARD = "/list?actionId=initList";
	private String countryId;
  
	public static final String FORWARD_LOGIN = "/login.jsp";;
 
	   public void init () {

 	        String countryId = getInitParameter ("lang");
	        if (countryId != null) {
	        	this.countryId=countryId;
	        }
	        
	    }

	    protected void doGet (
	            HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	  
	    	doExecute(request, response);	    }

	    protected void doPost (
	            HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	doExecute(request, response);
	    }
	 
	  public void doExecute (
		      HttpServletRequest request, HttpServletResponse response)
		      throws IOException, ServletException {
		    try {
		    	    request.setCharacterEncoding ("Cp1251");
 	                if (this.countryId != null) {
 	                	response.setLocale ((Locale) SecureServlet.LOCALES.get (this.countryId.toUpperCase()));
                    }

		    	    
  		            String password = request.getParameter (REQUEST_PARAM_PASSWORD);
 		            String login = request.getParameter (REQUEST_PARAM_LOGIN);
 
		            
		                List errors = new LinkedList ();
		                if (login == null || login.length () == 0) {
		                    errors.add ("login.emptyLogin");
		                }
		                if (password == null || password.length () == 0) {
		                    errors.add ("login.emptyPassword");
		                }
 		                if (errors.size () == 0) {
 		                    int result = UserDao.isExistsUser(login, password);
		                    switch (result) {
		                        case 0:
		                            errors.add ("login.wrongUser");
		                            break;
		                        case 1:
		                            errors.add ("login.wrongPassword");
		                            break;
		                        default:{
		                            request.getSession().invalidate();
		                            HttpSession session = request.getSession (true);
		                            session.setMaxInactiveInterval(3600);
		                            session.setAttribute (
		                            		SecureServlet.SESSION_ATTR_USERLOGIN, login);
		                            if (countryId != null)
		                                session.setAttribute (SecureServlet.SESSION_ATTR_LOCALE, SecureServlet.LOCALES.get (countryId));

 		                        }
 
		                    }
		                }
		      request.setAttribute (REQUEST_ATTR_ERRORS, errors);
		      if(errors.size()==0)      
 		        getServletContext ().getRequestDispatcher (FORWARD).forward (request, response);
 		      else
 	 		      getServletContext ().getRequestDispatcher (FORWARD_LOGIN).forward (request, response);
		    } catch (Exception e) {
		    	processError(request,response,e);//,log);
		    }

	  }
	  
	  protected void processError (
	            HttpServletRequest request, HttpServletResponse response,
	            Exception e)//,Logger log)
	              throws ServletException, IOException {
	            request.setAttribute ("javax.servlet.error.exception", e);
	          //  log.error(e.getMessage(),e);
	            getServletContext ().getRequestDispatcher ("/error/error.jsp").forward (request, response);
	 }

}
