package br.edu.ifpb.pwebi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DebugServlet
 */
@WebServlet(name = "debug", urlPatterns = { "/debug" })
public class DebugServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		PrintWriter out = resp.getWriter();
		out.println("<html><body><ul>");
		
		// DÚVIDA: fazer parar (tá em loop)
		// DÚVIDA 2: pegar os valores de cada parâmetro
		Enumeration enumeration = req.getParameterNames();
		String[] values;
        while (enumeration.hasMoreElements()) {
        	String param = (String) enumeration.nextElement();
        	values = req.getParameterValues(param);
			out.println("<li>"+param+" --- "+req.getHeader(param)+"</li>");
//            String parameterName = (String) enumeration.nextElement();
//            out.println("Parameter = " + parameterName);
        }
		out.println("</ul>");
		
		resp.getWriter().append("Served at: ").append(req.getContextPath()).append("</body></html>");
	}

    	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
