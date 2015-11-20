package exception;

public class SenhaProtegidaException extends Exception {

	private static final long serialVersionUID = 3905878922743309175L;

	public SenhaProtegidaException() {
		super("A senha dx usuarix eh protegida.");
	}
}
