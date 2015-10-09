package exception;

public class EmailInvalidoException extends Exception {

	private static final long serialVersionUID = -5979520826302796077L;

	public EmailInvalidoException() {
		super("Formato de e-mail esta invalido.");
	}
}