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
	private String 	status;
	private Integer data_aprovaca;

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
		if (this.anoMes_solicitacao < 6) {
			// retornar 129999 por default
			return this.anoMes_solicitacao = 129999;
		}
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getData_aprovaca() {
		return data_aprovaca;
	}

	public void setData_aprovaca(Integer data_aprovaca) {
		this.data_aprovaca = data_aprovaca;
	}

	@Override
	public String toString() {
		String str = 	"\nID Soliticação: " 				+ id_solicitacao 		 + 
						"\nCPF: " 							+ cpf 				 	 + 
						"\nAno/Mês solicitação: " 			+ anoMes_solicitacao 	 + 
						"\nTipo de Bilhete solicitado: " 	+ tipo_bilhete_alteracao + 
						"\nStatus: " 						+ status;
		return str;
	}

}
