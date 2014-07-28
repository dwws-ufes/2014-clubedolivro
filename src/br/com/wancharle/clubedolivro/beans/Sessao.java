package br.com.wancharle.clubedolivro.beans;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.wancharle.clubedolivro.domain.Usuario;
import br.com.wancharle.clubedolivro.persistence.UsuarioDAO;

@Named
@SessionScoped
public class Sessao implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(AddUsuario.class.getCanonicalName());
		@EJB
		private UsuarioDAO usuarioDAO;
		
		private Usuario usuarioLogado;

		private String login;
		private String password;


		public String login(){
			usuarioLogado = usuarioDAO.findByLoginAndPassword(login, password);

		FacesContext context = FacesContext.getCurrentInstance();
			if (usuarioLogado == null){
				
				logger.log(Level.SEVERE, "Não foi possivel logar: Usuario ou senha não conferem!");
				context.addMessage("loginForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Não foi possível logar!", "Usuario ou Senha não conferem!"));
				password="";
				return null;
			}else{
				logger.log(Level.INFO, "Usuario " + usuarioLogado.getNome() + "logado com sucesso!");
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Login concluído",  usuarioLogado.getNome()+" seja bem vindo ao clube do livro!"));
                return "/index.faces";
			}
		}
		
		public Usuario getUsuarioLogado() {
			return usuarioLogado;
		}


		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
}
