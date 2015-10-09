package exception;

public class LogoutException extends Exception {

	private static final long serialVersionUID = 5908773299754103418L;

	public LogoutException() {
		super("Nao eh possivel realizar logout. Nenhum usuarix esta logadx no +pop.");
	}
}
