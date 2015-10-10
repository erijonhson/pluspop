package util;

import java.util.ArrayList;
import java.util.Date;
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
	
	public Post construirPost(String mensagem, Date data) 
			throws CriaPostException {
		try {
			this.indiceHashtag = encontrarIndiceHashtag(mensagem);
			String texto = recuperarTextoValido(mensagem);
			String[] midias = recuperarMidias(mensagem);
			String[] hashTags = recuperarHashtagsValidas(mensagem);
			return new Post(construirConteudo(texto, midias), construirHashTags(hashTags), data);
		} catch (TamanhoMensagemException | HashTagException e) {
			throw new CriaPostException(e);
		}		
	
	}

	private String[] recuperarHashtagsValidas(String mensagem) 
			throws HashTagException {
		String[] hashTags = mensagem.substring(mensagem.indexOf("#")).split(" ");
		for (String hashTag : hashTags)
			if (hashTag.trim().equals("") || hashTag.charAt(0) != '#')
				throw new HashTagException(hashTag);
		return hashTags;
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
	

	private String recuperarMensagemSemMidia(String mensagem) {
		int primeiroAudio = mensagem.indexOf("<audio>");
		int primeiraImagem = mensagem.indexOf("<imagem>");
		if (existeTodasAsMidias(primeiroAudio, primeiraImagem)) {
			this.indiceMidia = Integer.min(primeiroAudio, primeiraImagem);
			return mensagem.substring(0, this.indiceMidia);
		} else if (existeApenasAudio(primeiroAudio, primeiraImagem)) {
			this.indiceMidia = primeiroAudio;
			return mensagem.substring(0, indiceMidia);
		} else if (existeApenasImagem(primeiroAudio, primeiraImagem)) {
			this.indiceMidia = primeiraImagem;
			return mensagem.substring(0, indiceMidia);
		} else
			return recuperarMensagemExcetoHashTags(mensagem);
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

	private String recuperarMensagemExcetoHashTags(String mensagem) {
		this.indiceMidia = this.indiceHashtag;
		return mensagem.substring(0, this.indiceMidia);
	}
		
	private int encontrarIndiceHashtag(String mensagem) {
		if (mensagem.contains("#"))		
			return mensagem.indexOf("#");
		else
			return mensagem.length();
	}
	
	private List<String> construirConteudo(String mensagem, String[] midias){
		List<String> conteudo = new ArrayList<String>();
		conteudo.add(mensagem);
		for (String midia: midias){
			conteudo.add(midia);
		}
		return conteudo;
	}
	
	private List<String> construirHashTags(String[] hashTags){
		List<String> newHashTags = new ArrayList<String>();
		for (String hashTag: hashTags){
			newHashTags.add(hashTag);
		}
		return newHashTags;
	}

	public static void main(String[] args) {
		String mensagem = "O Encontro de amanha estara otimo. "
				+ "Vamos falar sobre os problemas do preconceito na escola. "
				+ "<imagem>imagens/encontro_vinheta.jpg</imagem> "
				+ "<imagem>imagens/encontro_preview.jpg</imagem>";
		
	}

}
