package br.fiap.main;

import java.sql.SQLException;

import br.fiap.form.FormPrincipal;

public class Main {
	public static void main(String[] args) throws SQLException {
		
		new FormPrincipal().menuPrincipal();
		
	}
}
