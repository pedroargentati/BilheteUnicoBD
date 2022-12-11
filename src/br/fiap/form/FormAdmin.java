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
import br.fiap.modelo.Usuario;

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
				case 4:
					this.alterarNomeUsuario();
					break;
				case 5:
					this.excluirUsuario();
					break;
				default:
					break;
				}

			} catch (NumberFormatException e) {
				showMessageDialog(null, "A opção deve ser um número entre 1 e 5\n" + e);
			}
		} while (opcao != 6);

	}

	public void emitirBilhete() throws SQLException {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		BilheteDAO bilheteDao = new BilheteDAO();
		String nome, tipo;
		String[] opcao = { "Estudante", "Professor", "Normal" };

		String cpf = showInputDialog("Informe o CPF do usuário que deseja emitir o Bilhete: ");
		if (usuarioDAO.obterCPFPorChave(cpf)) {
			showMessageDialog(null, "O CPF " + cpf + " já está cadastrado.");
		
		} else {
			
			nome = showInputDialog("Informe o nome do usuário: ");
			tipo = (String) showInputDialog(null, "Tipo de Tarifa", "Tipo de Tarifa", 0, null, opcao, opcao[2]);

			double numero = gerarNumeroAleatorio();
			if (bilheteDao.obterBilhetePorChave(numero)) {
				showMessageDialog(null, "Número de bilhete já cadastrado. ");
			} 
			
			usuarioDAO.inserirUsuario(nome, cpf, tipo);
			bilheteDao.inserirBilhete(numero, cpf, DEFAULT_BALANCE);
			
			showMessageDialog(null, "Usuário criado\nBilhete único gerado! ");
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
			UsuarioDAO usuDAO = new UsuarioDAO();
			String cpf = showInputDialog("Informe o CPF que deseja consultar: ");

			BilheteUnico cpfConsulado = bilheteDao.obterBilhetePorCPF(cpf);
			Usuario usu = usuDAO.obterUsuarioPorCPF(cpf);
			
			if (cpfConsulado != null && usu != null) {
				showMessageDialog(null, "Nome: " + usu.getNome() + "\n" + cpfConsulado );
			} else {
				showMessageDialog(null, "O CPF " + cpf + " não foi encontrado em nossa base de dados.");
			}

		} catch (NumberFormatException e) {
			showMessageDialog(null, "A opção deve ser um número entre 1 e 4\n" + e);
		}
	}

	public void alterarNomeUsuario() {
		UsuarioDAO uDAO = new UsuarioDAO();
		String cpf = showInputDialog("Informe o CPF do usuário que deseja alterar o nome: ");
		Usuario usuPesquisa = uDAO.obterUsuarioPorCPF(cpf);
		
		if(uDAO.obterUsuarioPorCPF(cpf).getCpf() != null) {
			String novoNome = showInputDialog("Informe o novo nome que deseja inserir: ");
			uDAO.atualizarNomeUsuario(cpf, novoNome);
			showMessageDialog(null, "Nome atualizado com sucesso!\nNome antigo: " + usuPesquisa.getNome() + "\nNome novo: " + novoNome);
		} else {
			showMessageDialog(null, "Usuário com o CPF " + cpf + " não econtrado em nossa base de dados.");
		}
	}
	
	public void excluirUsuario() {
		UsuarioDAO usuDAO = new UsuarioDAO();
		Usuario usuarioPesquisa = null;
		
		String cpf = showInputDialog("Informe o CPF do usuário que deseja excluir: ");
		if (cpf != null && cpf.trim() != "") {
			usuarioPesquisa = usuDAO.obterUsuarioPorCPF(cpf);
		} else {
			showMessageDialog(null, "Insira um CPF válido para pesquisa.");
		}
			

		if (usuarioPesquisa != null) {
			String confirmacao = showInputDialog("Você tem certeza que deseja excluir o usuário " + usuarioPesquisa.getNome() + " ? (S/N)");
			if (confirmacao != null && confirmacao.trim() != "") {
				if (confirmacao.equalsIgnoreCase("S")) {
					usuDAO.excluirUsuarioPorCpf(cpf);
					showMessageDialog(null, "Usuário '" + usuarioPesquisa.getNome() + "' excluído com sucesso.");
				} else {
					showMessageDialog(null, "Tudo bem. Voltando ao menu principal.");
				}
			} else {
				showMessageDialog(null, "Insira um valor válido para pesquisa.");
			}
		} else {
			showMessageDialog(null, "Usuário com o CPF " + cpf + " não econtrado em nossa base de dados.");

		}
	}
	
	
	
	public double gerarNumeroAleatorio( ) {
		double numero = 0;
		
		Random random = new Random();
		numero = random.nextInt(100000);
		
		return numero;
	}
	
 	private String gerarMenuAdmin() {
		String menu = "Escolha uma operação:\n";
		menu += "1. Emitir Bilhete\n";
		menu += "2. Imprimir Bilhete\n";
		menu += "3. Consultar Bilhete\n";
		menu += "4. Alterar nome de Usuário\n";
		menu += "5. Excluir Usuário\n";
		menu += "6. Sair";
		return menu;

	}

}
