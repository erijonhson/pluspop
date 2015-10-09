package exception;

public class SenhaInvalidaException extends Exception {

	private static final long serialVersionUID = -872495122731881678L;

	public SenhaInvalidaException() {
		super("Senha invalida.");
	}
}
