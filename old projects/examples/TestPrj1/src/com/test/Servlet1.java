package com.test;

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
		String result = "result: ";
		String par1 = request.getParameter("par1");
		String par2 = request.getParameter("par2");
		result += "par1 = " + par1 + ", par2 = " + par2;
		System.out.println("reasult = " + result);
		
		if(par1 != null && par2 != null ) {
			int p1 = Integer.parseInt(par1);
			int p2 = Integer.parseInt(par2);
			result += " p1 + p2 = " + (p1 + p2);
		}
		
		String html = "<html><head>Программуля </head>"+
		"<body style='background-color:#c1c1c1;color:red'>"+result+"</body></html>";
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//response.setCharacterEncoding("utf8");
		//response.getWriter().append(html);
		
		request.getRequestDispatcher("page1.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
