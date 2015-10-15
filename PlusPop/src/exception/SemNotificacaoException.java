package exception;

public class SemNotificacaoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1640634340441418640L;
	
	public SemNotificacaoException(){
		super ("Nao ha mais notificacoes.");
	}
}
