package core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import core.midia.Audio;
import core.midia.HashTag;
import core.midia.Imagem;
import core.midia.Midia;
import exception.HashTagException;

/**
 * Representa um post de um usuario do PlusPop.
 * 
 * @author Laybson Plismenn
 */
public class Post implements Texto{
	
	private List<Midia> conteudos;
	private List<HashTag> hashtags;
	private int popularidade;
	private LocalDate data;
	private LocalTime hora;
	private int curtidas;
	private int rejeicoes;
	
	/**
	 * Construtor do Post
	 * @param lista de conteudos
	 * @param lista de hashtags
	 * @param data do post
	 * @param hora do post
	 */
	public Post(List<Midia> conteudo, List<HashTag> hashtags, LocalDate data, LocalTime time) {		
		
		this.conteudos = conteudo;
		this.hashtags = hashtags;
		this.data = data;
		this.hora = time;
		this.popularidade = 0;
		this.curtidas = 0;
		this.rejeicoes = 0;
		
	}

	/**
	 * Retorna uma string com todo o conteudo referente ao post.
	 * @return string com todo o conteudo referente ao post.
	 */
	public String getConteudo() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.conteudos.get(0).getRepresentacaoMidia().trim());
		for (int i = 1; this.conteudos.size() > i; i++) {
			sb.append(" ");
			sb.append(this.conteudos.get(i).getRepresentacaoMidia());
		}
		return sb.toString().trim();
	}

	/**
	 * Retorna uma string com todas as hashtags do post separadas por espaço.
	 * @return string com todas as hashtags do post separadas por espaço
	 */
	public String getStringOfHashtags() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.hashtags.get(0));
		for (int i = 1; this.hashtags.size() > i; i++){
			sb.append(",");
			sb.append(this.hashtags.get(i));
		}
		return sb.toString().trim();
	}
	
	public List<HashTag> getHashtags(){
		return this.hashtags;
	}

	/**
	 * Retorna a data e hora de criação do post.
	 * @return String com data e hora de criação do post.
	 */
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
			sb.append(this.hashtags.get(i).getRepresentacaoMidia().trim());
		}
		sb.append(" (");
		sb.append(getMomento());
		sb.append(")");
		return sb.toString();
	}

	public int getConteudoSize() {
		return this.conteudos.size();
	}

	/**
	 * Retorna uma string com todos os áudios do post separados por espaço
	 * @return string com todos os áudios do post separados por espaço
	 */
	public String getAudios(){
		StringBuilder sb = new StringBuilder();
		for (Midia conteudo : conteudos) {
			if (conteudo instanceof Audio) {
				sb.append(conteudo.getRepresentacaoMidia());
			}
		}
		return sb.toString();
	}

	/**
	 * Retorna uma string com todas as imagens do post separadas por espaço
	 * @return string com todas as imagens do post separadas por espaço
	 */
	public String getImagens(){
		StringBuilder sb = new StringBuilder();
		for (Midia conteudo : conteudos) {
			if (conteudo instanceof Imagem) {
				sb.append(conteudo.getRepresentacaoMidia());
			}
		}
		return sb.toString();
	}

	/**
	 * Retorna o conteudo de indice dado da lista de conteudos
	 * @param indice do conteudo
	 * @return conteudo de indice dado
	 */
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

	/**
	 * Soma o numero dado de popularidade ao post
	 * @param numero de popularidade
	 */
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
	
	/**
	 * Subtrai a quantidade dada de popularidade no post
	 * @param quantidade de popularidade a ser subtraida
	 */
	public void removePopularidade (int popularidade){
		this.popularidade -= popularidade;
	}
	
	/**
	 * Retorna True se a data atual é no mesmo dia da data do post. Retorna False caso contrario
	 * @return
	 */
	public boolean recente(){
		LocalDate hoje = LocalDate.now();
		return data.compareTo(hoje) == 0;
	}
	
	/**
	 * Adiciona uma hashtag à lista de hashtags
	 * @param hashtag
	 * @throws HashTagException 
	 */
	public void addHashTag(String hashtag) throws HashTagException{
		HashTag novaHashTag = new HashTag(hashtag);
		if (!this.hashtags.contains(novaHashTag)) {
			this.hashtags.add(novaHashTag);
		}
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
	
	public String toTextFormat(){
		StringBuilder sb = new StringBuilder();
		String endl = System.getProperty("line.separator");
		String hora = this.getHora().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString();
		String data = this.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
		
		sb.append(data + " " + hora);
		sb.append(endl);
		sb.append("Conteudo:");
		sb.append(endl);
		sb.append(getConteudoPost(0));
		sb.append(endl);
		
		sb.append(getImagens());
		if (!getImagens().isEmpty())
			sb.append(endl);
		
		sb.append(getAudios());
		if (!getAudios().isEmpty())
			sb.append(endl);
		
		for (HashTag hashtag : hashtags){
			sb.append(hashtag.getRepresentacaoMidia());
			sb.append(" ");
		}
		
		if (hashtags.size() != 0)
			sb.append(endl);
		
		sb.append("+Pop: ");
		sb.append(getPopularidade());
		sb.append(endl);

		return sb.toString();
	}
		
}