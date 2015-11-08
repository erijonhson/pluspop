package exception;

import java.text.ParseException;

public class ConversaoDeDataException extends ParseException {

	private static final long serialVersionUID = 118226197607863576L;

	public ConversaoDeDataException(ParseException pe) {
		super(getMensagem(pe), pe.getErrorOffset());
	}

	private static String getMensagem(ParseException e) {
		if (e instanceof FormatoDeDataException)
			return e.getMessage();
		else
			return "Data nao existe.";
	}
}