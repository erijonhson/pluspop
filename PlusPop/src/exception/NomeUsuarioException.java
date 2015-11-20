package exception;

public class NomeUsuarioException extends Exception {

	private static final long serialVersionUID = 4853358646123764544L;

	public NomeUsuarioException() {
		super("Nome dx usuarix nao pode ser vazio.");
	}
}
