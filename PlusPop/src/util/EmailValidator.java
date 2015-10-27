package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validador de e-mail com expressões regulares. Disponível em:
 * http://www.mkyong
 * .com/regular-expressions/how-to-validate-email-address-with-regular
 * -expression/
 * Sobre singleton: http://www.devmedia.com.br/trabalhando-com-singleton-java/23632 <br>
 * 
 * @author mkyong
 */
public class EmailValidator {
	
	private static EmailValidator instance;

	public static synchronized EmailValidator getInstance() {
		if (instance == null)
			instance = new EmailValidator();
		return instance;
	}

	private Pattern pattern;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public boolean validateEmail(final String email) {
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
