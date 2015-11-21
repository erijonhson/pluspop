package core;

import java.io.Serializable;
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
public class Feed implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8706335338312130931L;
	List <Post> feed;
	Comparator<Post> comparator;
	
	public Feed(Comparator<Post> comparator){
		feed = new ArrayList<Post>();
		this.comparator = comparator;
	}
	
	/**
	 * Atualiza o feed
	 * @param amigos que compartilharão os posts
	 */
	public void update(Set<Usuario> amigos){
		feed.clear();
		for (Usuario amigo : amigos){
			feed.addAll(amigo.compartilhar());
		}
		Collections.sort(feed, comparator);
	}
	
	public Post getPost (int idx){
		return this.feed.get(idx);
	}

}
