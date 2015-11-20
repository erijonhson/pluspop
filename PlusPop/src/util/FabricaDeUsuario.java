package util;

import java.time.LocalDate;

import core.Usuario;
import exception.ConversaoDeDataException;
import exception.EmailInvalidoException;
import exception.NomeUsuarioException;

/**
 * Construtor de Usuário válido. <br>
 * Faz todas as verificações necessárias e devolve objeto Usuario válido. <br>
 * Lança as exceçoes que, por ventura, ocorram.
 * 
 * @author Eri Jonhson
 * @author Laybson Plismenn
 * @author Ordan Santos
 */
public class FabricaDeUsuario {

	private static FabricaDeUsuario instance;

	public static synchronized FabricaDeUsuario getInstance() {
		if (instance == null)
			instance = new FabricaDeUsuario();
		return instance;
	}

	private static final String imagemDefault = "resources/default.jpg";

	private FabricaDeUsuario() {
	}

	public Usuario construirUsuario(String nome, String email, String senha,
			String dataNasc, String imagem) throws NomeUsuarioException,
			EmailInvalidoException, ConversaoDeDataException {
		verificaNome(nome);
		verificaEmail(email);
		LocalDate dataNascValida = ConversorDeData.getInstance().
				converterData(dataNasc);
		String imagemValida = imagemValida(imagem);
		return new Usuario(nome, email, senha, dataNascValida, imagemValida);
	}

	private void verificaNome(String nome) throws NomeUsuarioException {
		if (stringVazia(nome))
			throw new NomeUsuarioException();
	}

	private void verificaEmail(String email) throws EmailInvalidoException {
		if (emailInvalido(email))
			throw new EmailInvalidoException();
	}

	private boolean emailInvalido(String email) {
		return !EmailValidator.getInstance().validateEmail(email);
	}

	private String imagemValida(String imagem) {
		if (stringVazia(imagem))
			return imagemDefault;
		else
			return imagem;
	}

	private boolean stringVazia(String s) {
		return s == null || s.trim().equals("");
	}
}
