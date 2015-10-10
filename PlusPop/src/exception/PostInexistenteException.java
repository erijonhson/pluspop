package exception;

public class PostInexistenteException extends Exception {
	
	public PostInexistenteException(String e) {
		super("Item #" + e.split(" ")[0] + " nao existe nesse post, ele possui apenas " + e.split(" ")[1] + " itens distintos.");
	}

}
