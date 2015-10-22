package core;

public enum TipoDePopularidade {
	Normal(10), CelebridadePOP(25), IconePOP(50);

	private int fatorMultiplicativo;

	private TipoDePopularidade(int fatorMultiplicativo) {
		this.fatorMultiplicativo = fatorMultiplicativo;
	}

	public int getFatorMultiplicativo() {
		return fatorMultiplicativo;
	}
}
