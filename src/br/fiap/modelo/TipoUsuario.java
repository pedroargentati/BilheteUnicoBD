package br.fiap.modelo;

public enum TipoUsuario {
	
	ESTUDANTE(2.20, "Estudante"),
	PROFESSOR(2.10, "Professor"),
	NORMAL	 (4.40, "Normal");

	private double valorPassagem;
	private String tipo;
		
	private TipoUsuario(double valorPassagem, String tipo) {
		this.valorPassagem = valorPassagem;
		this.tipo = tipo;
	}

	public double getValorPassagem() {
		return valorPassagem;
	}

	public void setValorPassagem(double valorPassagem) {
		this.valorPassagem = valorPassagem;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}