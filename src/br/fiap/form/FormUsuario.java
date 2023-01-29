package br.fiap.form;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import br.fiap.dao.BilheteDAO;
import br.fiap.dao.SolAltBilDAO;
import br.fiap.dao.UsuarioDAO;
import br.fiap.modelo.BilheteUnico;
import br.fiap.modelo.SolAltBil;
import br.fiap.modelo.TipoUsuario;
import br.fiap.modelo.Usuario;
import br.fiap.utils.Utils;

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
				case 6: 
					this.consultarSolAltBilUsu(cpf);
					break;
				case 7:
					this.consultarPerfilUsuario(cpf);
					break;
				case 8:
					this.transferirValor(cpf);
					break;
				default:
					break;
				}

			} catch (NumberFormatException e) {
				showMessageDialog(null, "A opção deve ser um número entre 1 e 9\n" + e);
			}
		} while (opcao != 9);

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
			showMessageDialog(null, "O bilhete com CPF " + cpf + " não foi encontrado.");
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
				if (solicitacaoAlteracao != null && ( solicitacaoAlteracao.getStatus() != null && solicitacaoAlteracao.getStatus().equalsIgnoreCase("P"))) {
					String dataSol = String.valueOf(solicitacaoAlteracao.getAnoMes_solicitacao());
					showMessageDialog(null, "Você já tem uma solicitação em aberto !\nFavor aguardar.\n"
											+ "\nMês/Ano Solicitação: " + String.valueOf( Utils.formatDate(dataSol) )
											+ "\nTipo alteração solicitada: " + solicitacaoAlteracao.getTipo_bilhete_alteracao());
				} else {
					solicitacaoAlteracao = new SolAltBil();

					LocalDateTime dataSolicitacao = LocalDateTime.now();
					DateTimeFormatter formatDateSol = DateTimeFormatter.ofPattern("MMyyyy");

					Integer solAltBilMaxSeq = null;
					try {
						solAltBilMaxSeq = solAltBilDAO.obterMaxSeqSolAltBil();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					if (solAltBilMaxSeq != null && solAltBilMaxSeq > 0) {
						solicitacaoAlteracao.setId_solicitacao(++solAltBilMaxSeq);
					} else { 
						solicitacaoAlteracao.setId_solicitacao(0);
					}
					
					solicitacaoAlteracao.setCpf(cpf);
					solicitacaoAlteracao.setAnoMes_solicitacao(Integer.valueOf(dataSolicitacao.format(formatDateSol)));
					solicitacaoAlteracao.setTipo_bilhete_alteracao(tipo);
					solicitacaoAlteracao.setStatus("P"); // pendente

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
	
	public void consultarSolAltBilUsu(String cpf) {
		SolAltBilDAO solAltBilDAO = new SolAltBilDAO();
		
		if (Utils.verificaString(cpf)) {
			String statusEscolhido = showInputDialog("Você deseja visualizar as solicitações com qual status? \nA. Aprovadas\nR. Reprovadas\nP. Pendentes\nT. Todas");
			if (Utils.verificaString(statusEscolhido)) {
				List<SolAltBil> listSolAltBilUsu = solAltBilDAO.obterListaSolicitacoes(statusEscolhido);
				if (listSolAltBilUsu != null && !listSolAltBilUsu.isEmpty()) {
					String solicitacoes = null;
					for (SolAltBil solicitacao: listSolAltBilUsu) {
						solicitacoes += solicitacao + "\n";
					}
					if (Utils.verificaString(solicitacoes)) {
						showMessageDialog(null, "Suas solicitações '" + Utils.switchEscolha(statusEscolhido) + "' " + solicitacoes);
					}
				} else {
					statusEscolhido = Utils.switchEscolha(statusEscolhido);
					showMessageDialog(null, "Nenhuma solicitação com o status " + statusEscolhido + " foi encontrada!");
				}
			} else {
				showMessageDialog(null, "Opção " + statusEscolhido + " é inválida!");
			}
		}
	}
	
	/**
	 * @desctiption Realiza a transferência e contato com o usuário.
	 * @param cpf CPF do usuário que irá transferir o dinheiro.
	 */
	public void transferirValor(String cpf) {
		if (Utils.verificaString(cpf)) {
			
			UsuarioDAO usuDAO = new UsuarioDAO();
			BilheteDAO bilheteDAO = new BilheteDAO();
			
			Usuario usuario = usuDAO.obterUsuarioPorCPF(cpf);
			BilheteUnico bilheteUsuario  = bilheteDAO.obterBilhetePorCPF(cpf);
			
			if (usuario != null && bilheteUsuario != null) {
				String cpfDestino = showInputDialog("\nInforme o CPF de quem vai receber: ");
				if (Utils.verificaString(cpfDestino)) {
					if (cpfDestino.equalsIgnoreCase(cpf)) {
						showMessageDialog(null, "Você não pode transferir para você mesmo.");
					} else {
						Usuario usuDestino = usuDAO.obterUsuarioPorCPF(cpfDestino);
						if (usuDestino != null) {
							Double valorEnviado = null;
							valorEnviado = Double.valueOf(showInputDialog("Informe o valor que deseja enviar para o " + usuDestino.getNome() + "\nSaldo atual: R$" + bilheteUsuario.getSaldo()));
							if (valorEnviado != null && (valorEnviado < 0 || valorEnviado > bilheteUsuario.getSaldo())) {
								showMessageDialog(null, "Saldo insuficiente!\nSaldo atual: " + bilheteUsuario.getSaldo());
							} else {
								String confirmacao = "N";
								confirmacao = showInputDialog("Você tem certeza que deseja transferir R$" + valorEnviado + " para " + usuDestino.getNome() + " ?");
								if (Utils.verificaString(confirmacao) && confirmacao.equalsIgnoreCase("S")) {
									this.realizaDebito(usuario, valorEnviado);
									this.realizaTransferencia(usuDestino, valorEnviado);
									
									Double saldoAtual = this.consultarSaldoUsu(cpf);
									String msgConfirmacao = "Transferência realizada com sucesso! Informações: "
															+ "\nData da transferência: " + Utils.formatBusinessDate(new Date()) 
															+ "\nPara: " + usuDestino.getNome()
															+ "\nCPF destino: " + "***.".concat(cpfDestino).concat(".***-**")
															+ "\nValor: R$" + valorEnviado
															+ "\n\nSaldo apos transferência : R$ " + saldoAtual;
									
									showMessageDialog(null, msgConfirmacao);
								} else {
									showMessageDialog(null, "Tudo bem. Operação sendo cancelada.");
								}
							}
						} else {
							showMessageDialog(null, "Usuario com o cpf " + cpfDestino + " não foi encontrado.");
						}
					}
					
				} else {
					showMessageDialog(null, "CPF inválido!");
				}
			}
		}
			
	}
	
	public void consultarPerfilUsuario(String cpf) {
		if (Utils.verificaString(cpf)) {
			UsuarioDAO usuDAO = new UsuarioDAO();
			BilheteDAO bilheteDAO = new BilheteDAO();
			
			Usuario usuario = usuDAO.obterUsuarioPorCPF(cpf);
			BilheteUnico bilheteUsuario  = bilheteDAO.obterBilhetePorCPF(cpf);
			
			if (usuario != null && bilheteUsuario != null) {
				showMessageDialog(null, "Informações do usuário: " + usuario.getNome() + "\nCPF: " + usuario.getCpf() + "\nTipo: " + usuario.getTipo() + bilheteUsuario);
			}
		}
	}

	/**
	 * @description Consulta o saldo de um usuário.
	 * @param cpf CPF do usuário que deseja consulta o saldo.
	 * @return String com o saldo atual do usuário formatado.
	 */
	public void consularSaldo(String cpf) {
		BilheteDAO bDAO = new BilheteDAO();
		BilheteUnico bilheteAtu = bDAO.obterBilhetePorCPF(cpf);
		
		if(bilheteAtu != null) 
			showMessageDialog(null, "Seu saldo é de : " + NumberFormat.getCurrencyInstance().format(bilheteAtu.getSaldo()));	
	}
	
	/**
	 * @description Realiza a transferência de valores entre as contas.
	 * @param usuarioDestino Usuário que irá receber o valor.
	 * @param valor Valor que será acrescentado ou debitádo do usuário.
	 */
	protected void realizaTransferencia(Usuario usuarioDestino, Double valor) {
		BilheteDAO bilheteDAO = new BilheteDAO();
		
		BilheteUnico bilheteUsuDestino = bilheteDAO.obterBilhetePorCPF(usuarioDestino.getCpf());
		if (bilheteUsuDestino != null) {
			Double saldoAtual = bilheteUsuDestino.getSaldo();
			Double valorTotal = this.calculaSaldoTransferencia(saldoAtual, valor, "+");
			if (valorTotal != null) {
				bilheteDAO.atualizarBilhete(bilheteUsuDestino.getNumero(), usuarioDestino.getCpf(), valorTotal);
			} else {
				showInputDialog("Valor à ser enviado é inválido.\nValor: " + valor);
			}
			
		}
	}
	
	/**
	 * @description Efetiva o débito de saldo entre as contas.
	 * @param usuarioEnviando Usuário que está enviando o valor.
	 * @param valorEnviado Valor à ser enviado.
	 */
	protected void realizaDebito(Usuario usuarioEnviando, Double valorEnviado) {
		BilheteDAO bilheteDAO = new BilheteDAO();		
		BilheteUnico bilheteUsuEnviando = bilheteDAO.obterBilhetePorCPF(usuarioEnviando.getCpf());
		
		if (bilheteUsuEnviando != null) {
			Double saldoAtual = bilheteUsuEnviando.getSaldo();
			Double saldoAposDebido = this.calculaSaldoTransferencia(saldoAtual, valorEnviado, "-");
			if ( saldoAposDebido != null ) {
				bilheteDAO.atualizarBilhete(bilheteUsuEnviando.getNumero(), usuarioEnviando.getCpf(), saldoAposDebido);
			} else {
				showInputDialog("Erro ao realizar o débito: R$" + valorEnviado);
			}
		}
		
	}
	
	/**
	 * @description Realiza o cálculo do saldo do usuário com o valor à ser acrescentado ou debitado.
	 * @param saldoAtual Saldo atual do usuário.
	 * @param valorTransferencia Valor à ser transferido.
	 * @param tipo Tipo de transação: '+' ou '-'.
	 * @return saldo após o cálculo.
	 */
	protected Double calculaSaldoTransferencia(Double saldoAtual, Double valorTransferencia, String tipo) {
		if (valorTransferencia >= 0) {
			if (tipo.equalsIgnoreCase("+")) {
				return saldoAtual + valorTransferencia;				
			} else {
				return saldoAtual - valorTransferencia;
			}
		}
		return null;
	}
	
	/**
	 * @description Realiza a consulta do saldo do usuário.
	 * @param cpf CPF do usuário que está sendo consultado.
	 * @return saldo do usuário.
	 */
	protected Double consultarSaldoUsu(String cpf) {
		BilheteDAO bilheteDAO = new BilheteDAO();
		if (Utils.verificaString(cpf)) {
			BilheteUnico bilheteUsu = bilheteDAO.obterBilhetePorCPF(cpf);
			if (bilheteUsu != null) {
				return bilheteUsu.getSaldo();
			}
		}
		return null;
	}

	private String gerarMenuUsuario() {
		String menu = "Escolha uma operação:\n";
		menu += "1. Carregar Bilhete\n";
		menu += "2. Passar na Catraca\n";
		menu += "3. Consultar Saldo\n";
		menu += "4. Alterar tipo Bilhete\n";
		menu += "5. Solicitacar Alteração Tipo Bilhete\n";
		menu += "6. Minhas solicitações\n";
		menu += "7. Meu perfil\n";
		menu += "8. Transferir valor\n";
		menu += "9. Sair";
		return menu;
	}

}
