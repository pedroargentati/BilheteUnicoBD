package br.fiap.form;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.text.NumberFormat;

import br.fiap.dao.BilheteDAO;
import br.fiap.dao.UsuarioDAO;
import br.fiap.modelo.BilheteUnico;
import br.fiap.modelo.TipoUsuario;
import br.fiap.modelo.Usuario;

public class FormUsuario {

	public static final double NORMAL	 = 4.40;
	public static final double ESTUDANTE = 2.20;
	public static final double PROFESSOR = 2.10;
	
	public void menuUsuario(String cpf) {

		int opcao = 0;

		do {
			try {
				opcao = parseInt(showInputDialog(gerarMenuUsuario()));

				switch (opcao) {
				case 1:
					this.carregarBilhete(cpf);
					break;
				case 2:
					this.passarNaCatraca(cpf);
					break;
				case 3:
					this.consularSaldo(cpf);
				default:
					break;
				}

			} catch (NumberFormatException e) {
				showMessageDialog(null, "A opção deve ser um número entre 1 e 4\n" + e);
			}
		} while (opcao != 4);

	}

	private void carregarBilhete(String cpf) {
		BilheteDAO bDAO = new BilheteDAO();
		
		BilheteUnico bilheteAtu = bDAO.obterBilhetePorCPF(cpf);
		
		if(bilheteAtu != null) {
			double valoQueDesejaCarregar = parseDouble(showInputDialog("Informe o valor que deseja recarregar: "));
			
			bDAO.atualizarBilhete(bilheteAtu.getNumero(), bilheteAtu.getCpf(), bilheteAtu.getSaldo() + valoQueDesejaCarregar);

			valoQueDesejaCarregar += bilheteAtu.getSaldo() ;
			showMessageDialog(null, "O seu saldo foi atualizado com sucesso!\n Saldo atual: " + valoQueDesejaCarregar);
		} else {
			showMessageDialog(null, "O bilhete com CPF " + cpf + " nÃ£o foi encontrado.");
		}

	}
	
	public void passarNaCatraca(String cpf) {
		BilheteDAO bDAO = new BilheteDAO();
		BilheteUnico bilheteAtu = bDAO.obterBilhetePorCPF(cpf);
		
		UsuarioDAO usuDAO = new UsuarioDAO();
		Usuario usuAtu = usuDAO.obterUsuarioPorCPF(cpf);
		double valorAposPassarNaCatraca = 0;
		
		if(bilheteAtu != null) {
			
			if(bilheteAtu.getSaldo() >= NORMAL && TipoUsuario.NORMAL.getTipo().equalsIgnoreCase(usuAtu.getTipo())) {
				
				valorAposPassarNaCatraca = bilheteAtu.getSaldo() - TipoUsuario.NORMAL.getValorPassagem();
				bDAO.atualizarBilhete(bilheteAtu.getNumero(), cpf, valorAposPassarNaCatraca);
				showMessageDialog(null, "Usuário " + usuAtu.getNome() + " passou na catraca!\nTipo: " + usuAtu.getTipo() + "\nSaldo atual: " + NumberFormat.getCurrencyInstance().format(valorAposPassarNaCatraca));
			
			} else if(bilheteAtu.getSaldo() >= ESTUDANTE && TipoUsuario.ESTUDANTE.getTipo().equalsIgnoreCase(usuAtu.getTipo())){
				
				valorAposPassarNaCatraca = bilheteAtu.getSaldo() - TipoUsuario.ESTUDANTE.getValorPassagem();
				bDAO.atualizarBilhete(bilheteAtu.getNumero(), cpf, valorAposPassarNaCatraca);
				showMessageDialog(null, "Usuário " + usuAtu.getNome() + " passou na catraca!\nTipo: " + usuAtu.getTipo() + "\nSaldo atual: " + NumberFormat.getCurrencyInstance().format(valorAposPassarNaCatraca));
			
			} else if(bilheteAtu.getSaldo() >= PROFESSOR && TipoUsuario.PROFESSOR.getTipo().equalsIgnoreCase(usuAtu.getTipo())) {
				
				valorAposPassarNaCatraca = bilheteAtu.getSaldo() - TipoUsuario.PROFESSOR.getValorPassagem();
				bDAO.atualizarBilhete(bilheteAtu.getNumero(), cpf, valorAposPassarNaCatraca);
				showMessageDialog(null, "Usuário " + usuAtu.getNome() + " passou na catraca!\nTipo: " + usuAtu.getTipo() + "\nSaldo atual: " + NumberFormat.getCurrencyInstance().format(valorAposPassarNaCatraca));
			
			} else {
				showMessageDialog(null, "Sem saldo suficiente!");
			}
		}
	}
	
	public void consularSaldo(String cpf) {
		BilheteDAO bDAO = new BilheteDAO();
		BilheteUnico bilheteAtu = bDAO.obterBilhetePorCPF(cpf);
		
		if(bilheteAtu != null) 
			showMessageDialog(null, "Seu saldo é de : " + NumberFormat.getCurrencyInstance().format(bilheteAtu.getSaldo()));
		
	}

	private String gerarMenuUsuario() {
		String menu = "Escolha uma operação:\n";
		menu += "1. Carregar Bilhete\n";
		menu += "2. Passar na Catraca\n";
		menu += "3. Consultar Saldo\n";
		menu += "4. Sair";
		return menu;
	}

}
