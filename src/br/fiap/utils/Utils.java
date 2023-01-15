package br.fiap.utils;

import java.util.Random;

public class Utils {

	/**
	 * @author Pedro
	 * @since 01/2023
	 * 
	 * @descripiton Gera um n�mero aleat�rio de 0 � 100.000.
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
	 * @param str -> String � ser verificada.
	 * @descripiton Recebe uma string e verifca se ela � diferente de null e se n�o
	 *              � uma string vazia
	 */
	public static boolean verificaString(String str) {
		return str != null && !str.trim().equalsIgnoreCase("") ? true : false;
	}

	/**
	 * @author Pedro
	 * @since 01/2023
	 * 
	 * @param date -> Data � ser formatada.
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
	 * @param Status -> Status da solicita��o em quest�o.
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
