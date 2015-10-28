package exception;

public class PostInexistenteException extends Exception {

	private static final long serialVersionUID = -6070917069595703592L;

	public PostInexistenteException(String message) {
		super("Item #" + message.split(" ")[0]
				+ " nao existe nesse post, ele possui apenas "
				+ message.split(" ")[1] + " itens distintos.");
	}

}
