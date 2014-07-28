package br.com.wancharle.clubedolivro.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.wancharle.clubedolivro.domain.Usuario;
import br.com.wancharle.clubedolivro.utils.BaseJPADAO;

@Stateless
public class UsuarioDAO extends BaseJPADAO<Usuario>{
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<Usuario> getDomainClass() {
		return Usuario.class;
	}
	
	public Usuario findByLoginAndPassword(String login, String password){
		TypedQuery<Usuario> query = em.createQuery(
				"SELECT u FROM Usuario u WHERE u.usuario = :login and u.password = :password",
				Usuario.class);
		query.setParameter("login",login);		
		query.setParameter("password",password);		
		List<Usuario> results = query.getResultList();
		if (results.isEmpty())
			return null;
		else
			return results.get(0);
	}

}
