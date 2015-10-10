package exception;

public class TamanhoMensagemException extends Exception {

	private static final long serialVersionUID = 5825451612827972789L;

	public TamanhoMensagemException() {
		super("O limite maximo da mensagem sao 200 caracteres.");
	}
}
