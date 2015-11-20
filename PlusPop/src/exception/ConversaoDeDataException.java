package exception;

public class ConversaoDeDataException extends RuntimeException {

	private static final long serialVersionUID = 118226197607863576L;

	public ConversaoDeDataException(RuntimeException pe) {
		super(getMensagem(pe), pe.getCause());
	}

	private static String getMensagem(RuntimeException e) {
		if (e instanceof FormatoDeDataException)
			return e.getMessage();
		else
			return "Data nao existe.";
	}
}