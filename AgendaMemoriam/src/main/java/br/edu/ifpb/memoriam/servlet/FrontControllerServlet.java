package br.edu.ifpb.memoriam.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifpb.memoriam.entity.Contato;

/**
 * Servlet implementation class FrontControllerServlet
 */
@WebServlet("/FrontControllerServlet.do")
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontControllerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ContatoController contatoCtrl = new ContatoController();
		String proxPagina = null;
		
		this.getServletContext().removeAttribute("msgs");
		
		String operacao = request.getParameter("op");
		if(operacao == null){
			this.getServletContext().setAttribute("msgs", "Operação (op) não especificada na requisição.");
			response.sendRedirect(request.getHeader("Referer"));
			
			return;
		}
		
		switch(operacao) {
		case "conctt":
			List<Contato> contatos = contatoCtrl.consultar();
			request.setAttribute("contatos", contatos);
			
			proxPagina = "contato/consulta.jsp";
			break;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(proxPagina);
		dispatcher.forward(request, response);
	}

}
