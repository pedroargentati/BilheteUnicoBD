package br.fiap.form;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.fiap.dao.BilheteDAO;
import br.fiap.dao.SolAltBilDAO;
import br.fiap.dao.UsuarioDAO;
import br.fiap.modelo.BilheteUnico;
import br.fiap.modelo.SolAltBil;
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
					break;
				case 4:
					this.alterarTipoBilhte(cpf);
					break;
				case 5:
					this.solicitarAlteracaoTipoBilhete(cpf);
					break;
				default:
					break;
				}

			} catch (NumberFormatException e) {
				showMessageDialog(null, "A opção deve ser um número entre 1 e 6\n" + e);
			}
		} while (opcao != 6);

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
	
	public void alterarTipoBilhte(String cpf) {
		BilheteDAO bilheteDAO = new BilheteDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		String[] opcao = { "Estudante", "Professor", "Normal" };
		
		BilheteUnico bilhete = null;
		Usuario usuario = null;

		if (cpf != null && cpf.trim() != "") {
			bilhete = bilheteDAO.obterBilhetePorCPF(cpf);
			usuario = usuarioDAO.obterUsuarioPorCPF(cpf);
		} else {
			showMessageDialog(null, "Insira um CPF válido para pesquisa.");
		}

		if (bilhete != null && usuario != null) {
			String tipo = (String) showInputDialog(null, "O seu tipo de bilhete atual é: " + usuario.getTipo() + "\nDeseja alterar para qual tipo?", "Tipo de bilhete" , 0, null, opcao, opcao[2]);
			if(tipo != null && tipo.trim() != "") {
				usuarioDAO.atualizarTipoBilhete(cpf, tipo);
				showMessageDialog(null, "Bilhete atualizado com sucesso!\nTipo antigo: " + usuario.getTipo() + "\nNovo Tipo: " + tipo);
			} else {
				showMessageDialog(null, "Insira um tipo válido.");
			}
		}
	}
	
	public void solicitarAlteracaoTipoBilhete(String cpf) {
		BilheteDAO bilheteDAO = new BilheteDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		SolAltBilDAO solAltBilDAO = new SolAltBilDAO();
		
		String[] opcao = { "Estudante", "Professor", "Normal" };
		
		BilheteUnico bilhete = null;
		Usuario usuario = null;

		if (cpf != null && cpf.trim() != "") {
			bilhete = bilheteDAO.obterBilhetePorCPF(cpf);
			usuario = usuarioDAO.obterUsuarioPorCPF(cpf);
		} else {
			showMessageDialog(null, "Insira um CPF válido para pesquisa.");
		}

		if (bilhete != null && usuario != null) {
			String tipo = (String) showInputDialog(null, "O seu tipo de bilhete atual é: " + usuario.getTipo() + "\nDeseja alterar para qual tipo?", "Tipo de bilhete" , 0, null, opcao, opcao[2]);
			if (tipo != null && tipo.trim() != "") {
				SolAltBil solicitacaoAlteracao = solAltBilDAO.obterSolAltBilPorCpf(cpf);
				if (solicitacaoAlteracao != null) {
					String dataSol = String.valueOf(solicitacaoAlteracao.getAnoMes_solicitacao());
					showMessageDialog(null, "Você já tem uma solicitação em aberto !\nFavor aguardar.\n"
											+ "\nMês/Ano Solicitação: " + String.valueOf( dataSol.substring(0, 2) + "/" + dataSol.substring(2, 6) )
											+ "\nTipo alteração solicitada: " + solicitacaoAlteracao.getTipo_bilhete_alteracao());
				} else {
					solicitacaoAlteracao = new SolAltBil();

					LocalDateTime dataSolicitacao = LocalDateTime.now();
					DateTimeFormatter formatDateSol = DateTimeFormatter.ofPattern("MMyyyy");

					solicitacaoAlteracao.setId_solicitacao(3);
					solicitacaoAlteracao.setCpf(cpf);
					solicitacaoAlteracao.setAnoMes_solicitacao(Integer.valueOf(dataSolicitacao.format(formatDateSol)));
					solicitacaoAlteracao.setTipo_bilhete_alteracao(tipo);

					if (solicitacaoAlteracao != null) {
						// solicitar alteração do tipo do bilhete
						solAltBilDAO.inserirSolAltBil(solicitacaoAlteracao);
						showMessageDialog(null, "Sua solicitação foi enviada com sucesso!!\nTipo antigo: " + usuario.getTipo() + "\nNovo Tipo: " + tipo);
					}
				}
				
			} else {
				showMessageDialog(null, "Insira um tipo válido.");
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
		menu += "4. Alterar tipo Bilhete\n";
		menu += "5. Solicitacar Alteração Tipo Bilhete\n";
		menu += "6. Sair";
		return menu;
	}

}
