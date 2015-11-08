package exception;

public class CadastraUsuarioException extends Exception {

	private static final long serialVersionUID = 929250887006307211L;

	public CadastraUsuarioException(Exception e) {
		super("Erro no cadastro de Usuarios. " + e.getMessage(), e.getCause());
	}

}
