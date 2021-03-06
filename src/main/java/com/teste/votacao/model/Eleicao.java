package com.teste.votacao.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Eleicao extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// Nome da eleição
	@NotEmpty
	private String nome;

	// Data de inicio da eleição
	@Basic
	@NotNull
	private Date inicio;

	// Data final da eleição
	@Basic
	@NotNull
	private Date fim;

	// Getters e Setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	@Override
	public String toString() {
		return "Eleicao [nome=" + nome + ", inicio=" + inicio + ", fim=" + fim + "]";
	}
}
