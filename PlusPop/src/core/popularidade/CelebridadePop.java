package core.popularidade;

import core.Post;


public class CelebridadePop implements ComportamentoSocial{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7665350583841084664L;
	private static final int DELTA = 25;
	private static final int BONUS = 10;
	private static final int QTDPOSTSTOSHARE = 4;
	
	/**
	 * Adiciona ao post recebido 25 pops e mais 10, caso o post seja recente. Incrementa o numero de curtidas do post.
	 */
	@Override
	public void curtir(Post post) {
		post.addPopularidade(DELTA);
		post.setCurtidas(post.getCurtidas()+1);
		if (post.recente())
			post.addPopularidade(BONUS);		
	}

	/**
	 * Remove do post recebido 25 pops e mais 10, caso o post seja recente. Incrementa o numero de rejeições do post.
	 */
	@Override
	public void rejeitar(Post post) {
		post.removePopularidade(DELTA);
		post.setRejeicoes(post.getRejeicoes()+1);
		if (post.recente())
			post.removePopularidade(BONUS);		
	}

	@Override
	public int qtdParaCompartilhar() {
		return QTDPOSTSTOSHARE;
	}

	@Override
	public String toString() {
		return "Celebridade Pop";
	}

}
