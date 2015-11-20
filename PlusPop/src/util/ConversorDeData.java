package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.ConversaoDeDataException;
import exception.FormatoDeDataException;

/**
 * Conversor de data a partir de string do tipo dd/MM/yyyy. <br>
 * Sobre validar datas: http://www.guj.com.br/657-validar-data-em-java <br>
 * Sobre construir expressões regulares: http://turing.com.br/material/regex/introducao.html <br>
 * Sobre singleton: https://blogs.oracle.com/JavaFundamentals/entry/using_enhanced_for_loops_with <br>
 * 
 * @author Eri Jonhson
 */
public class ConversorDeData {

	private static ConversorDeData instance;

	/**
	 * Devolve o conversor de data do sistema. <br>
	 * Sobre métodos sincronizados: https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
	 * 
	 * @return Conversor de Data único.
	 */
	public static synchronized ConversorDeData getInstance() {
		if (instance == null)
			instance = new ConversorDeData();
		return instance;
	}

	private Pattern pattern;

	private static final String DATE_PATTERN = "\\d{2}/{1}\\d{2}/{1}\\d{4}";

	private ConversorDeData() {
		pattern = Pattern.compile(DATE_PATTERN);
	}

	/**
	 * Converte data no formato dd/MM/yyyy. <br>
	 * 
	 * @param dataNasc
	 * @param formato
	 * @return 
	 * @throws ConversaoDeDataException
	 * 
	 * @author Eri Jonhson
	 * @author Ítalo Héctor
	 */
	public LocalDate converterData(String dataNasc) throws ConversaoDeDataException {
		try {
			if (!formatoDataValido(dataNasc))
				throw new FormatoDeDataException();
			String[] data = dataNasc.split("/");
			return LocalDate.of(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0]));
		} catch (RuntimeException pe) {
			throw new ConversaoDeDataException(pe);
		}
	}

	private boolean formatoDataValido(final String dataNasc) {
		Matcher matcher = pattern.matcher(dataNasc);
		return matcher.matches();
	}
}
