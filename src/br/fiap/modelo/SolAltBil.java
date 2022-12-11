package br.fiap.modelo;

public class SolAltBil {
	
	/*
	 * Solicitação de alteração de Tipo de Bilhete.
	 * 
	 * */

	private Integer id_solicitacao;
	private String 	cpf;
	private Integer anoMes_solicitacao;
	private String 	tipo_bilhete_alteracao;

	public Integer getId_solicitacao() {
		return id_solicitacao;
	}

	public void setId_solicitacao(Integer id_solicitacao) {
		this.id_solicitacao = id_solicitacao;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getAnoMes_solicitacao() {
		return anoMes_solicitacao;
	}

	public void setAnoMes_solicitacao(Integer anoMes_solicitacao) {
		this.anoMes_solicitacao = anoMes_solicitacao;
	}

	public String getTipo_bilhete_alteracao() {
		return tipo_bilhete_alteracao;
	}

	public void setTipo_bilhete_alteracao(String tipo_bilhete_alteracao) {
		this.tipo_bilhete_alteracao = tipo_bilhete_alteracao;
	}

}
