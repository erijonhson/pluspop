package exception;

public class AtualizaPerfilException extends Exception {

	private static final long serialVersionUID = -5196481055821791933L;

	public AtualizaPerfilException(Exception e) {
		super(getMessagem(e) + e.getMessage());
	}

	private static String getMessagem(Exception e) {
		return "Erro na atualizacao de perfil. ";
	}
}
