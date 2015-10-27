package core;

public class IconePop implements Avaliador{
	
	private static final int DELTA = 50;
	private static final String EPICWIN = "#epicwin";
	private static final String EPICFAIL = "#epicfail";
	
	@Override
	public void curtir(Post post) {
		post.addPopularidade(DELTA);
		post.addHashTag(EPICWIN);
	}

	@Override
	public void rejeitar(Post post) {
		post.removePopularidade(DELTA);
		post.addHashTag(EPICFAIL);
		
	}
	
}
