package br.edu.ifpb.pwebi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ConversorGraus
 */
@WebServlet(name="conversor", urlPatterns={"/converter"})
public class ConversorGraus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		double c=0, f=0;
  		String stemp, smed;
  		stemp = request.getParameter("temp");
  		smed = request.getParameter("med");
  		
  		if(smed.equals("C")){
  			c=Double.parseDouble(stemp);
  			f=c*(9/5)+32;
  			request.setAttribute("temp", f);
  		}else if(smed.equals("F")){
  			f=Double.parseDouble(stemp);
  			c=(f-32)*(5/9);
  			request.setAttribute("temp", c);
  		}
  		request.setAttribute("med", smed);
		response.setContentType("html");
  		PrintWriter out = response.getWriter();
  		response.sendRedirect("formtemperatura.jsp");
//  		request.getRequestDispatcher("formtemperatura.jsp").forward(request, response);
  		
  		response.getWriter().append("Served at: ").append(request.getContextPath()).append("</body></html>");
	}

}
