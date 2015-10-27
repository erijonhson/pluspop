package core;

public class Normal implements Avaliador {

	private static final int DELTA = 10;

	@Override
	public void curtir(Post post) {
		post.addPopularidade(DELTA);
	}

	@Override
	public void rejeitar(Post post) {
		post.removePopularidade(DELTA);
	}

}
