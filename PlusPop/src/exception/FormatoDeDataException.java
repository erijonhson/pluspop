package exception;

public class FormatoDeDataException extends RuntimeException {

	private static final long serialVersionUID = -6645242371153452881L;

	public FormatoDeDataException() {
		super("Formato de data esta invalida.");
	}
}
