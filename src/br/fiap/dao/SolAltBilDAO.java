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
				sql.append(		"TIPO_BILHETE_ALTERACAO, ");
				sql.append(		"STATUS ");
				sql.append(	" FROM ");
				sql.append(		" JAVA_SOLALTBIL ");
				sql.append(	" WHERE ");
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
					result.setStatus				(resultSet.getString("STATUS"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		} finally {
			System.out.println("finalizando método obterSolAltBilPorCpf.");
		}
	}
	
	public SolAltBil obterSolAltBilPendentesPorCpf(String cpf) {
		System.out.println("iniciando método obterSolAltBilPendentesPorCpf.");
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
				sql.append(		"TIPO_BILHETE_ALTERACAO, ");
				sql.append(		"STATUS ");
				sql.append(	" FROM ");
				sql.append(		" JAVA_SOLALTBIL ");
				sql.append(	" WHERE ");
				sql.append(		"CPF = ?");
				sql.append(		"AND STATUS = 'P'");
				
				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString(1, cpf);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					result = new SolAltBil();
					result.setId_solicitacao		(resultSet.getInt("ID_SOLICITACAO"));
					result.setCpf					(resultSet.getString("CPF"));
					result.setAnoMes_solicitacao	(resultSet.getInt("ANOMES_SOLICITACAO"));
					result.setTipo_bilhete_alteracao(resultSet.getString("TIPO_BILHETE_ALTERACAO"));
					result.setStatus				(resultSet.getString("STATUS"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		} finally {
			System.out.println("finalizando método obterSolAltBilPendentesPorCpf.");
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
				sql.append(	"TIPO_BILHETE_ALTERACAO, ");
				sql.append(	"STATUS ) ");
				sql.append(		" VALUES(?, ?, ?, ?, ?)");
				
				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setInt	(1, solAltBil.getId_solicitacao());
				preparedStatement.setString	(2, solAltBil.getCpf());
				preparedStatement.setInt	(3, solAltBil.getAnoMes_solicitacao());
				preparedStatement.setString	(4, solAltBil.getTipo_bilhete_alteracao());
				preparedStatement.setString	(5, solAltBil.getStatus());
				preparedStatement.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("finalizando método inserirSolAltBil.");
		}
	}
	
	public List<SolAltBil> obterListaSolicitacoes(String status) {
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
				sql.append(		"TIPO_BILHETE_ALTERACAO, ");
				sql.append(		"STATUS ");
				sql.append(	"FROM ");
				sql.append(		"JAVA_SOLALTBIL ");
				if (!status.toUpperCase().equalsIgnoreCase("T")) {
					sql.append( "  WHERE ");
					sql.append( "	STATUS = ?");
				}
				
				preparedStatement = connection.prepareStatement(sql.toString());
				
				int idx = 1;
				if (!status.toUpperCase().equalsIgnoreCase("T")) {
					preparedStatement.setString	(idx, status);
				}
				resultSet = preparedStatement.executeQuery();
				
				result = new ArrayList<SolAltBil>();
				while(resultSet.next()) {
					SolAltBil vo = new SolAltBil();
					vo.setId_solicitacao		(resultSet.getInt("ID_SOLICITACAO"));
					vo.setCpf					(resultSet.getString("CPF"));
					vo.setAnoMes_solicitacao	(resultSet.getInt("ANOMES_SOLICITACAO"));
					vo.setTipo_bilhete_alteracao(resultSet.getString("TIPO_BILHETE_ALTERACAO"));
					vo.setStatus				(resultSet.getString("STATUS"));
					
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
	
	
	public void efetivarSolAltBil(String cpf, String status, Integer data_aprovacao) {
		try {
			System.out.println("iniciando método efetivarSolAltBil(cpf, idSolicitacao).");
			Connection connection;
			PreparedStatement preparedStatement = null;

			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"UPDATE ");
				sql.append(		"JAVA_SOLALTBIL ");
				sql.append(	"SET ");
				sql.append(		"STATUS = ?, ");
				sql.append(		"DATA_APROVACAO = ? ");
				sql.append(	"WHERE ");
				sql.append(		"CPF = ? ");

				preparedStatement = connection.prepareStatement(sql.toString());
				preparedStatement.setString	(1, status);
				preparedStatement.setInt	(2, data_aprovacao);
				preparedStatement.setString	(3, cpf);
				preparedStatement.execute();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			System.out.println("finalizando método efetivarSolAltBil(cpf, idSolicitacao).");
		}
	}
	
	public Integer obterMaxSeqSolAltBil() throws SQLException {
		try {
			System.out.println("iniciando metodo: obterMaxSeqSolAltBil().");

			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			Integer result = null;
			
			connection = conexao.conectar();

			try {
				
				StringBuffer sql = new StringBuffer();

				sql.append("SELECT ");
				sql.append(		"MAX(ID_SOLICITACAO) AS MAXSEQ ");
				sql.append(	"FROM ");
				sql.append(		"JAVA_SOLALTBIL ");

				preparedStatement = connection.prepareStatement(sql.toString());
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					result = resultSet.getInt("MAXSEQ");
				}

				return result;
				
			} catch (SQLException sqle) {
				throw new SQLException(sqle);
			} 
		} finally {
			System.out.println("finalizando metodo: obterMaxSeqSolAltBil().");
		}
	}
	
}
