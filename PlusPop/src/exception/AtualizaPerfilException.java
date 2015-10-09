package exception;

public class AtualizaPerfilException extends Exception {

	private static final long serialVersionUID = -5196481055821791933L;

	public AtualizaPerfilException(Exception e) {
		super(getMessagem(e) + e.getMessage());
	}

	private static String getMessagem(Exception e) {
		if (e.getClass().equals(UsuarioNaoLogadoException.class))
			return "Nao eh possivel atualizar um perfil. ";
		else
			return "Erro na atualizacao de perfil. ";
	}
}
