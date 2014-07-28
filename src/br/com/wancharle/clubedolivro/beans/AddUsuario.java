package br.com.wancharle.clubedolivro.beans;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.wancharle.clubedolivro.domain.Usuario;
import br.com.wancharle.clubedolivro.persistence.UsuarioDAO;


@Named
@RequestScoped
public class AddUsuario {
	private static final Logger logger = Logger.getLogger(AddUsuario.class.getCanonicalName());

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
		// TODO: Checar unique de usuario
		FacesContext context = FacesContext.getCurrentInstance();
		if (usuario.getPassword().compareTo(password2) == 0){
			
			logger.log(Level.INFO, "Adicionando usuario: [nome= {0}; usuario = {1}; sobre = {2}; password = {3}]", new Object[] {usuario.getNome(), usuario.getUsuario(),
					usuario.getSobre(),usuario.getPassword()});
			usuarioDAO.save(usuario);
			context.addMessage("cadForm", new FacesMessage(FacesMessage.SEVERITY_INFO,"Usuario cadastrado!","Usuario \"" + usuario.getNome() + "\" adicionado com sucesso!"));
			usuario = new Usuario(); // zera o usuario atual
		}else{

			logger.log(Level.SEVERE, "Não foi possivel adicionar USUARIO: senhas não conferem");
			context.addMessage("cadForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu um erro no cadastro!", "Senhas não conferem!"));
			usuario.setPassword("");
			password2="";
		}
		return null;
		
	}	
}
