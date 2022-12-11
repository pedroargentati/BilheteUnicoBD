package br.fiap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.fiap.conexao.Conexao;
import br.fiap.modelo.TipoBilhete;

public class TipoBilheteDAO {

	Conexao conexao = new Conexao();
	
	public List<TipoBilhete> obterListaTipos() {
		System.out.println("iniciando método obterListaTipos.");
		try {

			Connection connection;
			ResultSet resultSet = null;
			PreparedStatement preparedStatement = null;
			List<TipoBilhete> result = null; 
			
			connection = conexao.conectar();
			StringBuffer sql = new StringBuffer();

			try {
				sql.append(	"SELECT ");
				sql.append(		"ID_TIPO_PASSAGEM,  ");
				sql.append(		"TIPO_PASSAGEM ");
				sql.append(	"FROM ");
				sql.append(		"JAVA_TIPO_BILHETE ");
				
				preparedStatement = connection.prepareStatement(sql.toString());
				resultSet = preparedStatement.executeQuery();
				
				result = new ArrayList<TipoBilhete>();
				while(resultSet.next()) {
					TipoBilhete vo = new TipoBilhete();
					vo.setId_tipo_bilhete(resultSet.getInt("ID_TIPO_PASSAGEM"));
					vo.setTipo_passagem(resultSet.getString("TIPO_PASSAGEM"));
					
					result.add(vo);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		} finally {
			System.out.println("finalizando método obterListaTipos.");
		}
	}
	
}
