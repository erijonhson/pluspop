package exception;

import java.text.ParseException;

public class FormatoDeDataException extends ParseException {

	private static final long serialVersionUID = -6645242371153452881L;

	public FormatoDeDataException() {
		super("Formato de data esta invalida.", -1);
	}
}
