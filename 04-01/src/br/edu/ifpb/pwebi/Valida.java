package br.edu.ifpb.pwebi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Valida
 */
@WebServlet(name = "valide", urlPatterns = { "/valide" })
public class Valida extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = "";
		String pass = "";
		
		if(!req.getParameter("user").isEmpty())
			user = req.getParameter("user");
		if(!req.getParameter("pass").isEmpty())
			user = req.getParameter("pass");
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><body>");
		out.println("<h1>Dados</h1><h2>Login:"+user+"</h1>");
		out.println("<h2>Senha: "+pass+"</h2>");
		out.println("</html></body>");
		
		
		
		resp.getWriter().append("Served at: ").append(req.getContextPath());
		
	}

}
