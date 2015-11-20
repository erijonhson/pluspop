package exception;

public class FechaSistemaException extends Exception {

	private static final long serialVersionUID = -1827444736093755471L;

	public FechaSistemaException() {
		super("Nao foi possivel fechar o sistema. Um usuarix ainda esta logadx.");
	}
}
