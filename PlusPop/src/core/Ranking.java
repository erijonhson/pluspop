package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Classe que fornece o ranking dos usuários e das hashtags
 * @author ordan
 *
 */
public class Ranking {
	
	private List<Usuario> usuariosMaisPopulares;
	private List<Usuario> usuariosMenosPopulares;
	private List<String> hashtags;
	private HashMap<String, Integer> frequenciaHashtag;
	
	public Ranking (){
		usuariosMaisPopulares = new ArrayList<Usuario>();
		usuariosMenosPopulares = new ArrayList<Usuario>();
		hashtags = new ArrayList<String>();
		frequenciaHashtag = new HashMap<String, Integer>();
	}
	
	public void atualizaRank(List<Usuario> usuarios){
		this.frequenciaHashtag.clear();
		this.hashtags.clear();
		this.usuariosMaisPopulares.clear();
		this.usuariosMenosPopulares.clear();
		for (Usuario usuario : usuarios){
			atualizaRankUsuarioMaisPopulares(usuario);
			atualizaRankUsuarioMenosPopulares(usuario);
			for (Post post : usuario.getPosts()){
				for (String hashtag : post.getHashtags()){
					atualizaHashtag(hashtag);
				}
			}
		}
		
	}
	
	private void atualizaRankUsuarioMaisPopulares(Usuario usuario){
		if (usuariosMaisPopulares.size() < 3){
			usuariosMaisPopulares.add(usuario);
		} else{
			if (usuario.compareTo(usuariosMaisPopulares.get(2)) < 0){
				usuariosMaisPopulares.remove(2);
				usuariosMaisPopulares.add(usuario);
			}
		}
		Collections.sort(usuariosMaisPopulares);
	}
	
	private void atualizaRankUsuarioMenosPopulares(Usuario usuario){
		if (usuariosMenosPopulares.size() < 3){
			usuariosMenosPopulares.add(usuario);
		} else{
			if (usuario.compareTo(usuariosMenosPopulares.get(2)) > 0){
				usuariosMenosPopulares.remove(2);
				usuariosMenosPopulares.add(usuario);
			}
		}
		Collections.sort(usuariosMenosPopulares, Collections.reverseOrder());
	}
	
	
	private void atualizaHashtag(String hashtag){
		
		if (!frequenciaHashtag.containsKey(hashtag))
			frequenciaHashtag.put(hashtag, new Integer(1));
		else
			frequenciaHashtag.put(hashtag, new Integer (frequenciaHashtag.get(hashtag).intValue() + 1));
		
		if (!hashtags.contains(hashtag)){
			if (hashtags.size() < 3){
				hashtags.add(hashtag);
			} else{
				if (compareHashTag(hashtag, hashtags.get(2)) < 0){
					hashtags.remove(2);
					hashtags.add(hashtag);
				}
			}
		}
		
		Collections.sort(hashtags, (h1, h2) -> compareHashTag(h1, h2));
	}
	
	private int compareHashTag(String o1, String o2){
		
		if (frequenciaHashtag.get(o1).equals(frequenciaHashtag.get(o2)) ){
			
			return -(o1.compareTo(o2));
		} else{
			return -(frequenciaHashtag.get(o1) - frequenciaHashtag.get(o2));
		}
	}
	
	public String getRankUsuario(){
		String rank = "Mais Populares: ";
		for (int i = 0; i < this.usuariosMaisPopulares.size(); i++){
			if (i != 0) rank += "; ";
			rank += "("+(i+1)+") ";
			rank += this.usuariosMaisPopulares.get(i).getNome()+" "+this.usuariosMaisPopulares.get(i).getPopularidade();
		}
		rank += "; | Menos Populares: ";
		for (int i = 0; i < this.usuariosMenosPopulares.size(); i++){
			if (i != 0) rank += "; ";
			rank += "("+(i+1)+") ";
			rank += this.usuariosMenosPopulares.get(i).getNome()+" "+this.usuariosMenosPopulares.get(i).getPopularidade();
		}
		rank += ";";
		return rank;
	}
	
	public String getRankHashtag(){
		String rank = "Trending Topics:  ";
		for (int i = 0; i < this.hashtags.size(); i++){			
			if (i != 0) rank += "; ";
			rank += "("+(i+1)+") ";
			rank += this.hashtags.get(i)+": "+frequenciaHashtag.get(this.hashtags.get(i));
		}
		rank += ";";
		return rank;
	}
}
