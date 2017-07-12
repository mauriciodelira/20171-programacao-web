package br.edu.ifpb.memoriam.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.edu.ifpb.memoriam.entity.Contato;
import br.edu.ifpb.memoriam.entity.Perfil;
import br.edu.ifpb.memoriam.entity.Usuario;

public class ContatoDAO extends GenericDAO<Contato, Integer> {
	
	public ContatoDAO() {
		this(PersistenceUtil.getCurrentEntityManager());
	}

	public ContatoDAO(EntityManager em) {
		super(em);
	}
	
	public Contato findByID(String id) {
		Query q = this.getEntityManager().createQuery("select c from Contato c where c.id = :id");
		q.setParameter("id", id);
		Contato cont = null;
		try {
			cont = (Contato) q.getSingleResult();
		} catch (NoResultException e) {
		}
		return cont;
	}

	public List<Contato> pesquisarPorParte(String part, Usuario logado){
		Query q;
		if(logado.getPerfil().equals(Perfil.BASIC)){
			q = this.getEntityManager().createQuery(
				"select c from Contato c where c.usuario = :user and lower(c.nome) like lower(:part) or lower(c.fone) like lower(:part)", Contato.class);
		}else{
			q = this.getEntityManager().createQuery(
					"select c from Contato c where lower(c.nome) like lower(:part) or lower(c.fone) like lower(:part)", Contato.class);
		}
		q.setParameter("part", "%"+part+"%");
		q.setParameter("user", logado);
		return q.getResultList();
	}
	
	public List<Contato> findAllFromUser(Usuario user) {
		Query q = this.getEntityManager().createQuery("select c from Contato c where c.usuario = :user");
		q.setParameter("user", user);
		return q.getResultList();
	}

	
}
