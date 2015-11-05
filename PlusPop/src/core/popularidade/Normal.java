package core.popularidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Post;

public class Normal implements ComportamentoSocial {

	private static final int DELTA = 10;
	private static final int QTDPOSTSTOSHARE = 2;

	@Override
	public void curtir(Post post) {
		post.addPopularidade(DELTA);
		post.setCurtidas(post.getCurtidas()+1);
	}

	@Override
	public void rejeitar(Post post) {
		post.removePopularidade(DELTA);
		post.setRejeicoes(post.getRejeicoes()+1);
	}

	public List<Post> compartilhar(List<Post> posts){
		List<Post> recentes = new ArrayList<Post>();
		for (Post post : posts){
			if (recentes.size() < QTDPOSTSTOSHARE){
				recentes.add(post);
			} else{
				if (recentes.get(QTDPOSTSTOSHARE - 1).compareTempo(post) > 0){
					recentes.remove(QTDPOSTSTOSHARE - 1);
					recentes.add(post);
				}
			}
			Collections.sort(recentes, (pa, pb) -> pa.compareTempo(pb));
		}
		return recentes;
	}
	
	@Override
	public String toString() {
		return "Normal Pop";
	}
}
