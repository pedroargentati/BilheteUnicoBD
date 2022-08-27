package br.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.fiap.conexao.Conexao;
import br.fiap.modelo.BilheteUnico;

public class BilheteDAO {

	Conexao conexao = new Conexao();

	public void inserirBilhete(double numero, String cpf, double saldo) {
		try {

			Connection connection;
			PreparedStatement preparedStatement = null;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"INSERT INTO ");
				sql.append(		"JAVA_BILHETE ");
				sql.append(	"(NUMERO, ");
				sql.append(		"CPF, ");
				sql.append(		"SALDO) ");
				sql.append(" VALUES(?, ?, ?)");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setDouble(1, numero);
				preparedStatement.setString(2, cpf);
				preparedStatement.setDouble(3, saldo);
				preparedStatement.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("Finalizando método inserirBilhete.");
		}
	}
	
	public void atualizarBilhete(double numero, String cpf, double saldo) {
		try {

			Connection connection;
			PreparedStatement preparedStatement = null;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"UPDATE ");
				sql.append(		"JAVA_BILHETE ");
				sql.append(	"SET ");
				sql.append(		"SALDO = ? ");
				sql.append(	"WHERE ");
				sql.append( 	"CPF = ?");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setDouble(1, saldo);
				preparedStatement.setString(2, cpf);
				preparedStatement.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("Finalizando método atualizarBilhete.");
		}
	}

	public boolean obterBilhetePorChave(double numero) {
		try {

			Connection connection;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			boolean result = false;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"SELECT ");
				sql.append(		"NUMERO,  ");
				sql.append(		"CPF, ");
				sql.append(		"SALDO ");
				sql.append(		"FROM ");
				sql.append(	  "JAVA_BILHETE ");
				sql.append(	"WHERE ");
				sql.append(		"NUMERO = ?");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setDouble(1, numero);
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					result = true;
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		} finally {
			System.out.println("Finalizando método obterBilhetePorChave.");
		}
	}

	public List<BilheteUnico> obterListBilhete() throws SQLException {

		Connection connectcion;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		
		List<BilheteUnico> result = null;
		
		connectcion = conexao.conectar();
		StringBuffer sql = new StringBuffer();
		
		try {
			sql.append(	"SELECT ");
			sql.append(		"NUMERO,  ");
			sql.append(		"CPF, ");
			sql.append(		"SALDO ");
			sql.append(	"FROM ");
			sql.append(		"JAVA_BILHETE");
			
			preparedStatement = connectcion.prepareCall(sql.toString());
			resultSet = preparedStatement.executeQuery();
			result = new ArrayList<>();
			
			while(resultSet.next()) {
				BilheteUnico vo = new BilheteUnico(); 
				vo.setNumero(resultSet.getInt("NUMERO"));
				vo.setCpf(resultSet.getString("CPF"));
				vo.setSaldo(resultSet.getDouble("SALDO"));
				result.add(vo);
			}
			
			return result;
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return result;
	}
	
	public BilheteUnico obterBilhetePorCPF(String cpf) {
		try {

			Connection connection;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			BilheteUnico result = null;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"SELECT ");
				sql.append(		"NUMERO,  ");
				sql.append(		"CPF, ");
				sql.append(		"SALDO ");
				sql.append(	"FROM ");
				sql.append(	  "JAVA_BILHETE ");
				sql.append(	"WHERE ");
				sql.append(		"CPF = ?");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, cpf);
				resultSet = preparedStatement.executeQuery();

				while(resultSet.next()) {
					result = new BilheteUnico();
					result.setNumero(resultSet.getInt("NUMERO"));
					result.setCpf(resultSet.getString("CPF"));
					result.setSaldo(resultSet.getDouble("SALDO"));
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

}
