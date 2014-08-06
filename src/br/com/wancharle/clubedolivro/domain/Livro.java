package br.com.wancharle.clubedolivro.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Livro implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="livro" )
	private Set<Leitura> leituras;
		
	private String identificador;
	private String title;
	private String autor;
	private String autorKey;
	private Boolean fonteGutenberg;
	private String publisher;
	private String issued;
	private Long numPaginas;
	

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getAutorKey() {
		return autorKey;
	}

	public void setAutorKey(String audor_rdf) {
		this.autorKey = audor_rdf;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIssued() {
		return issued;
	}

	public void setIssued(String issued) {
		this.issued = issued;
	}

	public Long getNumPaginas() {
		return numPaginas;
	}

	public void setNumPaginas(Long numPaginas) {
		this.numPaginas = numPaginas;
	}

		
	public Long getId() {
		return id;
	}

	public Boolean getFonteGutenberg() {
		return fonteGutenberg;
	}

	public void setFonteGutenberg(Boolean fonteGutenberg) {
		this.fonteGutenberg = fonteGutenberg;
	}

	public Set<Leitura> getLeituras() {
		return leituras;
	}

	public void setLeituras(Set<Leitura> leituras) {
		this.leituras = leituras;
	}

	public String getGutenbergId(){
		// exemplo identificador gutenberg: http://www.gutenberg.org/feeds/catalog.rdf#etext33056
		String parts[] = getIdentificador().split("#etext");
		return parts[1];
	}
	public String getHTMLUrl(){
		// exemplo url html http://www.gutenberg.org/files/14600/14600-h/14600-h.htm
		String id = getGutenbergId();
        return String.format("http://www.gutenberg.org/files/%s/%s-h/%s-h.html",id,id,id);
	}
	public String getEPUBUrl(){
		String id = getGutenbergId();
        return String.format("http://www.gutenberg.org/books/%s.epub",id);
	}
	public String getURLCapa(String path){
		if (fonteGutenberg){
			return path+"/resources/img/gutenberg.jpg";
		}
		else{
			return "http://covers.openlibrary.org/b/olid/"+identificador+"-M.jpg";
		}
	}

	public void setId(Long id) {
		this.id = id;
	}
}
