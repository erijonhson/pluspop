package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import core.Texto;

/**
 * Classe que salva uma sequência de objetos como um texto em um arquivo
 * @author ordan
 *
 */
public class TextoWritter {
	
	private static final String DIRETORIO = "./arquivos";
	private static final String EXTENSAO = ".txt";
	private File file;
	
	public TextoWritter(String nomeDoArquivo){
		this.file = new File (DIRETORIO, nomeDoArquivo + EXTENSAO);
	}
	
	/**
	 * Escreve um texto em um arquivo
	 * @param texto a ser escrito
	 * @throws FileNotFoundException
	 */
	private void write(String texto, PrintWriter pw) throws FileNotFoundException{
		pw.print(texto);
	}

	/**
	 * Escreve uma sequência de textos em um arquivo
	 * @param textos objetos do tipo Texto
	 * @see Covariance
	 * @param nomeDoArquivo nome do arquivo que conterá o texto
	 * @throws FileNotFoundException
	 */
	public void write(List<? extends Texto> textos, String titulo) throws FileNotFoundException{
		
		String endl = System.getProperty("line.separator");
		
		// autoclose
		try (PrintWriter pw = new PrintWriter(this.file)){
			for (int i = 0; i < textos.size(); i++){
				if (i != 0)
					pw.print(endl + endl);
				pw.print(titulo + " #" + (i + 1) + " - ");
				write (textos.get(i).toTextFormat(), pw);
			}
		}
		
	}
	
}
