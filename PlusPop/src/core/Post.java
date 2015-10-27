package core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
	private LocalDate data;
	private LocalTime hora;

	public Post(List<String> conteudo, List<String> hashtags, LocalDate data, LocalTime time) {		
		
		this.conteudo = conteudo;
		this.hashtags = hashtags;
		this.data = data;
		this.hora = time;
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		return data + " " + hora.format(formatter);
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
	
	public void addPopularidade (int popularidade){
		this.popularidade += popularidade;
	}
	
	public void removePopularidade (int popularidade){
		this.popularidade -= popularidade;
		if (this.popularidade < 0)
			this.popularidade = 0;
	}
	
	public boolean recente(){
		LocalDate hoje = LocalDate.now();
		return data.compareTo(hoje) == 0;
	}
	
	public void addHashTag(String hashtag){
		this.hashtags.add(hashtag);
	}
}