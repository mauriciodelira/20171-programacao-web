package br.edu.ifpb.memoriam.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Operadora;
import br.edu.ifpb.memoriam.entity.Usuario;
import br.edu.ifpb.memoriam.facade.Categoria;
import br.edu.ifpb.memoriam.facade.ContatoController;
import br.edu.ifpb.memoriam.facade.LoginController;
import br.edu.ifpb.memoriam.facade.Mensagem;
import br.edu.ifpb.memoriam.facade.OperadoraController;
import br.edu.ifpb.memoriam.facade.Resultado;
import br.edu.ifpb.memoriam.facade.UsuarioController;

@WebServlet("/controller.do")
public class FrontControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

/* Joffily e Mariana - Reflection + Routes
 * 
 * Utilizaram o Reflection (pacote do java)
 * 
 * Utilizaram também sistemas de rotas (vide o repo de Gerência de Estágios/Routes do github de joffily)
 * https://github.com/joffilyfe/gerencia-de-estagios/
 * 
 * */	
	
//	doGet Só direciona para as páginas de consulta/cadastro ao clicar nos respectivos botões
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		this.getServletContext().removeAttribute("msgs");
		Resultado res = new Resultado();
		
		HttpSession sess = req.getSession();
		Usuario logado = (Usuario) sess.getAttribute("usuario");
		
//		  Antes de realizar qualquer coisa, se o usuário fizer um Get, avalie se ele está logado ou não.
		if(logado==null){
			res.addMensagem(new Mensagem("Opa, me parece que você não está logado. Por favor, efetue seu login abaixo. :)", Categoria.AVISO));
		    this.getServletContext().setAttribute("msgs", res.getMensagens());
		    resp.sendRedirect("login/login.jsp");
		    return;
		}
		
//		  Antes de criar qualquer variável, ele checa primeiro se a Operação doGet foi definida.
		String op = req.getParameter("op");
		if(op==null){
			res.addMensagem(new Mensagem("Operação não especificada na requisição.", Categoria.AVISO));
			this.getServletContext().setAttribute("msgs", res.getMensagens());
			resp.sendRedirect("login/login.jsp");
			return;
		}
		
//		  Se chegou aqui, é porque existia uma operação definida para o doGet
		ContatoController contatoCtrl = new ContatoController(logado);
		OperadoraController operadoraCtrl = new OperadoraController(logado);
		UsuarioController userCtrl = new UsuarioController(logado);
		String proxPagina = null;
		
		switch(op){
//		Pesquisar contatos ---------------------------------------------
		case "pesquisar":
			List<Contato> contatosPesq = contatoCtrl.pesquisa(req.getParameterMap(), logado);
			req.setAttribute("contatos", contatosPesq);
			proxPagina = "contato/consulta.jsp";
			break;
			
//		  consultar contato ---------------------------------------------
		case "showct": 
			List<Contato> contatos = contatoCtrl.consultar();
			req.setAttribute("contatos", contatos);
			proxPagina = "contato/consulta.jsp";
			break;
			
//		  editar contato (recebe ID) ---------------------------------------------
		case "editct": 
			Resultado resct = contatoCtrl.buscar(req.getParameterMap());
			Contato c = (!resct.isErro() ? (Contato) resct.getEntidade() : null);
			this.getServletContext().setAttribute("msgs", resct.getMensagens());
			req.setAttribute("contato", c);
			if(!resct.isErro())
				proxPagina = "contato/cadastro.jsp";
			else{
				proxPagina = "erro/";
			}
			break;		
			
			
//			Exibir USUÁRIOS ---------------------------------------------
		case "showus":
			List<Usuario> users = userCtrl.consultar();
			req.setAttribute("usuarios", users);
			proxPagina = "usuario/consulta.jsp";
			break;
			
//			Editar USUÁRIOS ---------------------------------------------
		case "editus":
			Resultado resus = userCtrl.buscar(req.getParameterMap());
			Usuario user = (!resus.isErro() ? (Usuario) resus.getEntidade() : null);
			this.getServletContext().setAttribute("msgs", resus.getMensagens());
			req.setAttribute("user", user);
			if(!resus.isErro()){
				proxPagina = "usuario/cadastro.jsp";
			}else{
				res.addMensagem(new Mensagem("Você não tem permissões para acessar essa área.", Categoria.AVISO));
				proxPagina = "erro/";
			}
			break;
			
//			Exibir OPERADORAS ---------------------------------------------
		case "showop":
			List<Operadora> operadoras = operadoraCtrl.consultar();
			req.setAttribute("operadoras", operadoras);
			proxPagina = "operadora/consulta.jsp";
			break;
			
//			Editar operadoras ---------------------------------------------
		case "editop":
			Resultado resop = operadoraCtrl.buscar(req.getParameterMap());
			Operadora oper = (!resop.isErro() ? (Operadora) resop.getEntidade() : null);
			this.getServletContext().setAttribute("msgs", resop.getMensagens());
			req.setAttribute("operadora", oper);
			if(!resop.isErro())
				proxPagina = "operadora/cadastro.jsp";
			else{
				res.addMensagem(new Mensagem("Você não tem permissões para acessar essa área.", Categoria.AVISO));
				proxPagina = "erro/";
			}
			break;

		default:
			proxPagina = "erro/";
			res.addMensagem(new Mensagem("Operação não especificada no servlet.", Categoria.AVISO));
			this.getServletContext().setAttribute("msgs", res.getMensagens());
		}
		
		System.out.println("DOGET - proxPagina: "+proxPagina);
		RequestDispatcher rd = req.getRequestDispatcher(resp.encodeRedirectURL(proxPagina)); //envia a url codificada caso os cookies estejam desabilitados
		rd.forward(req, resp);
		
	}

