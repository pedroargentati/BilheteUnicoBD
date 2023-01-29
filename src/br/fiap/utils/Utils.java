package br.fiap.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 
 * @author Pedro
 * @since 01/2023
 * 
 * @descripiton Classe criada com o intuito de facilitar e agilizar o desenvolvimento dos projetos.
 * 				Contém diversos métodos de verificação, formatação, tradução, geração de dados e/ou variáveis.
 */
public class Utils {

	/**
	 * @author Pedro
	 * @since 01/2023
	 * 
	 * @descripiton Gera um número aleatório de 0 à 100.000.
	 */
	public static double gerarNumeroAleatorio() {
		double numero = 0;

		Random random = new Random();
		numero = random.nextInt(100000);

		return numero;
	}

	/**
	 * @author Pedro
	 * @since 01/2023
	 * 
	 * @param str -> String à ser verificada.
	 * @descripiton Recebe uma string e verifca se ela é diferente de null e se não
	 *              é uma string vazia
	 */
	public static boolean verificaString(String str) {
		return str != null && !str.trim().equalsIgnoreCase("") ? true : false;
	}

	/**
	 * @author Pedro
	 * @since 01/2023
	 * 
	 * @param date -> Data à ser formatada.
	 * @descripiton Recebe uma string e formata ela como: 'mm/YYYY'.
	 */
	public static String formatDate(String date) {
		if (date.length() == 6) {
			return date.substring(0, 2) + "/".concat(date.substring(2, 6));
		} else {
			return "12/9999";
		}
	}
	
	/**
	 * @author Pedro
	 * @since 01/2023
	 * 
	 * @param date Data à ser formatada
	 * @return Data formatada: 'DD/MM/YYYY' ou 'YYYY/MM.
	 */
	public static String formatBusinessDate(Date date, String format) {
		if (!format.equalsIgnoreCase("YYYYMM")) 
			return null;
		
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	/**
	 * @author Pedro
	 * @since 01/2023
	 * 
	 * @param date Data à ser formatada
	 * @return Data formatada: 'DD/MM/YYYY'
	 */
	public static String formatBusinessDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("DD/MM/YYYY");
		return formatter.format(date);
	}

	/**
	 * @author Pedro
	 * @since 01/2023
	 * 
	 * @param Status -> Status da solicitação em questão.
	 * @descripiton Recebe uma string e traduz para seu significado completo.
	 */
	public static String switchEscolha(String statusEscolhido) {
		switch (statusEscolhido) {
		case "T":
			statusEscolhido = "TODAS";
			break;
		case "P":
			statusEscolhido = "PENDENTES";
			break;
		case "A":
			statusEscolhido = "APROVADAS";
			break;
		case "R":
			statusEscolhido = "REPROVADAS";
			break;
		default:
			break;
		}
		return statusEscolhido;
	}

}
