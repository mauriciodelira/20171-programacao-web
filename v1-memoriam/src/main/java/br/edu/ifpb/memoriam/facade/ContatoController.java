package br.edu.ifpb.memoriam.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.memoriam.dao.ContatoDAO;
import br.edu.ifpb.memoriam.dao.OperadoraDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.dao.UsuarioDAO;
import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Operadora;
import br.edu.ifpb.memoriam.entity.Perfil;
import br.edu.ifpb.memoriam.entity.Usuario;

public class ContatoController {
	private Contato contato; // fica aqui para ser acessível tanto a isParametrosValidos quanto ao método que o chama.
	private Usuario logado = null;
	
	public ContatoController(Usuario user){
		this.logado = user;
	}
	
	public List<Contato> consultar(){
		ContatoDAO dao = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
		List<Contato> list = null;
		if(logado!=null){
			if(logado.getPerfil().equals(Perfil.BASIC)){
				list = dao.findAllFromUser(logado);// busca todas as instâncias de Contato do Usuário no BD e retorna uma lista
			}
			else if(logado.getPerfil().equals(Perfil.ADMIN)){
				list = dao.findAll();
			}
		}
		return list;
	}

	public Resultado cadastrar(Map<String, String[]> params) { // recebe um Map de todos os parâmetros do HTTP
		Resultado res = new Resultado();
		res = isParametrosValidos(params);
		if(!res.isErro()){
			ContatoDAO dao = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
			OperadoraDAO daoOp = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
			
			if(!params.get("idCt")[0].equals("")) this.contato = dao.find(Integer.parseInt(params.get("idCt")[0]));
			else this.contato.setId(null);
				dao.beginTransaction();
			
//			Abriu a transação com o entity manager
			if(this.contato.getId() == null){
				dao.insert(this.contato);
			}else{
				this.contato.setFone(params.get("foneCt")[0]);
				this.contato.setNome(params.get("nomeCt")[0]);
				this.contato.setOperadora(daoOp.find(Integer.parseInt(params.get("operadoraCt")[0])));
				dao.update(this.contato);
			}
			dao.commit();
		
			res.setErro(false);
			res.addMensagem(new Mensagem("Contato salvo com sucesso!", Categoria.INFO));
		
		}else{
			res.setEntidade(this.contato);
			res.setErro(true);
//			res.addMensagens(this.mensagensErro, Categoria.ERRO);
		}
		return res;
			
	}
	
	private Resultado isParametrosValidos(Map<String, String[]> params){
		String[] id = params.get("idCt");
		String[] nome = params.get("nomeCt");
		String[] fone = params.get("foneCt");
		String[] aniv = params.get("dataaniv");
		
		this.contato = new Contato();
		Resultado res = new Resultado();
		res.setErro(false);
		
//		Confere que de todas as formas o parâmetro existe
		if(id!=null && id.length>0 && !id[0].isEmpty()){ // se não é nulo, se não é string vazia: "", e se existe pelo menos 1 id ([0])
			System.out.println("== isParamsValidos: ID: "+id[0]);
			contato.setId(Integer.parseInt(id[0]));
		}else
			System.out.println("== isParamsValidos: NÃO EXISTE ID");
		
		
		if(nome==null || nome.length==0 || nome[0].isEmpty()){ 
			res.addMensagem(new Mensagem("O campo \"Nome\" é obrigatório.", Categoria.AVISO));
			res.setErro(true);
//			this.mensagensErro.add("O campo \"Nome\" é obrigatório.");
		}else
			contato.setNome(nome[0]);

		//		Agora a ideia é a mesma de acima, mas verificada diferentemente
		if(fone==null || fone.length==0 || fone[0].isEmpty()){ // se é nulo OU se é string vazia: "" OU se o primeiro id é vazio [0]isEmpty
			res.addMensagem(new Mensagem("O campo \"Fone\" é obrigatório.", Categoria.AVISO));
			res.setErro(true);
//			this.mensagensErro.add("O campo \"Fone\" é obrigatório.");
		}else{
			if(fone[0].matches("[9]?[1-9][0-9][0-9][0-9]\\-?[0-9][0-9][0-9][0-9]")){
//				String parsedFone = fone[0].replaceFirst( "\\(?(\\d{2})?\\)? ?(\\9?\\d{4}){1}\\-? ?(\\d{4})", "$1$2$3");
				contato.setFone(fone[0]);
			}else{
				res.addMensagem(new Mensagem("Formato de telefone inválido. 9XXXX-XXXX (Você colocou: "+fone[0]+").", Categoria.ERRO));
				res.setErro(true);
//				this.mensagensErro.add("Formato de telefone inválido. 9XXXX-XXXX (Você colocou: "+fone[0]+").");
			}
		}
		
		if(aniv==null || aniv.length==0 || aniv[0].isEmpty()){
			res.addMensagem(new Mensagem("O campo \"Data\" é obrigatório.", Categoria.AVISO));
			res.setErro(true);
//			this.mensagensErro.add("O campo \"Data\" é obrigatório.");
		}else{
			if(aniv[0].matches(
					"(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/(19|20)\\d{2,2}")){
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					sdf.setLenient(false);
					Date parsedAniv = sdf.parse(aniv[0]);
					contato.setDataAniversario(parsedAniv);
				}catch(ParseException e){
					res.addMensagem(new Mensagem("Data inválida para data de aniversário.", Categoria.ERRO));
					res.setErro(true);
//					this.mensagensErro.add("Data inválida para data de aniversário.");
				}
			} else{
				if(aniv[0].matches("(19|20)\\d{2,2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])")){
					try{
						SimpleDateFormat sdfOrig = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdfFinal = new SimpleDateFormat("dd/MM/yyyy");
						
						sdfOrig.setLenient(false);
						Date parsedAnivOrig = sdfOrig.parse(aniv[0]);
						String dataFinal = sdfFinal.format(parsedAnivOrig);
						Date parsedAnivFinal = sdfFinal.parse(dataFinal);
						System.out.println("Data dd/MM/yyyy: "+parsedAnivFinal);
						System.out.println("Data STRING dd/MM/yyyy: "+dataFinal);
						contato.setDataAniversario(parsedAnivFinal);
					}catch(ParseException e){
						res.addMensagem(new Mensagem("Data inválida para data de aniversário.", Categoria.ERRO));
						res.setErro(true);
//						this.mensagensErro.add("Data inválida para data de aniversário.");
					}
				}else
					System.out.println("Data aniversário inválida: "+aniv[0]);
					res.addMensagem(new Mensagem("Formato de data inválido. Formato: dd/mm/aaaa.", Categoria.ERRO));
					res.setErro(true);
//					this.mensagensErro.add("Formato de data inválido. Formato: dd/mm/aaaa.");
				}
		}
		Operadora operadora = null;
		String idOperadora = params.get("operadoraCt")[0];
		if(idOperadora!=null){
			OperadoraDAO opDao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
			operadora = opDao.find(Integer.parseInt(idOperadora));
		}
		contato.setOperadora(operadora);
		contato.setUsuario(logado);
		
