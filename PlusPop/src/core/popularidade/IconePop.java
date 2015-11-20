package core.popularidade;

import core.Post;

public class IconePop implements ComportamentoSocial {

	private static final int DELTA = 50;
	private static final String EPICWIN = "#epicwin";
	private static final String EPICFAIL = "#epicfail";
	private static final int QTDPOSTSTOSHARE = 6;

	@Override
	public void curtir(Post post) {
		post.addPopularidade(DELTA);
		if (!post.getHashtags().contains("#epicwin")) {
			post.addHashTag(EPICWIN);
		}		
		post.setCurtidas(post.getCurtidas()+1);
	}

	@Override
	public void rejeitar(Post post) {
		post.removePopularidade(DELTA);
		if (!post.getHashtags().contains("#epicfail")) {
			post.addHashTag(EPICFAIL);
		}
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
