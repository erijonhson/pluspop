package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Retorna o feed de um usuário
 * @author ordan
 *
 */
public class Feed {
	List<Post> feed;
	Comparator<Post> comparator;
	
	public Feed(){
		feed = new ArrayList<Post>();
		setComparatorPorTempo();
	}
	
	public void update(Set<Usuario> amigos){
		feed.clear();
		for (Usuario amigo : amigos){
			feed.addAll(amigo.compartilhar());

		}
		Collections.sort(feed, comparator);
	}

	public void setComparatorPorPopularidade(){
		comparator = new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return -(o1.getPopularidade() - o2.getPopularidade());
			}
		};
	}
	
	public void setComparatorPorTempo(){
		comparator = new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return o1.compareTempo(o2);
			}
		};
	}
	
	public List<Post> getFeed(){
		return this.feed;
	}
}
