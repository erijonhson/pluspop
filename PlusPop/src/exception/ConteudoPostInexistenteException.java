package exception;

public class ConteudoPostInexistenteException extends Exception {

	private static final long serialVersionUID = -8645317550017890630L;

	public ConteudoPostInexistenteException(int indice, int quantItem) {
		super("Item #" + indice + " nao existe nesse post, "
				+ "ele possui apenas " + quantItem + " itens distintos.");
	}

}