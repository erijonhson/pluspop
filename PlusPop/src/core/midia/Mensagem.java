package core.midia;

public class Mensagem implements Midia {

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
