package core;

import java.util.Comparator;

/**
 * Classe que compara o Post por popularidade, caso empate, o tempo de publicação prevalece
 * @author Ordan Santos
 *
 */
public class ComparatorFeedPopular implements Comparator<Post> {

	@Override
	public int compare(Post o1, Post o2) {
		
		if (o1.getPopularidade() == o2.getPopularidade())	
			return o1.compareTempo(o2);
		
		return o1.getPopularidade() - o2.getPopularidade();
	}
	
}
