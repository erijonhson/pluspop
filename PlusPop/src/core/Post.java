package core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Representa um post de um usuario do PlusPop.
 * 
 * @author Laybson Plismenn
 */
public class Post {
	
	private List<String> conteudo;
	private List<String> hashtags;
	private int popularidade;
	private Date momento;

	public Post(List<String> conteudo, List<String> hashtags, Date momento) {		
		
		this.conteudo = conteudo;
		this.hashtags = hashtags;
		this.momento = momento;
		this.popularidade = 0;
		
	}
		
	public String getConteudo() {
		String conteudo = this.conteudo.get(0).trim();
		for (int i = 1; this.conteudo.size() > i; i++){
			conteudo = conteudo + " " + this.conteudo.get(i);
		}
		return conteudo.trim();
	}
	
	public String getHashtags() {
		String hashtags = this.hashtags.get(0);
		for (int i = 1; this.hashtags.size() > i; i++){
			hashtags = hashtags + "," + this.hashtags.get(i);
		}
		return hashtags.trim();
	}
	
	public String getMomento() {
		DateFormat formatadorDeData = new SimpleDateFormat("yyyy-dd-MM");
		return formatadorDeData.format(this.momento) + " " + this.momento.toString().substring(11, 19);
	}
	
	public int getPopularidade() {
		return popularidade;
	}
	
	public String toString() {
		String post = getConteudo();		
		for (int i = 0; this.hashtags.size() > i; i++){
			post = post + " " + this.hashtags.get(i).trim();
		}
		post = post + " (" + getMomento() + ")";
		return post;
	}
	
	public int getConteudoSize() {
		return this.conteudo.size();
	}

	public String getConteudoPost(int indice) {
		String conteudo = this.conteudo.get(indice).trim();
		if (conteudo.contains("<audio>")) {
			return "$arquivo_audio:" + conteudo.substring(7, conteudo.indexOf("</audio>"));
		} else if (conteudo.contains("<imagem>")){
			return "$arquivo_imagem:" + conteudo.substring(8, conteudo.indexOf("</imagem>"));
		} else {
			return conteudo;
		}
	}
}