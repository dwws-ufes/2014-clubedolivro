package br.com.wancharle.clubedolivro.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;


@Named
@RequestScoped
public class Search implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(AddUsuario.class.getCanonicalName());

    private String texto;
    private Long totalEncontrado = (long) 0;
    private JSONObject json; 
    private JSONArray livrosOpenlibre; 

    public void buscaPorCategoria(String categoria){
        String url = "https://openlibrary.org/search.json?subject="+ categoria;
        try {
            String genreJson = IOUtils.toString(new URL(url));
            json = (JSONObject) JSONValue.parseWithException(genreJson);
        
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    public String busca(){
    	buscaPorCategoria("popular");
        return "/search.faces";
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

	public Long getTotalEncontrado() {
		return totalEncontrado;
	}

	public void setTotalEncontrado(Long totalEncontrado) {
		this.totalEncontrado = totalEncontrado;
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
		

}
