package core.popularidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Post;


public class CelebridadePop implements ComportamentoSocial{
	
	private static final int DELTA = 25;
	private static final int BONUS = 10;
	private static final int QTDPOSTSTOSHARE = 4;
	
	@Override
	public void curtir(Post post) {
		post.addPopularidade(DELTA);
		post.setCurtidas(post.getCurtidas()+1);
		if (post.recente())
			post.addPopularidade(BONUS);		
	}

	@Override
	public void rejeitar(Post post) {
		post.removePopularidade(DELTA);
		post.setRejeicoes(post.getRejeicoes()+1);
		if (post.recente())
			post.removePopularidade(BONUS);		
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
		return "Celebridade Pop";
	}

}
