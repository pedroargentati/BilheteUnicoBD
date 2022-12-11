package br.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.fiap.conexao.Conexao;
import br.fiap.modelo.SolAltBil;

public class SolAltBilDAO {

	Conexao conexao = new Conexao();

	public SolAltBil obterSolAltBilPorCpf(String cpf) {
		System.out.println("iniciando método obterSolAltBilPorCpf.");
		try {

			Connection connection;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			SolAltBil result = null;
			
			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"SELECT ");
				sql.append(		"ID_SOLICITACAO,  ");
				sql.append(		"CPF, ");
				sql.append(		"ANOMES_SOLICITACAO, ");
				sql.append(		"TIPO_BILHETE_ALTERACAO ");
				sql.append(	"FROM ");
				sql.append(		"JAVA_SOLALTBIL ");
				sql.append(	"WHERE ");
				sql.append(		"CPF = ?");
				
				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, cpf);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					result = new SolAltBil();
					result.setId_solicitacao		(resultSet.getInt("ID_SOLICITACAO"));
					result.setCpf					(resultSet.getString("CPF"));
					result.setAnoMes_solicitacao	(resultSet.getInt("ANOMES_SOLICITACAO"));
					result.setTipo_bilhete_alteracao(resultSet.getString("TIPO_BILHETE_ALTERACAO"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		} finally {
			System.out.println("finalizando método obterSolAltBilPorCpf.");
		}
	}
	
	public void inserirSolAltBil(SolAltBil solAltBil) {
		System.out.println("iniciando método inserirSolAltBil.");
		try {

			Connection connection;
			PreparedStatement preparedStatement = null;
			
			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"INSERT INTO ");
				sql.append(		"JAVA_SOLALTBIL ");
				sql.append(	"(ID_SOLICITACAO,  ");
				sql.append(	"CPF, ");
				sql.append(	"ANOMES_SOLICITACAO, ");
				sql.append(	"TIPO_BILHETE_ALTERACAO ) ");
				sql.append(		" VALUES(?, ?, ?, ?)");
				
				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setInt	(1, solAltBil.getId_solicitacao());
				preparedStatement.setString	(2, solAltBil.getCpf());
				preparedStatement.setInt	(3, solAltBil.getAnoMes_solicitacao());
				preparedStatement.setString	(4, solAltBil.getTipo_bilhete_alteracao());
				preparedStatement.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("finalizando método inserirSolAltBil.");
		}
	}
	
	public List<SolAltBil> obterListaSolicitacoes() {
		System.out.println("iniciando método obterListaSolicitacoes.");
		try {

			Connection connection;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			List<SolAltBil> result = null; 
			
			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"SELECT ");
				sql.append(		"ID_SOLICITACAO,  ");
				sql.append(		"CPF, ");
				sql.append(		"ANOMES_SOLICITACAO, ");
				sql.append(		"TIPO_BILHETE_ALTERACAO ");
				sql.append(	"FROM ");
				sql.append(		"JAVA_SOLALTBIL ");
				
				preparedStatement = connection.prepareStatement(sql.toString());
				resultSet = preparedStatement.executeQuery();
				
				result = new ArrayList<SolAltBil>();
				while(resultSet.next()) {
					SolAltBil vo = new SolAltBil();
					vo.setId_solicitacao		(resultSet.getInt("ID_SOLICITACAO"));
					vo.setCpf					(resultSet.getString("CPF"));
					vo.setAnoMes_solicitacao	(resultSet.getInt("ANOMES_SOLICITACAO"));
					vo.setTipo_bilhete_alteracao(resultSet.getString("TIPO_BILHETE_ALTERACAO"));
					
					result.add(vo);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		} finally {
			System.out.println("finalizando método obterListaSolicitacoes.");
		}
	}
	
	
	public void efetivarSolAltBil(String cpf, String idSolicitacao) {
		try {
			System.out.println("iniciando método efetivarSolAltBil(cpf, idSolicitacao).");
			Connection connection;
			PreparedStatement preparedStatement = null;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"DELETE FROM ");
				sql.append(		"JAVA_SOLALTBIL ");
				sql.append(	"WHERE ");
				sql.append(		"CPF = ? ");
				sql.append(		"AND ID_SOLICITACAO = ? ");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, cpf);
				preparedStatement.setString(2, idSolicitacao);
				preparedStatement.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("finalizando método efetivarSolAltBil(cpf, idSolicitacao).");
		}
	}
	
}
