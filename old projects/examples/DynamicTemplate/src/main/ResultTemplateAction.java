package main;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ResultTemplate
 */
@WebServlet("/ResultTemplate")
public class ResultTemplateAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public static final String TEXTS_QTTY = "textsqtty";
	public static final String RADIOS_QTTY = "radiosqtty";
	public static final String CHECKS_QTTY = "checksqtty";
	public static final String SELECTS_QTTY = "selectsqtty";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultTemplateAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		response.setCharacterEncoding("utf8");
		
		response.getWriter().append(Integer.toString(textqtty));*/
		HashMap error = templateValidate(request);
		
		if(!error.isEmpty()) {
			request.setAttribute("errorMap", error);
			request.getRequestDispatcher("jsp/TemplateStructure.jsp").forward(request, response);
			return;
		}
		
		TemplateBean bean = new TemplateBean();
		bean.setTextsqtty(Integer.parseInt(request.getParameter(TEXTS_QTTY)));
		bean.setRadiosqtty(Integer.parseInt(request.getParameter(RADIOS_QTTY)));
		bean.setChecksqtty(Integer.parseInt(request.getParameter(CHECKS_QTTY)));
		bean.setSelectsqtty(Integer.parseInt(request.getParameter(SELECTS_QTTY)));
		
		request.setAttribute("templateBean", bean);
		request.getRequestDispatcher("jsp/resultTemplate.jsp").forward(request, response);
	}

	private HashMap templateValidate(HttpServletRequest request){
		HashMap error = new HashMap();
		String textsqtty = request.getParameter(TEXTS_QTTY);
		String radiosqtty = request.getParameter(RADIOS_QTTY);
		String checksqtty = request.getParameter(CHECKS_QTTY);
		String selectsqtty = request.getParameter(SELECTS_QTTY);
		
		System.out.println("textsqtty = " + textsqtty );
		System.out.println("radiosqtty = " + radiosqtty );
		System.out.println("checksqtty = " + checksqtty );
		System.out.println("selectsqtty = " + selectsqtty );
		
		if(textsqtty == null || textsqtty.trim().length() == 0) {
			error.put(TEXTS_QTTY, "Количество Text fields должно быть больше нуля");
		} else {
			//TODO проверка на число
			try {
				int r = Integer.parseInt(textsqtty.trim());
				if(Constants.TEXTS_QTTY_MAX < r ){
					error.put(TEXTS_QTTY, "Значение Text fields должно быть меньше " + Constants.TEXTS_QTTY_MAX);
				}
			} catch (NumberFormatException ex) {
				error.put(TEXTS_QTTY, "Значение Text fields должно быть числом");
			}
		}
		/*if(error.get(TEXTS_QTTY) != null)
			request.setAttribute(TEXTS_QTTY, textsqtty);*/
		
		
		if(radiosqtty == null || radiosqtty.trim().length() == 0) {
			error.put(RADIOS_QTTY, "Количество radiobuttons должно быть больше нуля");
		} else {
			//TODO проверка на число
			try {
				int r = Integer.parseInt(radiosqtty.trim());
				if(Constants.RADIOS_QTTY_MAX < r ){
					error.put(RADIOS_QTTY, "Значение radiobuttons должно быть меньше " + Constants.RADIOS_QTTY_MAX);
				}
			} catch (NumberFormatException ex) {
				error.put(RADIOS_QTTY, "Значение radiobuttons должно быть числом");
			}
		}
		/*if(error.get(RADIOS_QTTY) != null)
			request.setAttribute(RADIOS_QTTY, radiosqtty);*/
		
		
		if(checksqtty == null || checksqtty.trim().length() == 0) {
			error.put(CHECKS_QTTY, "Количество checkbuttons должно быть больше нуля");
		} else {
			//TODO проверка на число
			try {
				int r = Integer.parseInt(checksqtty.trim());
				if(Constants.CHECKS_QTTY_MAX < r ){
					error.put(CHECKS_QTTY, "Значение checkbuttons должно быть меньше " + Constants.CHECKS_QTTY_MAX);
				}
			} catch (NumberFormatException ex) {
				error.put(CHECKS_QTTY, "Значение checkbuttons должно быть числом");
			}
		}
		/*if(error.get(CHECKS_QTTY) != null)
			request.setAttribute(CHECKS_QTTY, checksqtty);*/
		
		if(selectsqtty == null || selectsqtty.trim().length() == 0) {
			error.put(SELECTS_QTTY, "Количество checkbuttons должно быть больше нуля");
		} else {
			//TODO проверка на число
			try {
				int r = Integer.parseInt(selectsqtty.trim());
				if(Constants.SELECTS_QTTY_MAX < r ){
					error.put(SELECTS_QTTY, "Значение checkbuttons должно быть меньше " + Constants.CHECKS_QTTY_MAX);
				}
			} catch (NumberFormatException ex) {
				error.put(SELECTS_QTTY, "Значение checkbuttons должно быть числом");
			}
		}
		
		
		if (!error.isEmpty()) {
			request.setAttribute(TEXTS_QTTY, textsqtty);
			request.setAttribute(RADIOS_QTTY, radiosqtty);
			request.setAttribute(CHECKS_QTTY, checksqtty);
			request.setAttribute(SELECTS_QTTY, checksqtty);
		}
		
		return error;
	}
}
