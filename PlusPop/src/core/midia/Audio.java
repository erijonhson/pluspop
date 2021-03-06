package core.midia;

public class Audio implements Midia {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7736392645174562597L;
	private String local;

	public Audio(String local) {
		this.local = local;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@Override
	public String getInformacoesMidia() {
		return local;
	}

	public String getRepresentacaoMidia() {
		return "<audio>" + local + "</audio>";
	}

}
