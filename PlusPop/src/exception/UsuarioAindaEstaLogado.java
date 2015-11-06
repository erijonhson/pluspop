package exception;

public class UsuarioAindaEstaLogado extends Exception{

	private static final long serialVersionUID = -3412876082442384822L;
	
	private static final String msg = "Um usuarix ainda esta logadx.";
	
	public UsuarioAindaEstaLogado() {
		super (msg);
	}
}
