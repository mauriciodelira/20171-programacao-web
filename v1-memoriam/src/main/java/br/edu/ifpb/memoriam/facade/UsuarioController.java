package br.edu.ifpb.memoriam.facade;

import java.util.List;
import java.util.Map;

import br.edu.ifpb.memoriam.dao.ContatoDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.dao.UsuarioDAO;
import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Perfil;
import br.edu.ifpb.memoriam.entity.Usuario;
import br.edu.ifpb.memoriam.util.PasswordUtil;

public class UsuarioController {
	private Usuario logado = null;
	private Usuario usuario = null;
	
	public UsuarioController(Usuario logado){
		this.logado = logado;
	}
	
	public List<Usuario> consultar(){
		UsuarioDAO dao = new UsuarioDAO(PersistenceUtil.getCurrentEntityManager());
		List<Usuario> usrs = null;
		
		if(logado!=null){
			if(logado.getPerfil().equals(Perfil.ADMIN)){
				usrs = dao.findAll();
			}
		}
		
		return usrs;
	}
	
//	Buscar só com o ID
	public Usuario buscar(String id){
		UsuarioDAO dao = new UsuarioDAO(PersistenceUtil.getCurrentEntityManager());
		Usuario usr = null;
		if(!id.isEmpty() && id!=null && !id.equals(""))
//			usr = dao.find(Integer.parseInt(id));
			usr = dao.findById(id);
		return usr;
	}

//	Buscar com todos os parâmetros do request
	public Resultado buscar(Map<String, String[]> params) {
		Resultado res = new Resultado();
		UsuarioDAO dao = new UsuarioDAO(PersistenceUtil.getCurrentEntityManager());
		String[] id = params.get("id");
		
		if(id.length>0 && !id[0].isEmpty()){
			usuario = this.buscar(id[0]);
			res.setErro(false);
		}
		if(usuario == null){
			res.setErro(true);
			res.addMensagem(new Mensagem("Usuário não encontrado.", Categoria.ERRO));
			res.setEntidade(null);
		}
		if(logado.getPerfil().equals(Perfil.ADMIN))
			res.setEntidade(usuario);
		else{
			res.setErro(true);
			res.addMensagem(new Mensagem("Você não tem permissão para acessar esta página.", Categoria.ERRO));
			res.setEntidade(null);
		}
		
		return res;
	}

	public Resultado cadastrar(Map<String, String[]> params) {
		Resultado res = new Resultado();
		res = isParametrosValidos(params);
		if(!res.isErro()){
			System.out.println("Não tem erros, parâmetros são válidos");
			UsuarioDAO dao = new UsuarioDAO(PersistenceUtil.getCurrentEntityManager());
			
			if(!params.get("id")[0].equals(""))
				this.usuario = dao.find(Integer.parseInt(params.get("id")[0]));
			else this.usuario.setId(null);
			
			dao.beginTransaction();
			if(this.usuario.getId()==null){
				dao.insert(this.usuario);
			}
			else{
				this.usuario.setEmail(params.get("email")[0]);
				
				if(params.get("ativo")!=null && params.get("ativo").length>0 && !params.get("ativo")[0].isEmpty()){
					if(params.get("ativo")[0].equals("on"))
						this.usuario.setAtivo(true);
					else this.usuario.setAtivo(false);
				}else
					this.usuario.setAtivo(false);
				
				this.usuario.setNome(params.get("nome")[0]);
				this.usuario.setPerfil(Perfil.valueOf(params.get("perfil")[0]));
				this.usuario.setSenha(PasswordUtil.encryptMD5(params.get("senha")[0]));
				dao.update(this.usuario);
				
			}
			dao.commit();
			
			res.setErro(false);
			res.addMensagem(new Mensagem("Usuário salvo com sucesso!", Categoria.INFO));
		}else{
			res.setEntidade(this.usuario);
			res.setErro(true);
			res.addMensagem(new Mensagem("Não foi possível salvar o usuário.", Categoria.INFO));
		}
		
		return res;
	}

