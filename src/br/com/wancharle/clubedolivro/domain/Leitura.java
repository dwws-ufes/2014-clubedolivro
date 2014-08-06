package br.com.wancharle.clubedolivro.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"livro_id" , "usuario_id"})})
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
	public static final String VOU_LER = "V";
	public static final String ESTOU_LENDO ="E";
	public static final String JA_LI = "J";
	public static final String  ABANDONEI = "A";
	
	private String situacao = VOU_LER;
	
	private Boolean favorita;	
	
	@Column(columnDefinition="TEXT")
	private String resenha;
	
	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getResenhaCurta(){
		return resenha.substring(0,Math.min(120, resenha.length()));
	}
	
	
}
