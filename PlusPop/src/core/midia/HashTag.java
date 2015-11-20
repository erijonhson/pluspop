package core.midia;

import exception.HashTagException;

public class HashTag implements Midia {

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

}
