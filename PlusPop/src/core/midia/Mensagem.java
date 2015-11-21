package core.midia;

public class Mensagem implements Midia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2104514875590799966L;
	private String texto;

	public Mensagem(String texto) {
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public String getInformacoesMidia() {
		return texto;
	}

	@Override
	public String getRepresentacaoMidia() {
		return texto;
	}
}
