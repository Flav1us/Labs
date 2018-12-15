package ua.com.testcms.actions;

import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
 import java.io.*;
import java.util.*;
import java.text.*;
 
public abstract class SecureServlet extends HttpServlet {
   
  protected static final HashMap LOCALES = new HashMap (2);

  public static final String COUNTRYID_EN = "EN";
  public static final String COUNTRYID_RUS = "RUS";
   
  public static final String SESSION_ATTR_LOCALE = "locale";
  public static final String SESSION_ATTR_USERLOGIN = "userLogin";
  public static final String SESSION_ATTR_COUNTRYID = "countryId";
  public static final String REQUEST_PARAM_ACTION = "actionId";
 
  protected void doGet (
           HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 
                  HttpSession session = request.getSession();

                  request.setCharacterEncoding ("Cp1251");

                  String userLogin = (String) session.getAttribute (SESSION_ATTR_USERLOGIN);
                  if (userLogin == null) {
                     RequestDispatcher rd = getServletContext().getRequestDispatcher(LoginServlet.FORWARD_LOGIN);
                     rd.forward(request, response);
                     return;
                  }

                 /* String countryId = (String) session.getAttribute (SESSION_ATTR_COUNTRYID);
                  if (countryId != null) {
                      response.setLocale ((Locale) LOCALES.get (countryId.toUpperCase()));
                  }*/

                  this.doExecute (request, response);

    }

   protected void doPost (
           HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
     doGet(request, response);
   }
//------------------------------------------------------------------

   abstract void doExecute (
                         HttpServletRequest request, HttpServletResponse response)
                         throws ServletException, IOException;



 

  static {
         LOCALES.put (COUNTRYID_EN, new Locale ("en", "EN"));
         LOCALES.put (COUNTRYID_RUS, new Locale ("ru", "RU"));
         }

}
