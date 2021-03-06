package util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import core.Post;
import core.midia.Audio;
import core.midia.HashTag;
import core.midia.Imagem;
import core.midia.Mensagem;
import core.midia.Midia;
import exception.CriaPostException;
import exception.HashTagException;
import exception.TamanhoMensagemException;

/**
 * Construtor de Post válido. <br>
 * Faz todas as verificações necessárias e devolve objeto Post válido. <br>
 * Lança as exceçoes que, por ventura, ocorram.
 * 
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

	/**
	 * Construtor da Fabrica de Post.
	 */
	private FabricaDePost() {
	}

	
	public Post construirPost(String mensagem, String dataHora) 
			throws CriaPostException {
		try {

			this.indiceHashtag = encontrarIndiceHashtag(mensagem);
			Mensagem texto = recuperarMensagem(mensagem);
			List<Midia> midias = recuperarMidias(mensagem);
			midias.add(0, texto);
			List<HashTag> hashTags = recuperarHashtagsValidas(mensagem);
			return new Post(midias, hashTags, buildDate(dataHora), buildTime(dataHora));

		} catch (TamanhoMensagemException | HashTagException e) {
			throw new CriaPostException(e);
		}
	}
	
	/**
	 * Recebe uma string representado a data e hora e retorna um objeto {@link LocalDate} representando a data.
	 * @param String dataHora
	 * @return LocalDate data
	 */
	public LocalDate buildDate(String dataHora){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = LocalDate.parse(dataHora.substring(0, 10), formatter);
		return data;
	}
	
	/**
	 * Recebe uma string representado a data e hora e retorna um objeto {@link LocalDate} representando a hora.
	 * @param String dataHora
	 * @return LocalDate hora
	 */
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

	private Mensagem recuperarMensagem(String mensagem) 
			throws TamanhoMensagemException {
		String textoSemMidia = recuperarMensagemSemMidia(mensagem);
		if (textoSemMidia.length() > 200)
			throw new TamanhoMensagemException();
		return new Mensagem(textoSemMidia);
	}

	private List<Midia> recuperarMidias(String mensagem) {
		String[] textoMidias = mensagem.substring(this.indiceMidia, this.indiceHashtag).split(" ");
		List<Midia> midias = new ArrayList<>();
		Midia midia = null;
		for (String e : textoMidias) {
			if (e.startsWith("<audio>")) {
				int gap = "<audio>".length();
				midia = new Audio(e.substring(e.indexOf("<audio>") + gap, e.indexOf("</audio>")));
			}
			else if (e.startsWith("<imagem>")) {
				int gap = "<imagem>".length();
				midia = new Imagem(e.substring(e.indexOf("<imagem>") + gap, e.indexOf("</imagem>")));
			}
			if (midia != null)
				midias.add(midia);
		}
		return midias;
	}

	private List<HashTag> recuperarHashtagsValidas(String mensagem) throws HashTagException {
		List<HashTag> lista = new LinkedList<>();
		if (mensagem.contains("#")) {
			String[] tags = mensagem.substring(this.indiceHashtag).trim().split(" ");
			HashTag tag = null;
			for (String hashTag : tags) {
				tag = new HashTag(hashTag);
				lista.add(tag);
			}
		}
		return lista;
	}
	
	/**
	private List<String> construirHashTags(String[] hashTags){
		List<String> newHashTags = new ArrayList<String>();
		Collections.addAll(newHashTags, hashTags);
		return newHashTags;
	}
	*/
	
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
		return mensagem.substring(0, indiceMidia).trim();
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