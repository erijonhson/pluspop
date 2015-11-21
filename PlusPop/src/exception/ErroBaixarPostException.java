package exception;

public class ErroBaixarPostException extends Exception{

	private static final long serialVersionUID = 1L;
	private static final String msg = "Erro ao baixar posts.";
	
	public ErroBaixarPostException() {
		super(msg);
	}
	
	public ErroBaixarPostException(String erro) {
		super (msg + " " + erro);
	}
	
}
