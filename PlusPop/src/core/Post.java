package core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import core.midia.Audio;
import core.midia.Imagem;
import core.midia.Midia;

/**
 * Representa um post de um usuario do PlusPop.
 * 
 * @author Laybson Plismenn
 */
public class Post {
	
	private List<Midia> conteudos;
	private List<String> hashtags;
	private int popularidade;
	private LocalDate data;
	private LocalTime hora;
	private int curtidas;
	private int rejeicoes;
	
	public Post(List<Midia> conteudo, List<String> hashtags, LocalDate data, LocalTime time) {		
		
		this.conteudos = conteudo;
		this.hashtags = hashtags;
		this.data = data;
		this.hora = time;
		this.popularidade = 0;
		this.curtidas = 0;
		this.rejeicoes = 0;
		
	}

	public String getConteudo() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.conteudos.get(0).getRepresentacaoMidia().trim());
		for (int i = 1; this.conteudos.size() > i; i++) {
			sb.append(" ");
			sb.append(this.conteudos.get(i).getRepresentacaoMidia());
		}
		return sb.toString().trim();
	}

	public String getStringOfHashtags() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.hashtags.get(0));
		for (int i = 1; this.hashtags.size() > i; i++){
			sb.append(",");
			sb.append(this.hashtags.get(i));
		}
		return sb.toString().trim();
	}
	
	public List<String> getHashtags(){
		return this.hashtags;
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
		return this.conteudos.size();
	}
	
	public String getAudios(){
		StringBuilder sb = new StringBuilder();
		for (Midia conteudo : conteudos) {
			if (conteudo instanceof Audio) {
				sb.append(conteudo.getRepresentacaoMidia());
			}
		}
		return sb.toString();
	}

	public String getImagens(){
		StringBuilder sb = new StringBuilder();
		for (Midia conteudo : conteudos) {
			if (conteudo instanceof Imagem) {
				sb.append(conteudo.getRepresentacaoMidia());
			}
		}
		return sb.toString();
	}

	public String getConteudoPost(int indice) {
		Midia conteudo = this.conteudos.get(indice);
		if (conteudo instanceof Audio) {
			return "$arquivo_audio:" + conteudo.getInformacoesMidia();
		} else if (conteudo instanceof Imagem) {
			return "$arquivo_imagem:" + conteudo.getInformacoesMidia();
		} else {
			return conteudo.getRepresentacaoMidia();
		}
	}
	
	public void addPopularidade (int popularidade){
		this.popularidade += popularidade;
	}
	
	public int getCurtidas() {
		return curtidas;
	}
	
	public int getRejeicoes() {
		return rejeicoes;
	}
	
	public void setCurtidas(int curtidas) {
		this.curtidas = curtidas;
	}
	
	public void setRejeicoes(int rejeicoes) {
		this.rejeicoes = rejeicoes;
	}
	
	public void removePopularidade (int popularidade){
		this.popularidade -= popularidade;
	}
	
	public boolean recente(){
		LocalDate hoje = LocalDate.now();
		return data.compareTo(hoje) == 0;
	}
	
	public void addHashTag(String hashtag){
		this.hashtags.add(hashtag);
	}
	
	public LocalDate getData(){
		return data;
	}
	
	public LocalTime getHora(){
		return hora;
	}
	
	public int compareTempo(Post other) {
		if (data.compareTo(other.getData()) != 0)
			return data.compareTo(other.getData());
		return hora.compareTo(other.getHora());
	}
	
	public String toFileFormat(){
		String out = "";
		String endl = System.getProperty("line.separator");
		out += "Conteúdo:" + endl + getConteudo() + endl + getImagens() + endl + getAudios() + endl;
		for (String hashtag : hashtags){
			out += hashtag + " ";
		}
		out += "+Pop: " + getPopularidade() + endl;
		return out;
	}
		
}