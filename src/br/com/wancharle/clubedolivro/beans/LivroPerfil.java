package br.com.wancharle.clubedolivro.beans;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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

import br.com.wancharle.clubedolivro.domain.Livro;
import br.com.wancharle.clubedolivro.persistence.LivroDAO;


@Named
@RequestScoped
public class LivroPerfil {

	private static final Logger logger = Logger.getLogger(LivroPerfil.class.getCanonicalName());

	@EJB
	private LivroDAO livroDAO;
	private Livro livro = new Livro();
	
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

        livro.setIdentificador(key);
        livro.setFonteGutenberg(false);
        
        try {
			String url = "https://openlibrary.org/books/"+ URLEncoder.encode(key,"UTF-8")+".json";
			String rawJson = IOUtils.toString(new URL(url));
			json = (JSONObject) JSONValue.parseWithException(rawJson);

            livro.setTitle((String)json.get("title"));
            
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
                livro.setAutor( getAutorName(autorKey));
                livro.setAutorKey(autorKey);
            }else{
            	livro.setAutor(autor_do_work); // caso não ache o autor
                livro.setAutorKey(null);
            }
            
            livro.setIssued((String)json.get("publish_date"));
            livro.setPublisher((String)((JSONArray)json.get("publishers")).get(0));
            livro.setNumPaginas((Long) json.get("number_of_pages"));
          
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
    }
	public String view(Livro livro_escolhido){
		Livro livro_existente = livroDAO.findByIdentificador(livro_escolhido.getIdentificador());
		if (livro_existente != null){
			livro = livro_existente;
		}else{
			livro = livro_escolhido;
		}
		return "/livro.faces";
	}
	public String view(String key, String autor){
		Livro livro_existente = livroDAO.findByIdentificador(key);
		if (livro_existente != null){
			livro=livro_existente;
		}else{
			livro.setIdentificador(key);
			getLivroJson(key,autor);
		}
		return "/livro.faces";
	}
	public String add() {
		return null;
	}	

	public Livro getLivro() {
		return livro;
	}
}
