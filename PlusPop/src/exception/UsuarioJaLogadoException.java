package exception;

public class UsuarioJaLogadoException extends Exception {

	private static final long serialVersionUID = 4849737620756782704L;

	public UsuarioJaLogadoException(String nomeUsuario) {
		super("Um usuarix ja esta logadx: " + nomeUsuario + ".");
	}
}
