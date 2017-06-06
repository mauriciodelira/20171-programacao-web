package br.edu.ifpb.pwebi;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Info
 */
@WebServlet("/info")
public class Info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("html");
		String user = "Marcelo";
		Integer age = 19;
		
		if(request.getParameter("user") != null)
			user = request.getParameter("user");
		if(request.getParameter("age") != null)
			age	= Integer.parseInt(request.getParameter("age"));
		request.setAttribute("user", user);
		request.setAttribute("age", age);		
		
		RequestDispatcher despachante = request.getRequestDispatcher("info.jsp");
		despachante.forward(request, response);
	}


}
