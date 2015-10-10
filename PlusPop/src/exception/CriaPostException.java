package exception;

public class CriaPostException extends Exception {

	private static final long serialVersionUID = 6840053165710545162L;

	public CriaPostException(Exception e) {
		super("Nao eh possivel criar o post. " + e.getMessage());
	}
}
