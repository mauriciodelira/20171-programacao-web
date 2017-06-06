package br.edu.ifpb.pwebi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SegundoServlet
 */
@WebServlet("/Segundo")
public class SegundoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		// Serve que nem System.out
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body><h1>Opa!</h1></body>");
		out.println("</html>");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
