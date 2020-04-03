package com.teste.votacao.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class Eleitor extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String nome;

	@NotEmpty
	@Length(min = 14, max = 14)
	private String cdf;

	@Length(min = 19, max = 19)
	private String protocolo;

	@NotNull
	@ManyToOne
	private Eleicao eleicao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCdf() {
		return cdf;
	}

	public void setCdf(String cdf) {
		this.cdf = cdf;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Eleicao getEleicao() {
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao) {
		this.eleicao = eleicao;
	}

}
