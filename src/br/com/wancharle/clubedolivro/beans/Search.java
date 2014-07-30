package br.com.wancharle.clubedolivro.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;


@Named
@SessionScoped
public class Search implements Serializable {
	private static final long serialVersionUID = 1L;
	

    private String texto;
    private String categoria;

    private JSONObject json; 
    private JSONArray livrosOpenlibre; 

    public String busca() {
    	if (texto.length()>2){
        try {
        	String url = "https://openlibrary.org/search.json?limit=50&q="+ URLEncoder.encode(texto,"UTF-8");
            String genreJson = IOUtils.toString(new URL(url));
            json = (JSONObject) JSONValue.parseWithException(genreJson);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        }else{
        	
        	texto = "";
        }
    	return "/search.faces";
    }
 
    public String buscaPorCategoria(String categoria){
    	if (categoria.length()>2){
            setCategoria(categoria);
            String url = "https://openlibrary.org/search.json?limit=50&subject="+ categoria;
            try {
                String genreJson = IOUtils.toString(new URL(url));
                json = (JSONObject) JSONValue.parseWithException(genreJson);
            
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
         }
    	return "/search.faces";
    }
    
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }



	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public JSONArray getLivrosOpenlibre() {
		return livrosOpenlibre;
	}

	public void setLivrosOpenlibre(JSONArray livrosOpenlibre) {
		this.livrosOpenlibre = livrosOpenlibre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
		

}
