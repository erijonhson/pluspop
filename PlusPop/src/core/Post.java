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
		StringBuilder sb = new StringBuilder();
		sb.append(this.conteudo.get(0).trim());
		for (int i = 1; this.conteudo.size() > i; i++){
			sb.append(" ");
			sb.append(this.conteudo.get(i));
		}
		return sb.toString().trim();
	}

	public String getHashtags() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.hashtags.get(0));
		for (int i = 1; this.hashtags.size() > i; i++){
			sb.append(",");
			sb.append(this.hashtags.get(i));
		}
		return sb.toString().trim();
	}

	public String getMomento() {
		DateFormat formatadorDeData = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
		return formatadorDeData.format(this.momento);
	}

	public int getPopularidade() {
		return popularidade;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getConteudo());
		for (int i = 0; this.hashtags.size() > i; i++) {
			sb.append(" ");
			sb.append(this.hashtags.get(i).trim());
		}
		sb.append(" (");
		sb.append(getMomento());
		sb.append(")");
		return sb.toString();
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