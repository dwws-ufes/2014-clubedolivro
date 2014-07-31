package br.com.wancharle.clubedolivro.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.wancharle.clubedolivro.domain.Livro;
import br.com.wancharle.clubedolivro.utils.BaseJPADAO;

@Stateless
public class LivroDAO extends BaseJPADAO<Livro>{
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<Livro> getDomainClass() {
		return Livro.class;
	}
	
	public Livro findByIdentificador(String login){
		TypedQuery<Livro> query = em.createQuery(
				"SELECT l FROM Livro l WHERE l.identificador = :identificador",
				Livro.class);
		query.setParameter("identificador",login);		
		List<Livro> results = query.getResultList();
		if (results.isEmpty())
			return null;
		else
			return results.get(0);
	}

}
