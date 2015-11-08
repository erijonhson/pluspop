package exception;

public class LoginException extends Exception {

	private static final long serialVersionUID = 1954246253432570683L;

	public LoginException(Exception e) {
		super("Nao foi possivel realizar login. " + e.getMessage(), e.getCause());
	}
}