//	Realiza algumas regras para 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		this.getServletContext().removeAttribute("msgs");
		Resultado resultado = new Resultado();
		
//		  Antes de criar qualquer variável, ele checa primeiro se a Operação doPost foi definida.
		String op = req.getParameter("op");
		if(op == null){
			resultado.addMensagem(new Mensagem("Operação não especificada na requisição.", Categoria.AVISO));
			this.getServletContext().setAttribute("msgs", resultado.getMensagens());
			resp.sendRedirect("login/login.jsp");
			return;
		}
		
		HttpSession sess = req.getSession();
		Usuario logado = (Usuario) sess.getAttribute("usuario");
		ContatoController contatoCtrl = new ContatoController(logado);
		OperadoraController operadoraCtrl = new OperadoraController(logado);
		LoginController loginCtrl = new LoginController();
		UsuarioController userCtrl = new UsuarioController(logado);
		
		
//		Dá pra colocar os links abaixo (sucesso/erro) com a tag <c:url />
		String paginaSucessoCt = "controller.do?op=showct";
		String paginaErroCt = "contato/cadastro.jsp";
		String paginaSucessoOp = "controller.do?op=showop";
		String paginaErroOp = "operadora/cadastro.jsp";
		String paginaSucessoUs = "controller.do?op=showus";
		String paginaErroUs = "usuario/cadastro.jsp";
		String proxPagina = null;

		
		switch(op){
//		  Novo contato ----------
			case "newct":
				resultado = contatoCtrl.cadastrar(req.getParameterMap());
				if(!resultado.isErro()){
					proxPagina = paginaSucessoCt;
					req.setAttribute("msgs",  resultado.getMensagens());
				}else{
					req.setAttribute("contato",  (Contato) resultado.getEntidade());
					req.setAttribute("msgs", resultado.getMensagens());
					proxPagina = paginaErroCt;
				}
				break;
	
	//		  Deletar um contato ----
			case "delct":
				String[] deletarCtts = req.getParameterValues("contatosPraDeletar");			
				
				resultado = contatoCtrl.deletar(deletarCtts);
				
				if(deletarCtts!=null && deletarCtts.length>0 && !deletarCtts[0].isEmpty()){
					for(String id : deletarCtts){
						Contato ctt = null;
						if(id!=null)
							ctt = contatoCtrl.buscar(id);
						if(ctt!=null){
							resultado = contatoCtrl.deletar(ctt);
						}
					}
				}
				
				if(!resultado.isErro()){
					proxPagina = paginaSucessoCt;
					req.setAttribute("msgs",  resultado.getMensagens());
				}else{
					req.setAttribute("contato",  (Contato) resultado.getEntidade());
					req.setAttribute("msgs", resultado.getMensagens());
					proxPagina = paginaErroCt;
				}
				break;
				
			case "newus":
				resultado = userCtrl.cadastrar(req.getParameterMap());
				if(!resultado.isErro()){
					proxPagina = paginaSucessoUs;
					req.setAttribute("msgs", resultado.getMensagens());
				}else{
					req.setAttribute("msgs", resultado.getMensagens());
					req.setAttribute("usuario", resultado.getEntidade());
					proxPagina = paginaErroUs;
				}
				break;

			case "delus":
				String[] delUs = req.getParameterValues("marcadosParaDeletar");
				resultado = userCtrl.deletar(delUs);
				proxPagina = paginaSucessoUs;
				break;
				
	//		  Nova operadora  ---------
			case "newop":
				resultado = operadoraCtrl.cadastrar(req.getParameterMap());
				if(!resultado.isErro()){
					proxPagina = paginaSucessoOp;
					req.setAttribute("msgs",  resultado.getMensagens());
				}else{
					req.setAttribute("operadora",  (Operadora) resultado.getEntidade());
					req.setAttribute("msgs", resultado.getMensagens());
					proxPagina = paginaErroOp;
				}
				break;
				
	//		  Deletar operadora(s) ---
			case "delop":
				String[] delOps = req.getParameterValues("marcadosParaDeletar");
				resultado = operadoraCtrl.deletar(delOps);
				
				if(!resultado.isErro()){
					proxPagina = paginaSucessoOp;
					req.setAttribute("msgs",  resultado.getMensagens());
				}else{
					List<Operadora> operadoras = operadoraCtrl.consultar();
					req.setAttribute("operadoras", operadoras);
					req.setAttribute("operadora",  (Operadora) resultado.getEntidade());
					req.setAttribute("msgs", resultado.getMensagens());
					proxPagina = "controller.do?op=showop";
				}
				break;
				
//				Login do usuário na sessão ---
			case "login":
				String paginaSucessoLogin = "controller.do?op=showct";
				String paginaErroLogin = "login/login.jsp";
				resultado = loginCtrl.isValido(req.getParameterMap());

				if(resultado.isErro()){
					req.setAttribute("msgs", resultado.getMensagens());
					proxPagina = paginaErroLogin;
				}else{
					sess.setAttribute("usuario", (Usuario) resultado.getEntidade());
					System.out.println("Login: "+resultado.getEntidade()!=null ? ((Usuario)resultado.getEntidade()).getNome() : "Não setado.");
					proxPagina = paginaSucessoLogin;
					
//					  Mantém o email em cookies
					String lembrar = req.getParameter("lembrar");
					if(lembrar!=null){
						Cookie ck = new Cookie("loginCookie", ((Usuario) sess.getAttribute("usuario")).getEmail());
						ck.setMaxAge(-1);
						resp.addCookie(ck);
					}else{
						for(Cookie cookie : req.getCookies()) {
							if(cookie.getName().equals("loginCookie"));
							cookie.setValue(null);
							cookie.setMaxAge(0);
							resp.addCookie(cookie);
						}
					}
				}
				break;
				
//				Logout do usuário na sessão ------------
			case "logout":
				System.out.println("DOPOST - Entrou no LOGOUT");
				proxPagina = "login/login.jsp";
				sess.invalidate();
				resultado.setErro(false);
				break;
				
			default:
				req.setAttribute("erro", "Operação não especificada no servlet.");
				proxPagina = "erro";
				break;
		} // final do Switch
		
		if(resultado.isErro()){
			System.out.println("DOPOST - resultado tem erro :x proxPagina: "+proxPagina);
			RequestDispatcher rd = req.getRequestDispatcher(proxPagina);
			rd.forward(req, resp);
			return;
		}else{
			System.out.println("DOPOST - resultado sem erro. ProxPagia: "+proxPagina);
			resp.sendRedirect(proxPagina);
			
		}
	}

}
