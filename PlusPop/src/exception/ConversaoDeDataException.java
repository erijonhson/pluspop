package exception;

import java.text.ParseException;

public class ConversaoDeDataException extends ParseException {

	private static final long serialVersionUID = 118226197607863576L;

	public ConversaoDeDataException(ParseException pe, boolean formatoValido) {
		super(getMenssagem(formatoValido), pe.getErrorOffset());
	}

	private static String getMenssagem(boolean formatoValido) {
		return !formatoValido ? "Formato de data esta invalida." : "Data nao existe.";
	}
}