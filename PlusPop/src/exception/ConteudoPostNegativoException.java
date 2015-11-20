package exception;

public class ConteudoPostNegativoException extends Exception {

	private static final long serialVersionUID = -2761402874266284587L;

	public ConteudoPostNegativoException() {
		super("Requisicao invalida. O indice deve ser maior ou igual a zero.");
	}

}
