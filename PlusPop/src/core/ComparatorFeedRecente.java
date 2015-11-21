package core;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Classe que compara o post pelo tempo de publicação
 * @author Ordan Santos
 *
 */
public class ComparatorFeedRecente implements Comparator<Post>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6912542368730763742L;

	@Override
	public int compare(Post o1, Post o2) {
		return o1.compareTempo(o2);
	}

}
