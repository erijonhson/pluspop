package core.popularidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Post;

public class IconePop implements ComportamentoSocial {

	private static final int DELTA = 50;
	private static final String EPICWIN = "#epicwin";
	private static final String EPICFAIL = "#epicfail";
	private static final int QTDPOSTSTOSHARE = 6;

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

}
