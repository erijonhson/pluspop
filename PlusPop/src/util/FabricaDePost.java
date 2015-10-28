package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.Post;
import exception.CriaPostException;
import exception.HashTagException;
import exception.TamanhoMensagemException;

/**
 * Construtor de Post válido. <br>
 * Faz todas as verificações necessárias e devolve objeto Post válido para usuario. <br>
 * Lança as exceçoes que, por ventura, ocorram.
 * @author Eri Jonhson
 * @author Laybson Plismenn
 * @author Ordan Santos
 */
public class FabricaDePost {

	private static FabricaDePost instance;

	public static synchronized FabricaDePost getInstance() {
		if (instance == null)
			instance = new FabricaDePost();
		return instance;
	}

	private int indiceMidia;
	private int indiceHashtag;

	private FabricaDePost() {
	}

	public Post construirPost(String mensagem, String dataHora) 
			throws CriaPostException {
		try {
			this.indiceHashtag = encontrarIndiceHashtag(mensagem);
			String texto = recuperarTextoValido(mensagem);
			String[] midias = recuperarMidias(mensagem);
			String[] hashTags = recuperarHashtagsValidas(mensagem);
			return new Post(construirConteudo(texto, midias), construirHashTags(hashTags), buildDate(dataHora), buildTime(dataHora));
		} catch (TamanhoMensagemException | HashTagException e) {
			throw new CriaPostException(e);
		}
	}
	
	public LocalDate buildDate(String dataHora){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = LocalDate.parse(dataHora.substring(0, 10), formatter);
		return data;
	}
	
	public LocalTime buildTime(String dataHora){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime time = LocalTime.parse (dataHora.substring(11, 19), formatter);
		return time;
	}

	/*
	 * Refatoração
	 */

	private int encontrarIndiceHashtag(String mensagem) {
		if (mensagem.contains("#"))
			return mensagem.indexOf("#");
		else
			return mensagem.length();
	}

	private String recuperarTextoValido(String mensagem) 
			throws TamanhoMensagemException {
		String textoSemMidia = recuperarMensagemSemMidia(mensagem);
		if (textoSemMidia.length() > 200)
			throw new TamanhoMensagemException();
		return textoSemMidia;
	}

	private String[] recuperarMidias(String mensagem) {
		return mensagem.substring(this.indiceMidia, this.indiceHashtag).split(" ");
	}

	private String[] recuperarHashtagsValidas(String mensagem) 
			throws HashTagException {
		String[] hashTags = mensagem.substring(this.indiceHashtag).split(" ");
		for (String hashTag : hashTags)
			if (hashTag.trim().equals("") || !hashTag.startsWith("#"))
				throw new HashTagException(hashTag);
		return hashTags;
	}

	private List<String> construirConteudo(String mensagem, String[] midias){
		List<String> conteudo = new ArrayList<String>();
		conteudo.add(mensagem);
		Collections.addAll(conteudo, midias);
		return conteudo;
	}

	private List<String> construirHashTags(String[] hashTags){
		List<String> newHashTags = new ArrayList<String>();
		Collections.addAll(newHashTags, hashTags);
		return newHashTags;
	}

	private String recuperarMensagemSemMidia(String mensagem) {
		int primeiroAudio = mensagem.indexOf("<audio>");
		int primeiraImagem = mensagem.indexOf("<imagem>");
		if (existeTodasAsMidias(primeiroAudio, primeiraImagem))
			this.indiceMidia = Integer.min(primeiroAudio, primeiraImagem);
		else if (existeApenasAudio(primeiroAudio, primeiraImagem))
			this.indiceMidia = primeiroAudio;
		else if (existeApenasImagem(primeiroAudio, primeiraImagem))
			this.indiceMidia = primeiraImagem;
		else
			this.indiceMidia = this.indiceHashtag;
		return mensagem.substring(0, indiceMidia);
	}

	private boolean existeApenasAudio(int primeiroAudio, int primeiraImagem) {
		return primeiroAudio != -1 && primeiraImagem == -1;
	}

	private boolean existeApenasImagem(int primeiroAudio, int primeiraImagem) {
		return primeiroAudio == -1 && primeiraImagem != -1;
	}

	private boolean existeTodasAsMidias(int primeiroAudio, int primeiraImagem) {
		return primeiroAudio != -1 && primeiraImagem != -1;
	}

}