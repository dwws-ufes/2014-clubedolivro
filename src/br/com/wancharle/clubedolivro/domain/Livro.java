package br.com.wancharle.clubedolivro.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Livro implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	private Long id;
	private String identificador;
	
	
	private String title;
	
	private String autor;
	private String autorKey;
	
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
}
