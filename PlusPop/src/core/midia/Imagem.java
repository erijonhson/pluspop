package core.midia;

public class Imagem implements Midia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6254981899471643558L;
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
