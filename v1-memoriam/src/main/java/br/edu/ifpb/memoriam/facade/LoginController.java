package br.edu.ifpb.memoriam.facade;

import java.util.Map;

import br.edu.ifpb.memoriam.dao.UsuarioDAO;
import br.edu.ifpb.memoriam.entity.Usuario;
import br.edu.ifpb.memoriam.util.PasswordUtil;

public class LoginController {
	

	public Resultado isValido(Map<String, String[]> params){
		Resultado r = new Resultado();
		r.setErro(false);

		String[] email = params.get("loginEmail");
		String[] senha = params.get("loginSenha");
		UsuarioDAO udao = new UsuarioDAO();
		Usuario usuario = null;
		
//		Confere que de todas as formas o parâmetro email existe
		if(email!=null && email.length>0 && !email[0].isEmpty()){ // se não é nulo, se não é string vazia: "", e se existe pelo menos 1 email no mapa ([0])
			usuario = udao.findByLogin(email[0]);
		}else{
			r.setErro(true);
			r.addMensagem(new Mensagem("Email inválido.", Categoria.ERRO));
		}
		
//		  Valida a senha com o email do usuário
		if(usuario!=null){
			if(senha==null || senha.length==0 || senha[0].isEmpty()){
				r.setErro(true);
				r.addMensagem(new Mensagem("Senha inválida.", Categoria.ERRO));
			}else{
				if(usuario.getSenha().equals(PasswordUtil.encryptMD5(senha[0]))){
					r.setEntidade(usuario);
					r.setErro(false);
				}
				else{
					r.setErro(true);
					r.addMensagem(new Mensagem("Usuário ou senha incorretos.", Categoria.AVISO));
				}
			}
		}else{
			r.setErro(true);
			r.addMensagem(new Mensagem("Email não cadastrado.", Categoria.ERRO));
		}
		return r; // retorna o resultado contendo as mensagens
	}
}
