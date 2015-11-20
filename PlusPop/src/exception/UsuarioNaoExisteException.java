package exception;

public class UsuarioNaoExisteException extends Exception {

	private static final long serialVersionUID = -2808695496033112542L;

	public UsuarioNaoExisteException(String emailUsuario) {
		super("Um usuarix com email " + emailUsuario + " nao esta cadastradx.");
	}

}
