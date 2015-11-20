package exception;

public class PostOutOfRangeException extends Exception {

	private static final long serialVersionUID = 5650942921507138197L;

	public PostOutOfRangeException(int postNumber, int sizePost) {
		super("Post #" + postNumber + " nao existe. Usuarix possui apenas "
				+ sizePost + " post(s).");
	}

}
