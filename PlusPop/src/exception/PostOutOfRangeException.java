package exception;

public class PostOutOfRangeException extends Exception {

	private static final long serialVersionUID = 5650942921507138197L;

	public PostOutOfRangeException(String message) {
		super("Post #" + message.split(" ")[0]
				+ " nao existe. Usuarix possui apenas "
				+ message.split(" ")[1] + " post(s).");
	}

}
