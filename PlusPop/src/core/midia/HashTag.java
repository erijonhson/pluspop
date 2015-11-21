package core.midia;

import exception.HashTagException;

public class HashTag implements Midia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3023336608940954875L;
	private String texto;

	public HashTag(String texto) throws HashTagException {
		if (texto.trim().equals("") || !texto.startsWith("#"))
			throw new HashTagException(texto);
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
	
	@Override
	public String toString() {
		return texto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof HashTag))
			return false;
		HashTag other = (HashTag) obj;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}

}
