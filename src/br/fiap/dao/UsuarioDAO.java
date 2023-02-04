package br.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.fiap.conexao.Conexao;
import br.fiap.modelo.Usuario;

public class UsuarioDAO {

	Conexao conexao = new Conexao();
	public static final Integer TODOS = 4;
	
	public List<Usuario> obterListUsuario(Integer argEscolha) throws SQLException {
		System.out.println("iniciando método: obterListUsuario(argEscolha)");
		
		Connection connectcion;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		
		List<Usuario> result = null;
		argEscolha = argEscolha == null ? TODOS : argEscolha;
		
		connectcion = conexao.conectar();
		StringBuffer sql = new StringBuffer();										
		
		try {
			sql.append(	"SELECT ");
			sql.append(		"JAVA_USUARIO.NOME, ");
			sql.append(		"JAVA_USUARIO.CPF, ");
			sql.append(		"JAVA_USUARIO.TIPO, ");
			sql.append(		"JAVA_BILHETE.SALDO ");
			sql.append(	"FROM JAVA_USUARIO JAVA_USUARIO ");
			sql.append(		"LEFT JOIN JAVA_BILHETE JAVA_BILHETE ");
			sql.append(		"ON JAVA_USUARIO.CPF = JAVA_BILHETE.CPF ");
			if (!this.switchEscolhaBilhete(argEscolha).equalsIgnoreCase("Todos")) {				
				sql.append(	"WHERE ");
				sql.append(		"JAVA_USUARIO.TIPO = ?");
			}
			
			preparedStatement = connectcion.prepareCall(sql.toString());
			if (argEscolha.compareTo(TODOS) != 0) {
				preparedStatement.setString(1, this.switchEscolhaBilhete(argEscolha));
			}
			
			resultSet = preparedStatement.executeQuery();
			result = new ArrayList<>();
			
			while(resultSet.next()) {
				Usuario vo = new Usuario();
				vo.setNome	(resultSet.getString("NOME"));
				vo.setCpf	(resultSet.getString("CPF"));
				vo.setTipo	(resultSet.getString("TIPO"));
				vo.setSaldo	(resultSet.getDouble("SALDO"));
				
				result.add(vo);
			}
			
			return result;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			System.out.println("finalizando método: obterListUsuario(argEscolha)");
		}
		return result;
	}

	public boolean obterCPFPorChave(String cpf)  {
		try {

			Connection connection;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			boolean result = false;
			
			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"SELECT ");
				sql.append(		"NOME,  ");
				sql.append(		"CPF, ");
				sql.append(		"TIPO ");
				sql.append(	"FROM ");
				sql.append(		"JAVA_USUARIO ");
				sql.append(	"WHERE ");
				sql.append(		"CPF = ?");
				
				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, cpf);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					result = true;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		} finally {
			System.out.println("Finalizando método obterCPFPorChave.");
		}
	}

	public void atualizarNomeUsuario(String cpf, String nome) {
		try {
			System.out.println("iniciando método atualizarUsuario.");
			Connection connection;
			PreparedStatement preparedStatement = null;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"UPDATE ");
				sql.append(		"JAVA_USUARIO ");
				sql.append(	"SET ");
				sql.append(		"NOME = ? ");
				sql.append(	"WHERE ");
				sql.append( 	"CPF = ?");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, nome);
				preparedStatement.setString(2, cpf);
				preparedStatement.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("finalizando método atualizarUsuario.");
		}
	}
	
	public void inserirUsuario(String nome, String cpf, String tipo)  {
		try {

			Connection connection;
			PreparedStatement preparedStatement = null;
			
			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"INSERT INTO ");
				sql.append(		"JAVA_USUARIO ");
				sql.append(	"(NOME, ");
				sql.append(		"CPF, ");
				sql.append(		"TIPO) ");
				sql.append(	" VALUES(?, ?, ?)");
				
				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, nome);
				preparedStatement.setString(2, cpf);
				preparedStatement.setString(3, tipo);
				preparedStatement.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("Finalizando método inserirUsuario.");
		}
	}
	
	public Usuario obterUsuarioPorCPF(String cpf) {
		try {

			Connection connection;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			Usuario result = null;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"SELECT ");
				sql.append(		"NOME,  ");
				sql.append(		"CPF, ");
				sql.append(		"TIPO ");
				sql.append(	"FROM ");
				sql.append(		"JAVA_USUARIO ");
				sql.append(	"WHERE ");
				sql.append(		"CPF = ?");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, cpf);
				resultSet = preparedStatement.executeQuery();

				while(resultSet.next()) {
					result = new Usuario();
					result.setNome(resultSet.getString("NOME"));
					result.setCpf(resultSet.getString("CPF"));
					result.setTipo(resultSet.getString("TIPO"));
				}
				
				return result;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		} finally {
			System.out.println("Finalizando método obterBilhetePorCPF.");
		}
	}
	
	
	public void excluirUsuarioPorCpf(String cpf) {
		try {
			System.out.println("iniciando método excluirUsuarioPorCpf(cpf).");
			Connection connection;
			PreparedStatement preparedStatement = null;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"DELETE FROM ");
				sql.append(		"JAVA_USUARIO ");
				sql.append(	"WHERE ");
				sql.append(		"CPF = ? ");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, cpf);
				preparedStatement.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("finalizando método excluirUsuarioPorCpf(cpf).");
		}
	}
	
	public void atualizarTipoBilhete(String cpf, String tipo) {
		try {

			Connection connection;
			PreparedStatement preparedStatement = null;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"UPDATE ");
				sql.append(		"JAVA_USUARIO ");
				sql.append(	"SET ");
				sql.append(		"TIPO = ? ");
				sql.append(	"WHERE ");
				sql.append( 	"CPF = ?");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, tipo);
				preparedStatement.setString(2, cpf);
				preparedStatement.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("Finalizando método atualizarBilhete.");
		}
	}
	
	private String switchEscolhaBilhete(Integer argEscolha) {
		String translatedValue = null;
		
		switch (argEscolha) {
		case 1:
			translatedValue = "Normal";
			break;
		case 2:
			translatedValue = "Estudante";
			break;
		case 3:
			translatedValue = "Professor";
			break;
		case 4:
			translatedValue = "Todos";
		default:
			translatedValue = "Todos";
			break;
		}
		return translatedValue;
	}
	
}
