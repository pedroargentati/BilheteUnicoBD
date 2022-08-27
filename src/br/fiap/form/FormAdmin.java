package br.fiap.form;

import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import br.fiap.dao.BilheteDAO;
import br.fiap.dao.UsuarioDAO;
import br.fiap.modelo.BilheteUnico;

public class FormAdmin {
	
	private static final Integer DEFAULT_BALANCE = 0;
	
	public void menuAdmin() throws SQLException {
		int opcao = 0;

		do {
			try {
				opcao = parseInt(showInputDialog(gerarMenuAdmin()));

				switch (opcao) {
				case 1:
					this.emitirBilhete();
					break;
				case 2: 
					this.imprimirBilhete();
					break;
				case 3:
					this.consularBilhete();
					break;
				default:
					break;
				}

			} catch (NumberFormatException e) {
				showMessageDialog(null, "A op��o deve ser um n�mero entre 1 e 4\n" + e);
			}
		} while (opcao != 4);

	}

	public void emitirBilhete() throws SQLException {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		BilheteDAO bilheteDao = new BilheteDAO();
		String nome, tipo;
		String[] opcao = { "Estudante", "Professor", "Normal" };

		String cpf = showInputDialog("Informe o CPF do usu�rio que deseja emitir o Bilhete: ");
		if (usuarioDAO.obterCPFPorChave(cpf)) {
			showMessageDialog(null, "O CPF " + cpf + " j� est� cadastrado.");
		
		} else {
			
			nome = showInputDialog("Informe o nome do usu�rio: ");
			tipo = (String) showInputDialog(null, "Tipo de Tarifa", "Tipo de Tarifa", 0, null, opcao, opcao[2]);

			double numero = gerarNumeroAleatorio();
			if (bilheteDao.obterBilhetePorChave(numero)) {
				showMessageDialog(null, "N�mero de bilhete j� cadastrado. ");
			} 
			
			usuarioDAO.inserirUsuario(nome, cpf, tipo);
			bilheteDao.inserirBilhete(numero, cpf, DEFAULT_BALANCE);
			
			showMessageDialog(null, "Usu�rio criado\nBilhete �nico gerado! ");
		}

	}
	
	public void imprimirBilhete() throws SQLException {
		BilheteDAO bilheteDao = new BilheteDAO();
		
		List<BilheteUnico> listBilheteUnico = bilheteDao.obterListBilhete();
		showMessageDialog(null, listBilheteUnico);
	}
	
	public void consularBilhete() {
		
		try {
			BilheteDAO bilheteDao = new BilheteDAO();
			String cpf = showInputDialog("Informe o CPF que deseja consultar: ");

			BilheteUnico cpfConsulado = bilheteDao.obterBilhetePorCPF(cpf);

			if (cpfConsulado != null) {
				showMessageDialog(null, cpfConsulado);
			} else {
				showMessageDialog(null, "O CPF " + cpf + " n�oo foi encontrado em nossa base de dados.");
			}

		} catch (NumberFormatException e) {
			showMessageDialog(null, "A op��o deve ser um n�mero entre 1 e 4\n" + e);
		}

	}
	
	public double gerarNumeroAleatorio( ) {
		double numero = 0;
		
		Random random = new Random();
		numero = random.nextInt(100000);
		
		return numero;
	}
	
	
 	private String gerarMenuAdmin() {
		String menu = "Escolha uma opera��o:\n";
		menu += "1. Emitir Bilhete\n";
		menu += "2. Imprimir Bilhete\n";
		menu += "3. Consultar Bilhete\n";
		menu += "4. Sair";
		return menu;

	}

}