	private Resultado isParametrosValidos(Map<String, String[]> params) {
		String[] id = params.get("id");
		String[] perfil = params.get("perfil");
		String[] nome = params.get("nome");
		String[] email = params.get("email");
		String[] senha = params.get("senha");
		String[] ativo = params.get("ativo");
		Resultado res = new Resultado();
		res.setErro(false);
		
		this.usuario = new Usuario();
		
		if(id!=null && id.length>0 && !id[0].isEmpty()){
			this.usuario.setId(Integer.parseInt(id[0]));
			System.out.println(id[0]);
		}

		if(perfil==null || perfil.length==0 || perfil[0].isEmpty()){
			res.addMensagem(new Mensagem("Campo \"Perfil\" é obrigatório.", Categoria.AVISO));
			res.setErro(true);
		}else{
			System.out.println(perfil[0]);
			usuario.setPerfil(Perfil.valueOf(perfil[0]));
		}

		if(nome==null || nome.length==0 || nome[0].isEmpty()){
			res.addMensagem(new Mensagem("Campo \"Nome\" é obrigatório.", Categoria.AVISO));
			res.setErro(true);
		}else{
			System.out.println(nome[0]);
			usuario.setNome(nome[0]);
		}

		if(email==null || email.length==0 || email[0].isEmpty()){
			res.addMensagem(new Mensagem("Campo \"Email\" é obrigatório.", Categoria.AVISO));
			res.setErro(true);
		}else{
			System.out.println(email[0]);
			usuario.setEmail(email[0]);
		}

		if(senha==null || senha.length==0 || senha[0].isEmpty()){
			res.addMensagem(new Mensagem("Campo \"Senha\" é obrigatório.", Categoria.AVISO));
			res.setErro(true);
		}else{
			System.out.println(senha[0]);
			usuario.setSenha(PasswordUtil.encryptMD5(params.get("senha")[0]));
		}

		if(ativo==null || ativo.length==0 || ativo[0].isEmpty()){
			usuario.setAtivo(false);
		}else{
			if(ativo[0].equals("on"))
				usuario.setAtivo(true);
			else
				usuario.setAtivo(false);
		}
		res.setEntidade(this.usuario);
		return res;
	}

//	  Deletar em batch
	public Resultado deletar(String[] delUs) {
		Resultado res = new Resultado();
		
		if(delUs!=null && delUs.length>0 && !delUs[0].isEmpty()){
			for(String id : delUs){
				Usuario usr = null;
				if(id!=null){
					usr = this.buscar(id);
				}
				if(usr!=null){
					res = this.deletar(usr);
				}
			}
		}
		
		return res;
	}

//	  Deletar único
	private Resultado deletar(Usuario usr) {
		Resultado res = new Resultado();
		UsuarioDAO dao = new UsuarioDAO(PersistenceUtil.getCurrentEntityManager());
		ContatoDAO daoCt = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
		
		for(Contato c : daoCt.findAllFromUser(usr)){
			try{
				daoCt.beginTransaction();
				daoCt.delete(c);
				daoCt.commit();
			}catch(Exception e){
				res.setErro(true);
				res.setEntidade(usr);
				res.addMensagem(new Mensagem("Não foi possível excluir os contatos do usuário.", Categoria.ERRO));
			}
		}
		
		try{
			dao.beginTransaction();
			dao.delete(usr);
			dao.commit();
		}catch(Exception e){
			res.setErro(true);
			res.setEntidade(usr);
			res.addMensagem(new Mensagem("Não foi possível excluir o usuário.", Categoria.ERRO));
		}
		
		if(dao.find(usr.getId())==null){
			res.setErro(false);
			res.addMensagem(new Mensagem("Usuário deletado com sucesso!", Categoria.INFO));
			
		}
		else{
			res.setErro(true);
			res.setEntidade(usr);
		}
		
		return res;
	}

	
	

}
