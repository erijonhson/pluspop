package core.popularidade;

import core.Post;
import exception.HashTagException;

public class IconePop implements ComportamentoSocial {

	private static final int DELTA = 50;
	private static final String EPICWIN = "#epicwin";
	private static final String EPICFAIL = "#epicfail";
	private static final int QTDPOSTSTOSHARE = 6;

	/**
	 * Adiciona ao post recebido 50 pops, Incrementa o numero de curtidas do post, adiciona ao post a hashtag #epicwin.
	 * @throws HashTagException 
	 */
	@Override
	public void curtir(Post post) throws HashTagException {
		post.addPopularidade(DELTA);
		post.addHashTag(EPICWIN);
		post.setCurtidas(post.getCurtidas()+1);
	}

	/**
	 * Remove do post recebido 50 pops. Incrementa o numero de rejeições do post. Adiciona a hashtag #epicfail ao post.
	 * @throws HashTagException 
	 */
	@Override
	public void rejeitar(Post post) throws HashTagException {
		post.removePopularidade(DELTA);
		post.addHashTag(EPICFAIL);
		post.setRejeicoes(post.getRejeicoes()+1);
	}
	
	public int qtdParaCompartilhar(){
		return QTDPOSTSTOSHARE;
	}
	
	@Override
	public String toString() {
		return "Icone Pop";
	}

}
