package br.edu.ifpb.memoriam.bean;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.memoriam.dao.OperadoraDAO;
import br.edu.ifpb.memoriam.dao.PersistenceUtil;
import br.edu.ifpb.memoriam.dao.UsuarioDAO;
import br.edu.ifpb.memoriam.entity.Operadora;
import br.edu.ifpb.memoriam.entity.Perfil;
import br.edu.ifpb.memoriam.entity.Usuario;

public class UtilBean {
	
	public List<Operadora> getOperadoras(){
		OperadoraDAO dao = new OperadoraDAO(PersistenceUtil.getCurrentEntityManager());
		List<Operadora> ops = dao.findAll();
		return ops;
	}
	
	public List<Perfil> getPerfis(){
		UsuarioDAO dao = new UsuarioDAO(PersistenceUtil.getCurrentEntityManager());
		List<Usuario> users = dao.findAll();
		List<Perfil> perfis = new ArrayList<>();
		for(Usuario u : users){
			if(!perfis.contains(u.getPerfil())){
				perfis.add(u.getPerfil());				
			}
		}
		return perfis;
	}
	
	
}
