package br.com.wancharle.clubedolivro.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
