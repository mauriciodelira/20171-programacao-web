package br.edu.ifpb.pwebi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "login", urlPatterns = { "/login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Properties passwdFile;
		passwdFile = new Properties();
		try{
			// carrega arquivos de senha que se encontram no contexto do servlet
			passwdFile.load(
					this.getServletContext().getResourceAsStream("/WEB-INF/passwd.properties"));
		}catch(IOException e){
			// vai enviar um erro do tipo "bad request"
			response.sendError(
					response.SC_BAD_REQUEST, "Erro ao pegar as senhas");
			return; // encerra o servlet
		}
		response.setContentType("html");
		String user = "";
		String pass = "";
		
		if(!request.getParameter("user").isEmpty() && !request.getParameter("pass").isEmpty()){
			user = request.getParameter("user");
			pass = request.getParameter("pass");
		}
			
		if(passwdFile.containsKey(user) && pass.equals(passwdFile.get(user))){
			request.setAttribute("user", user);
			
			// vai enviar, caso usuário exista e senha bata com o usuário, para a página "loginValido.jsp"
			request.getRequestDispatcher("homepage.jsp").forward(request, response);
		}else
			response.sendRedirect("index.html"); //retorna para form de login
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><meta charset=\"UTF-8\"><title>Bem vindo, "+user+"!</title></head><body>");
		out.println("<h1>Dados</h1><h2>Login:"+user+"</h1>");
		out.println("<h2>Senha: "+pass+"</h2>");
		out.println("</body>");
		
		response.getWriter().append("<h6>Served at: ").append(request.getContextPath()).append("</h6></html>");
		
	}

}