		return res; // retorna se o array é vazio ou não
	}
	
	public Resultado buscar(Map<String, String[]> params){
		ContatoDAO dao = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
		Resultado res = new Resultado();
		String[] idContato = params.get("idCt");
		Contato c = null;
		
		c = dao.find(Integer.parseInt(idContato[0])); // busca todas as instâncias de Contato no BD e retorna uma lista
		if(c==null){
			res.setErro(true);
			res.addMensagem(new Mensagem("Contato ainda não cadastrado.", Categoria.AVISO));
			return null;
		}
		if(logado.getPerfil().equals(Perfil.ADMIN) || c.getUsuario().getEmail().equals(logado.getEmail()))
			res.setEntidade(c);
		else{
			res.setErro(true);
			res.addMensagem(new Mensagem("Não encontramos esse contato em sua agenda.", Categoria.ERRO));
			res.setEntidade(null);
		}
		return res;
	}
	
	public Contato buscar(String id){
		ContatoDAO dao = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
		Contato c = null;
		c = dao.find(Integer.parseInt(id)); // busca todas as instâncias de Contato no BD e retorna uma lista
		if(logado.getPerfil().equals(Perfil.ADMIN) || c.getUsuario().getEmail().equals(logado.getEmail()))
			return c;
		else{
			return null;
		}
		
	}
	

//  Pesquisar por nome ou telefone (ou parte deles)
	public Resultado pesquisaResultado(Map<String, String[]> params, Usuario logado) {
		ContatoDAO dao = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
		Resultado res = new Resultado();
		String[] parte = params.get("q");
		List<Contato> resposta = null;
		
		if(parte.length>0 && !parte[0].isEmpty()){
			resposta = dao.pesquisarPorParte(parte[0], logado);
			res.setErro(false);
			
		}else{
			res.setErro(true);
			res.addMensagem(new Mensagem("Não encontramos nenhum contato com os termos: "+parte[0]+"."
					+ " Tente realizar a pesquisa de novo com outras palavras.", Categoria.ERRO));
		}
		
		res.setEntidade(resposta);
		return res;
	}

//	 RETORNANDO UMA LISTA DIRETO
	public List<Contato> pesquisa(Map<String, String[]> params, Usuario logado) {
		ContatoDAO dao = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
		String[] parte = params.get("q");
		List<Contato> resposta = null;
		
		if(parte.length>0 && !parte[0].isEmpty()){
			resposta = dao.pesquisarPorParte(parte[0], logado);
		}
		
		return resposta;
	}
	
	public Resultado deletar(Contato contato){
		Resultado res = new Resultado();
		ContatoDAO dao = new ContatoDAO(PersistenceUtil.getCurrentEntityManager());
		if(logado.getPerfil().equals(Perfil.ADMIN) || contato.getUsuario().getEmail().equals(logado.getEmail())){
			dao.beginTransaction();
			dao.delete(contato);
			dao.commit();
		}else{
			res.addMensagem(new Mensagem("Você não pode deletar o contato de outra pessoa!", Categoria.ERRO));
			res.setErro(true);
		}
		if(dao.find(contato.getId())==null){
			res.setErro(false);
			res.addMensagem(new Mensagem("Contato deletado com sucesso!", Categoria.INFO));
		}else{
			res.setErro(true);
			res.addMensagem(new Mensagem("Não foi possível apagar o contato. Verifique o log.", Categoria.ERRO));
			res.setEntidade(contato);
		}
		return res;
		
	}

	public Resultado deletar(String[] delCts) {
		Resultado res = new Resultado();
		
		if(delCts!=null && delCts.length>0 && !delCts[0].isEmpty()){
			for(String id : delCts){
				Contato ct= null;
				
				if(id!=null){
					ct = this.buscar(id);
				}
				if(ct!=null){
					res = this.deletar(ct);
				}
			}
		}
		
		return res;
	}
}
