package br.com.wancharle.clubedolivro.beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.wancharle.clubedolivro.domain.Usuario;
import br.com.wancharle.clubedolivro.persistence.UsuarioDAO;


@Named
@RequestScoped
public class AddUsuario {

	@EJB
	private UsuarioDAO usuarioDAO;
	
	private Usuario usuario = new Usuario();
	private String password2;
	
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public Usuario getUsuario(){
			return usuario;
	}
	public String add() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (usuario.getPassword().compareTo(password2) == 0){
			usuarioDAO.save(usuario);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Usuario cadastrado!","Usuario \"" + usuario.getNome() + "\" adicionado com sucesso!"));
			usuario = new Usuario(); // zera o usuario atual
		}else{
			context.addMessage("cadForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu um erro no cadastro!", "Senhas não conferem!"));
			usuario.setPassword("");
			password2="";
		}
		return null;
	}	
}
