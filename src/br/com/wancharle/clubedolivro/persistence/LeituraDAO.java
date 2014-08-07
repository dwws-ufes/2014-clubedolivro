package br.com.wancharle.clubedolivro.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.wancharle.clubedolivro.domain.Leitura;
import br.com.wancharle.clubedolivro.domain.Livro;
import br.com.wancharle.clubedolivro.domain.Usuario;
import br.com.wancharle.clubedolivro.utils.BaseJPADAO;

@Stateless
public class LeituraDAO extends BaseJPADAO<Leitura>{
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected Class<Leitura> getDomainClass() {
		return Leitura.class;
	}
	
	public Leitura findByLivroUser(Livro livro, Usuario user){
		TypedQuery<Leitura> query = em.createQuery(
				"SELECT l FROM Leitura l WHERE l.livro.id= :livro_id and l.usuario.id= :user_id",
				Leitura.class);
		query.setParameter("livro_id",livro.getId());		
		query.setParameter("user_id",user.getId());		
		List<Leitura> results = query.getResultList();
		if (results.isEmpty())
			return null;
		else
			return results.get(0);
	}
	
	public List<Leitura> findByUserSituacao(Usuario user, String situacao){
		TypedQuery<Leitura> query = em.createQuery(
				"SELECT l FROM Leitura l JOIN Fetch l.livro WHERE l.situacao= :situacao and l.usuario.id= :user_id",
				Leitura.class);
		query.setParameter("situacao",situacao);		
		query.setParameter("user_id",user.getId());		
		return query.getResultList();
	}

	public List<Leitura> findFavoritosByUser(Usuario user){
		TypedQuery<Leitura> query = em.createQuery(
				"SELECT l FROM Leitura l JOIN Fetch l.livro WHERE l.favorita = true and l.usuario.id= :user_id",
				Leitura.class);
		query.setParameter("user_id",user.getId());		
		return query.getResultList();
	}
    public List<Leitura> findUtilmasResenhas(int inf, int sup ){
		TypedQuery<Leitura> query = em.createQuery(
				"SELECT l FROM Leitura l JOIN Fetch l.livro WHERE l.resenha like '__%' ORDER BY l.id DESC",
				Leitura.class);
		query.setFirstResult(inf);		
		query.setMaxResults(sup);		
		return query.getResultList();
	}
 
}
