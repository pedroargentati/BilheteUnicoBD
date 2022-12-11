package br.fiap.form;
import static javax.swing.JOptionPane.showInputDialog;

import java.sql.SQLException;

import br.fiap.dao.UsuarioDAO;

public class FormPrincipal {

	
	public void menuPrincipal() throws SQLException {
		String opcao;
		UsuarioDAO uDAO = new UsuarioDAO();
		
		do {
			opcao = showInputDialog("Digite sua senha ou CPF ou Sair");
			if(opcao.equalsIgnoreCase("admin")) {
				new FormAdmin().menuAdmin();
			} else if(uDAO.obterCPFPorChave(opcao)) {
				new FormUsuario().menuUsuario(opcao);
			} 
			
		} while(!opcao.equalsIgnoreCase("sair"));		
	}
	
}
