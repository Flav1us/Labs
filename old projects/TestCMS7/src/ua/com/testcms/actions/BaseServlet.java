package ua.com.testcms.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseServlet  extends SecureServlet {

 protected static final Map ACTIONS_MAP = new HashMap();
 protected static final int ACTION_UNKNOWN = 0;
 protected static final String REQUEST_PARAM_ACTION = "actionId";
 protected static final String REQUEST_ATTR_ERRORS = "errors";

	
 protected  int getAction (String actionName) {
        int result = ACTION_UNKNOWN;
        Object o = ACTIONS_MAP.get (actionName);
        if (o != null) {
          result = ((Integer) o).intValue ();
        }
        return result;
      }

 protected void forward (
            HttpServletRequest request, HttpServletResponse response,
            String forward)
            throws ServletException, IOException {
        getServletContext ().getRequestDispatcher (forward)
                .forward (request, response);
 }
 protected void processError (
            HttpServletRequest request, HttpServletResponse response,
            Exception e)
              throws ServletException, IOException {
            request.setAttribute ("javax.servlet.error.exception", e);
            forward(request, response,"/error/error.jsp");
 }

}
