package br.fiap.modelo;

public class BilheteUnico {
	private int numero;
	private String cpf;
	private double saldo;
	public static final double valorPassagem = 4.4;
	
	public BilheteUnico() {	}
	
	public BilheteUnico(String cpf) {
		super();
		this.cpf = cpf;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getValorPassagem() {
		return valorPassagem;
	}	
	
	@Override
	public String toString() {
		return "\n\n Número: " + numero + "\n CPF: " + cpf + "\nSaldo: R$" + saldo;
	}
}
