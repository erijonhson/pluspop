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
	
	private List<Usuario> usuarios;
	private List<String> hashtags;
	private HashMap<String, Integer> frequenciaHashtag;
	
	public Ranking (){
		usuarios = new ArrayList<Usuario>();
		hashtags = new ArrayList<String>();
		frequenciaHashtag = new HashMap<String, Integer>();
	}
	
	public void atualizaRank(List<Usuario> usuarios){
		this.frequenciaHashtag.clear();
		this.hashtags.clear();
		this.usuarios.clear();
		for (Usuario usuario : usuarios){
			atualizaRankUsuario(usuario);
			for (Post post : usuario.getPosts()){
				for (String hashtag : post.getHashtags()){
					atualizaHashtag(hashtag);
				}
			}
		}
		
	}
	
	private void atualizaRankUsuario(Usuario usuario){
		if (usuarios.size() < 3){
			usuarios.add(usuario);
		} else{
			if (usuario.getPopularidade() > usuarios.get(2).getPopularidade()){
				usuarios.remove(2);
				usuarios.add(usuario);
			}
		}
		Collections.sort(usuarios, (u1, u2) -> -(u1.getPopularidade() - u2.getPopularidade()));
	}
	
	private void atualizaHashtag(String hashtag){
		if (!frequenciaHashtag.containsKey(hashtag))
			frequenciaHashtag.put(hashtag, new Integer(0));
		else
			frequenciaHashtag.put(hashtag, new Integer (frequenciaHashtag.get(hashtag).intValue() + 1));
		if (!hashtags.contains(hashtag)){
			if (hashtags.size() < 3){
				hashtags.add(hashtag);
			} else{
				if (frequenciaHashtag.get(hashtag) > frequenciaHashtag.get(hashtags.get(2))){
					hashtags.remove(2);
					hashtags.add(hashtag);
				}
			}
		}
		
		Collections.sort(hashtags, (h1, h2) -> -(frequenciaHashtag.get(h1) - frequenciaHashtag.get(h2)));
	}
	
	public String getRankUsuario(){
		String rank = "Mais Populares: ";
		for (int i = 0; i < this.usuarios.size(); i++){
			if (i != 0) rank += "; ";
			rank += "("+(i+1)+") ";
			rank += this.usuarios.get(i).getNome()+" "+this.usuarios.get(i).getPopularidade();
		}
		rank += "; | Menos Populares: ";
		return rank;
	}
	
	public String getRankHashtag(){
		String rank = "";
		for (int i = 0; i < this.hashtags.size(); i++){			
			if (i != 0) rank += "; ";
			rank += "("+(i+1)+") ";
			rank += this.hashtags.get(i)+": "+frequenciaHashtag.get(this.hashtags.get(i));
		}
		rank += ";";
		return rank;
	}
}
