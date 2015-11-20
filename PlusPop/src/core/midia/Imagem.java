package core.midia;

public class Imagem implements Midia {

	private String local;

	public Imagem(String local) {
		this.local = local;
	}

	@Override
	public String getInformacoesMidia() {
		return local;
	}

	public String getRepresentacaoMidia() {
		return "<imagem>" + local + "</imagem>";
	}

}
