package br.com.wancharle.clubedolivro.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Leitura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	private Long id;
	
	@ManyToOne
	private Livro livro;

	@ManyToOne
	private Usuario usuario;
	
	//Constants
	public static final char VOU_LER = 'V';
	public static final char ESTOU_LENDO = 'E';
	public static final char JA_LI = 'J';
	public static final char ABANDONEI = 'A';
	
	private char situacao;
	
	private Boolean favorita;	
	
	@Column(columnDefinition="TEXT")
	private String resenha;
	
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public char getSituacao() {
		return situacao;
	}

	public void setSituacao(char situacao) {
		this.situacao = situacao;
	}

	public Boolean getFavorita() {
		return favorita;
	}

	public void setFavorita(Boolean favorita) {
		this.favorita = favorita;
	}

	public String getResenha() {
		return resenha;
	}

	public void setResenha(String resenha) {
		this.resenha = resenha;
	}
	
	
	
}
