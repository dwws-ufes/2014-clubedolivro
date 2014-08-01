package br.com.wancharle.clubedolivro.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import br.com.wancharle.clubedolivro.domain.Livro;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;


@Named
@SessionScoped
public class Search implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String texto;
    private String categoria;

    private JSONObject json; 
    private JSONArray livrosOpenlibre; 

    private List<Livro> livrosGutenberg;
    private void clearSessao(){
    	setJson(null);
    	setLivrosGutenberg(null);
    }
    
    public String busca() {
    	categoria = null;
    	clearSessao();
    	if (texto.length()>2){
    		buscaSparqlGutenberg(texto);
            try {
                String url = "https://openlibrary.org/search.json?has_fulltext=true&limit=50&q="+ URLEncoder.encode(texto,"UTF-8");
                String genreJson = IOUtils.toString(new URL(url));
                json = (JSONObject) JSONValue.parseWithException(genreJson);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    	return "/search.faces";
    }
    
    public String buscaPorCategoria(String categoria){
    	clearSessao();
    	texto = null;
    	if (categoria.length()>2){
            setCategoria(categoria);
            String url = "https://openlibrary.org/search.json?has_fulltext=true&limit=50&subject="+ categoria;
            try {
                String genreJson = IOUtils.toString(new URL(url));
                json = (JSONObject) JSONValue.parseWithException(genreJson);
            
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
         }
    	return "/search.faces";
    }
    public String buscaSparqlGutenberg(String texto){
    	String queryString =
    			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
    			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
    			"PREFIX dc: <http://purl.org/dc/elements/1.1/>" + 
    			"PREFIX dcterms: <http://purl.org/dc/terms/>" +
    			"PREFIX dcmitype: <http://purl.org/dc/dcmitype/>" +
    			"PREFIX cc: <http://web.resource.org/cc/>" +
    			"PREFIX pgterms: <http://www.gutenberg.org/rdfterms/>" +
    			"PREFIX dcmitype: <http://purl.org/dc/dcmitype/>" +
				"SELECT DISTINCT ?book ?title ?author ?publisher " +
				"WHERE {" +
				" ?book dc:title ?title  ;" +
				" dc:creator ?author ;" +
				" dc:publisher ?publisher . " +
				" FILTER (regex(str(?title), \"%s\",\"i\") || regex(str(?author),\"%s\",\"i\"))"
				+ "} LIMIT 50" ;
    	Query query = QueryFactory.create(String.format(queryString,texto,texto));
    	QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://clubedolivro.wancharle.com.br:3030/livros/query",query);
    	ResultSet results = queryExecution.execSelect();
    	setLivrosGutenberg(new ArrayList<Livro>());
    	QuerySolution sol;
    	
    	while( results.hasNext()){
    		Livro livro = new Livro();
    		sol = results.nextSolution();
    		livro.setIdentificador( sol.get("book").asResource().getURI());
    		livro.setTitle( sol.get("title").asLiteral().getString());
       		livro.setAutor( sol.get("author").asLiteral().getString());
       		livro.setPublisher(sol.get("publisher").toString());	
    		livro.setIssued("data desconhecida");

    		livro.setFonteGutenberg(true);
    		livro.setNumPaginas(0L);
       		livro.setAutorKey(null);
       		
    		getLivrosGutenberg().add(livro);
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

	public List<Livro> getLivrosGutenberg() {
		return livrosGutenberg;
	}

	public void setLivrosGutenberg(List<Livro> livros) {
		this.livrosGutenberg = livros;
	}
		

}
