package exception;

public class ConteudoPostNegativoException extends Exception {
	
	public ConteudoPostNegativoException(Exception e) {
		super("Requisicao invalida. O indice deve ser maior ou igual a zero.");
	}

}
