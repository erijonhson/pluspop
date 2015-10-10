package exception;

public class HashTagException extends Exception {

	private static final long serialVersionUID = -3646854020466271780L;

	public HashTagException(String hashTagErrada) {
		super("As hashtags devem comecar com '#'. Erro na hashtag: '" + hashTagErrada + "'.");
	}
}
