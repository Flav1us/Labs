package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Webmorda")
public class Webmorda extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Webmorda() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log.log(LocalDateTime.now() + " client connected from " + request.getRemoteAddr());
		Console.scan(request.getParameter("target_webpage"));
		response.getWriter().append("scanning");
		try {
			FileReader fr = new FileReader(Console.RESULT);
			BufferedReader br = new BufferedReader(fr);
			String buf;
			while((buf = br.readLine()) != null) {
				response.getWriter().append(buf + "\n");
			}
			fr.close();
			br.close();
		} catch (IOException e) {
			response.getWriter().append("IOEXC " +e.getMessage());
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
