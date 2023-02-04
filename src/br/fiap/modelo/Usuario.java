package br.fiap.modelo;

public class Usuario {
	private String nome;
	private String cpf;
	private String tipo;
	
	private Double saldo;
	
	public Usuario() {}
	
	public Usuario(String nome, String cpf, String tipo) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	
	public String toString() {
		return "\n\n Nome: "  + nome + 
				"\nCPF: " 	  + cpf  + 
				"\nTipo: "	  + tipo + 
				"\nSaldo: R$" + saldo;
	}
}
