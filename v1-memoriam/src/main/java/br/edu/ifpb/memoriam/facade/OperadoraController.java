package br.edu.ifpb.memoriam.facade;

import java.util.List;
import java.util.Map;

import br.edu.ifpb.memoriam.dao.OperadoraDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.entity.Operadora;
import br.edu.ifpb.memoriam.entity.Perfil;
import br.edu.ifpb.memoriam.entity.Usuario;

public class OperadoraController {
	private Operadora operadora;
	private Usuario logado;
	
	public OperadoraController(Usuario user){
		this.logado = user;
	}
	
//	 Consultar todas as operadoras
	public List<Operadora> consultar() {
		// Criar o DAO puxando o EM da sessão se existir
		OperadoraDAO dao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
		
		List<Operadora> list = dao.findAll();
		
		return list;
	}

//	 Buscar recebendo todos os parâmetros
	public Resultado buscar(Map<String, String[]> params){
		OperadoraDAO dao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
		Resultado res = new Resultado();
		String[] idOp = params.get("id");
		
		if(idOp.length>0 && !idOp[0].isEmpty()){
		  operadora = dao.find(Integer.parseInt(idOp[0]));
		  res.setErro(false);
		}
		if(operadora == null){
			res.setErro(true);
			res.addMensagem(new Mensagem("Operadora não encontrada.", Categoria.ERRO));
			res.setEntidade(null);
			return null;
		}
		if(logado.getPerfil().equals(Perfil.ADMIN))
			res.setEntidade(operadora);
		else{
			res.setErro(true);
			res.addMensagem(new Mensagem("Você não tem permissão para acessar esta página.", Categoria.ERRO));
			res.setEntidade(null);
		}
		return res;
	}
	
//	 Buscar recebendo apenas o ID
	public Operadora buscar(String id){
		OperadoraDAO dao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
		
		if(!id.isEmpty() && id!=null && !id.equals(""))
		  this.operadora = dao.find(Integer.parseInt(id));
		
		return this.operadora;
	}
	
//	 Deletar passando objeto
	public Resultado deletar(Operadora op){
		Resultado res = new Resultado();
		OperadoraDAO dao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
		
		try{
			dao.beginTransaction();
			dao.delete(op);
			dao.commit();
		}catch(Exception e){
			res.setErro(true);
			res.setEntidade(op);
			res.addMensagem(new Mensagem("Não foi possível excluir a operadora. "
					+ "Veja se ela possui algum cliente cadastrado e tente novamente.",Categoria.ERRO));
			return res;
		}
		
		if(dao.find(op.getId())==null){
			res.setErro(false);
			res.addMensagem(new Mensagem("Operadora deletada com sucesso!", Categoria.INFO));
		}
		else{
			res.setErro(true);
			res.setEntidade(op);
		}
		
		return res;
	}
	
//	 Deletar em batch, várias operadoras de uma só vez
	public Resultado deletar(String[] delOps) {
		Resultado res = new Resultado();
		
		if(delOps!=null && delOps.length>0 && !delOps[0].isEmpty()){
			for(String id : delOps){
				Operadora oper = null;
				if(id!=null){
					oper = this.buscar(id);
				}
				if(oper!=null){
					res = this.deletar(oper);
				}
			}
		}
		
		return res;
	}
	
//	 Cadastrar nova operadora
	public Resultado cadastrar(Map<String, String[]> params){
		Resultado res = new Resultado();
		res = isParametrosValidos(params);
		if(!res.isErro()){
			OperadoraDAO dao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
			
//			Vai tentar buscar a operadora pelo ID no banco
			if(!params.get("idOp")[0].equals(""))
				this.operadora = dao.find(Integer.parseInt(params.get("idOp")[0]));
			else this.operadora.setId(null);
			dao.beginTransaction();
			
//			Se ele não encontrou nada pelo ID na busca anterior, faça...
			if(this.operadora.getId()==null){
				dao.insert(this.operadora);
			}else{
				this.operadora.setPrefixo(Integer.parseInt(params.get("prefixoddd")[0]));
				this.operadora.setNome(params.get("nomeOp")[0]);
				dao.update(this.operadora);
			}
			dao.commit();
			
			res.setErro(false);
			res.addMensagem(new Mensagem("Operadora salva com sucesso.", Categoria.INFO));
		}else{
			res.setEntidade(this.operadora);
			res.setErro(true);
//			res.setMensagensErro(this.mensagensErro);
		}
		
		return res;
	}

	private Resultado isParametrosValidos(Map<String, String[]> params) {
		String[] id = params.get("idOp");
		String[] nome = params.get("nomeOp");
		String[] ddd = params.get("prefixoddd");
		Resultado res = new Resultado();
		
		this.operadora = new Operadora();
		
//		Verificações:
		if(id!=null && id.length>0 && !id[0].isEmpty()){ // se o ID não é nulo
			operadora.setId(Integer.parseInt(id[0]));
		}
		
		if(nome==null||nome.length==0||nome[0].isEmpty()){
			res.addMensagem(new Mensagem("Campo \"Nome\" é obrigatório.", Categoria.AVISO));
			res.setErro(true);
		}else{
			operadora.setNome(nome[0]);
		}
		
		if(ddd==null||ddd.length==0||ddd[0].isEmpty()){
			res.addMensagem(new Mensagem("Especifique um DDD.",Categoria.ERRO));
			res.setErro(true);
		}
		else {
			if(ddd[0].matches("[0-9]?[0-9][0-9]"))
				operadora.setPrefixo(Integer.parseInt(ddd[0]));
			else{
				res.addMensagem(new Mensagem("DDD incorreto. Insira de 1 a 3 dígitos e tente novamente.", Categoria.ERRO));
				res.setErro(true);
			}
		}
		
		return res;
	}

}
