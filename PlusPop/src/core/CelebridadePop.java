package core;


public class CelebridadePop implements Avaliador{
	
	private static final int DELTA = 25;
	private static final int BONUS = 10;
	
	@Override
	public void curtir(Post post) {
		post.addPopularidade(DELTA);
		if (post.recente())
			post.addPopularidade(BONUS);
	}

	@Override
	public void rejeitar(Post post) {
		post.removePopularidade(DELTA);
		if (post.recente())
			post.addPopularidade(BONUS);
	}

}
