package br.com.wancharle.clubedolivro.beans;


import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.primefaces.json.JSONException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

import br.com.wancharle.clubedolivro.domain.Leitura;
import br.com.wancharle.clubedolivro.domain.Livro;
import br.com.wancharle.clubedolivro.domain.Usuario;
import br.com.wancharle.clubedolivro.persistence.LeituraDAO;
import br.com.wancharle.clubedolivro.persistence.LivroDAO;


@ConversationScoped
@Named
public class LivroPerfil implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LivroPerfil.class.getCanonicalName());
	@Inject
	private Conversation conversation;
	
	@EJB
	private LivroDAO livroDAO;
	
	@EJB
	private LeituraDAO leituraDAO;
	
	@Inject
	private Sessao sessao;
	

	private Livro livro = new Livro();
	private Leitura leitura = new Leitura();
	
	public String begin() {
		if (conversation.isTransient()){
			conversation.begin();
	}else{
			conversation.end();
			conversation.begin();
		}
		return "/livro.xhtml";
	}

	private String getAutorName(String key){
		try {
		String 	url = "https://openlibrary.org"+key+".json";
			String genreJson = IOUtils.toString(new URL(url));
			JSONObject json = (JSONObject) JSONValue.parseWithException(genreJson);
			return (String) json.get("name");
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}	
	private void getLivroJson(String key,String autor_do_work){
        JSONObject json;

        getLivro().setIdentificador(key);
        getLivro().setFonteGutenberg(false);
        
        try {
			String url = "https://openlibrary.org/books/"+ URLEncoder.encode(key,"UTF-8")+".json";
			String rawJson = IOUtils.toString(new URL(url));
			json = (JSONObject) JSONValue.parseWithException(rawJson);

            getLivro().setTitle((String)json.get("title"));
            
            JSONObject autor;
			JSONArray autores = (JSONArray) json.get("authors");
            if (autores != null)
				autor = (JSONObject) autores.get(0);
            else{
                autor = (JSONObject) json.get("author");
			}
            if (autor != null){
                String autorKey;
                try{
                autorKey= (String) autor.get("key");
                }catch(Exception exception){
                    autorKey= (String) ((JSONObject)autor.get("author")).get("key");
                }
                getLivro().setAutor( getAutorName(autorKey));
                getLivro().setAutorKey(autorKey);
            }else{
            	getLivro().setAutor(autor_do_work); // caso não ache o autor
                getLivro().setAutorKey(null);
            }
            
            getLivro().setIssued((String)json.get("publish_date"));
            getLivro().setPublisher((String)((JSONArray)json.get("publishers")).get(0));
            getLivro().setNumPaginas((Long) json.get("number_of_pages"));
          
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
    }
	private void loadLeitura(){
		Usuario user = sessao.getUsuarioLogado();
		if (user !=null && livro!=null){
			Leitura leitura_existente = leituraDAO.findByLivroUser(livro,user);
			if (leitura_existente!= null)
			{
				leitura = leitura_existente;
			}
		}
	}
	public String view(Livro livro_escolhido){
		Livro livro_existente = livroDAO.findByIdentificador(livro_escolhido.getIdentificador());
		if (livro_existente != null){
			setLivro(livro_existente);
			loadLeitura();
		}else{
			setLivro(livro_escolhido);
			livroDAO.save(getLivro());
		}
		return begin();
	}
	public String view(String key, String autor){
		Livro livro_existente = livroDAO.findByIdentificador(key);
		if (livro_existente != null){
			setLivro(livro_existente);
		loadLeitura();	
		}else{
			getLivro().setIdentificador(key);
			getLivroJson(key,autor);
			livroDAO.save(getLivro());
		}
		return begin();
	}
	
	public String salvarNaEstante(){
		Usuario user = sessao.getUsuarioLogado();
		if (user == null){
			FacesContext context = FacesContext.getCurrentInstance();
		    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Requer login", "É necessário está logado para adicionar uma resenha!"));

			conversation.end();
			return "/login.faces";
		}else{
			leitura.setUsuario(user);
			leitura.setLivro(livro);
			leituraDAO.save(leitura);
			conversation.end();
            return "/perfil.faces";
		}
	}
	
	public String add() {
		return null;
	}	

	public Livro getLivro() {
		return livro;
	}
	public Leitura getLeitura() {
		return leitura;
	}
	public void setLeitura(Leitura leitura) {
		this.leitura = leitura;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
}
