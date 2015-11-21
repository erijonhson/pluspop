package core.popularidade;

import core.Post;

public class Normal implements ComportamentoSocial {

	private static final int DELTA = 10;
	private static final int QTDPOSTSTOSHARE = 2;

	/**
	 * Adiciona ao post recebido 10 pops. Incrementa o numero de curtidas do post.
	 */
	@Override
	public void curtir(Post post) {
		post.addPopularidade(DELTA);
		post.setCurtidas(post.getCurtidas()+1);
	}

	/**
	 * Remove do post recebido 10 pops. Incrementa o numero de rejeições do post.
	 */
	@Override
	public void rejeitar(Post post) {
		post.removePopularidade(DELTA);
		post.setRejeicoes(post.getRejeicoes()+1);
	}

	@Override
	public int qtdParaCompartilhar() {
		return QTDPOSTSTOSHARE;
	}

	@Override
	public String toString() {
		return "Normal Pop";
	}
}
