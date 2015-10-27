package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.ConversaoDeDataException;
import exception.FormatoDeDataException;

/**
 * Conversor de data a partir de string do tipo dd/MM/yyyy. <br>
 * Sobre validar datas: http://www.guj.com.br/657-validar-data-em-java <br>
 * Sobre construir expressões regulares: http://turing.com.br/material/regex/introducao.html <br>
 * Sobre singleton: http://www.devmedia.com.br/trabalhando-com-singleton-java/23632 <br>
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

	DateFormat formatadorDeData;
	private Pattern pattern;

	private static final String DATE_PATTERN = "\\d{2}/{1}\\d{2}/{1}\\d{4}";

	private ConversorDeData() {
		formatadorDeData = new SimpleDateFormat("dd/MM/yyyy");
		formatadorDeData.setLenient(false);
		pattern = Pattern.compile(DATE_PATTERN);
	}

	public Date converterData(String dataNasc) throws ConversaoDeDataException {
		try {
			if (!formatoDataValido(dataNasc))
				throw new FormatoDeDataException();
			return formatadorDeData.parse(dataNasc);
		} catch (ParseException pe) {
			throw new ConversaoDeDataException(pe);
		}
	}

	private boolean formatoDataValido(final String dataNasc) {
		Matcher matcher = pattern.matcher(dataNasc);
		return matcher.matches();
	}
}
