package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf8");
		String html = "<html><head></head><body>\n"+
					"<form action='/TestPrj3/Servlet1' method='post'>\n"+
					"Login<br>\n"+
					"<input type='text' name='login'><br>\n"+
					"Password:<br>\n"+
					"<input type='text' name='password'><br><br>\n"+
					"<input type='submit' value='Check'><br>\n"+
					"</body></html>";
		response.getWriter().append(html);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("login"));
		System.out.println(request.getParameter("password"));
		if( "a".equals(request.getParameter("login"))  && "p".equals(request.getParameter("password"))) {
			response.sendRedirect("http://localhost:8080/TestPrj3/Servlet2");
		}
		else {
			response.sendRedirect("http://localhost:8080/TestPrj3/Servlet3");
		}
	}

}
