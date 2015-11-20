package exception;

public class AtualizaPerfilException extends Exception {

	private static final long serialVersionUID = -5196481055821791933L;

	public AtualizaPerfilException(Exception e) {
		super("Erro na atualizacao de perfil. " + e.getMessage(), e.getCause());
	}

}
