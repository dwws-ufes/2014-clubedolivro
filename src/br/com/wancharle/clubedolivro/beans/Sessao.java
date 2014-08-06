package br.com.wancharle.clubedolivro.beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.wancharle.clubedolivro.domain.Leitura;
import br.com.wancharle.clubedolivro.domain.Usuario;
import br.com.wancharle.clubedolivro.persistence.LeituraDAO;
import br.com.wancharle.clubedolivro.persistence.UsuarioDAO;

@Named
@SessionScoped
public class Sessao implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @EJB
    private UsuarioDAO usuarioDAO;
    
    private Usuario usuarioLogado;

    private String login;
    private String password;

	@EJB
	private LeituraDAO leituraDAO;
	private List<Leitura> leituras;
	

    public String logout(){
            usuarioLogado = null;
            return "/index.faces?faces-redirect=true";
        
    }
    
    public String login(){
        usuarioLogado = usuarioDAO.findByLoginAndPassword(login, password);
        FacesContext context = FacesContext.getCurrentInstance();
        if (usuarioLogado == null){
            context.addMessage("loginForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Não foi possível logar!", "Usuario ou Senha não conferem!"));
            password="";
            return null;
        }else{
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

 	public void loadLeituras(String situacao){
		setLeituras(leituraDAO.findByUserSituacao(getUsuarioLogado(),situacao));	
	}
 	
	public void loadLeiturasFavoritas(){
		setLeituras(leituraDAO.findFavoritosByUser(getUsuarioLogado()));	
	}

	public List<Leitura> getLeituras() {
		return leituras;
	}

	public void setLeituras(List<Leitura> leituras) {
		this.leituras = leituras;
	}	   
}
