package core;

import java.util.Comparator;

/**
 * Classe que compara o post pelo tempo de publicação
 * @author Ordan Santos
 *
 */
public class ComparatorFeedRecente implements Comparator<Post>{

	@Override
	public int compare(Post o1, Post o2) {
		return o1.compareTempo(o2);
	}

}
