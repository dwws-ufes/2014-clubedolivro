package br.com.wancharle.clubedolivro.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.wancharle.clubedolivro.domain.Leitura;
import br.com.wancharle.clubedolivro.domain.Usuario;
import br.com.wancharle.clubedolivro.persistence.LeituraDAO;
import br.com.wancharle.clubedolivro.persistence.UsuarioDAO;


@Named
@ApplicationScoped
public class TopMais {

	@EJB
	private LeituraDAO leituraDAO;
	
	public List<Leitura> ultimasResenhas(int in, int out){
		return leituraDAO.findUtilmasResenhas(in,out);
	}
}
