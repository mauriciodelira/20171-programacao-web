package br.edu.ifpb.memoriam.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.ifpb.memoriam.entity.Usuario;
import br.edu.ifpb.memoriam.facade.Categoria;
import br.edu.ifpb.memoriam.facade.Mensagem;
import br.edu.ifpb.memoriam.facade.Resultado;

/**
 * Este filtro faz a verificação se há algum usuário na sessão logado ou não.
 * FIXME falta resolver o problema das mensagens, que acumulam.
 */
//@WebFilter(urlPatterns = {"*.do", "*.jsp"})
public class IsLogadoFilter implements Filter {
	private Usuario logado = null;
	private Resultado res;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String query = req.getQueryString();
		String getpost = req.getMethod();
		String reqUri = req.getRequestURI();
		
		System.out.println("_______________");
		System.out.println("- query: "+query);
		System.out.println("- uri: "+reqUri);
		System.out.println("- logado: "+session.getAttribute("usuario"));
		System.out.println("- msgs: "+ (req.getServletContext().getAttribute("msgs")!=null ? ((List<Mensagem>) req.getServletContext().getAttribute("msgs")).size() : req.getServletContext().getAttribute("msgs")) );
		System.out.println("");

		if(query != null){
			if(query.equals("op=login")){
				System.out.println("---- op == login");
				req.getServletContext().removeAttribute("msgs");
				chain.doFilter(request, response);
				return;
			}else{
				System.out.println("---- op != login");
				req.getServletContext().removeAttribute("msgs");
			}
		}else if(getpost.equalsIgnoreCase("post")){
			System.out.println("---- metodo: "+getpost);
			req.getServletContext().removeAttribute("msgs");
			chain.doFilter(request, response);
			return;
		}
		
		if(reqUri.equals("/memoriam/") || reqUri.equals("/memoriam/login/login.jsp") || reqUri.equals("/erro/")){
			System.out.println("---- reqUri: "+reqUri);
			req.getServletContext().removeAttribute("msgs");
			if(session.getAttribute("usuario")!=null){
				resp.sendRedirect("controller.do?op=showct");
				return;
			}
			chain.doFilter(request, response);
			return;
		}
		else {
			if(session.getAttribute("usuario") == null){
				this.res = new Resultado();
				System.out.println("---- usuário não está logado e tentou acessar: "+reqUri+", query: "+query);
//				Usuário não está logado
				res.addMensagem(new Mensagem("Opa, me parece que você não está logado. Inicie sua sessão para acessar aquela página. :)", Categoria.ERRO));
				res.setErro(true);
				request.setAttribute("msgs", res.getMensagens());
				RequestDispatcher rd = request.getRequestDispatcher("login/login.jsp");
				rd.forward(request, response);
				return;
			}else{
				chain.doFilter(request, response);
				return;
			}
			
		}
		
		
		
//		if(!reqUri.equals("/memoriam/") && !reqUri.equals("/memoriam/login/login.jsp") && !reqUri.equals("/memoriam/erro/")
//				&& (req.getAttribute("op")==null || !req.getAttribute("op").equals("login")))
//		{
//			this.res = new Resultado();
//			
//			if(session!=null){
////				Existe uma sessão
//				if(session.getAttribute("usuario") == null){
////					Não há usuário logado.
//					res.addMensagem(new Mensagem("Opa, me parece que você não está logado. Por favor, entre para poder acessar aquela página. :)", Categoria.AVISO));
//					res.setErro(true);
//					req.getServletContext().setAttribute("msgs", res.getMensagens());
//					RequestDispatcher rd = req.getRequestDispatcher("login/login.jsp");
//					rd.forward(req, resp);
//					return;
//				}
//			}
//		}
//		else{
//			System.out.println("reqUri == /memoriam, /login/login.jsp, /erro OU ENTÃO attr.op existe null ou op == login");
//			res.setErro(false);
//			res.setMensagens(null);
//			chain.doFilter(req, resp);
//			return;
//		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void destroy() {
	}
}
