package exception;

public class SolicitacaoNaoEnviadaException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5849419339500367064L;
	
	public SolicitacaoNaoEnviadaException(String nome) {
		super (nome + " nao lhe enviou solicitacoes de amizade.");
	}
}
