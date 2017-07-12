package br.edu.ifpb.memoriam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.edu.ifpb.memoriam.entity.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario, Integer> {

	public UsuarioDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public UsuarioDAO(EntityManager em) {
		super(em);
	}
	
	public Usuario findByLogin(String login) {
		System.out.println("Find by login");
		Query q = this.getEntityManager().createQuery("select u from Usuario u where u.email = :login");
		q.setParameter("login", login);
		System.out.println("Email: "+login);
		Usuario usuario = null;
		try {
			usuario = (Usuario) q.getSingleResult();
			System.out.println(usuario);
		} catch (NoResultException e) {
		}
		return usuario;
	}
	
	public Usuario findById(String intId) {
		TypedQuery<Usuario> q = this.getEntityManager().createQuery("select u from Usuario u where u.id = :id", Usuario.class);
//		Query q = this.getEntityManager().createQuery("select u from Usuario u where u.id = :id");
		q.setParameter("id", Integer.parseInt(intId));
		Usuario usuario = null;
		try {
			usuario = (Usuario) q.getSingleResult();
		} catch (NoResultException e) {
		}
		return usuario;
	}
	
}
