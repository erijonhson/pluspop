package core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import util.ConversorDeData;
import util.EmailValidator;
import exception.ConversaoDeDataException;
import exception.EmailInvalidoException;
import exception.NomeUsuarioException;

/**
 * Usuario comum do PlusPop.
 * 
 * @author Eri Jonhson
 */
public class Usuario implements Serializable {

	private static final long serialVersionUID = -8892086465318181235L;
	private static final String imagemDefault = "resources/default.jpg";
	
	private String nome;
	private String email;
	private String senha;
	private Date dataNasc;
	private String imagem;
	private ArrayList<Post> mural;

	public Usuario(String nome, String email, String senha, String dataNasc, String imagem)
			throws NomeUsuarioException, EmailInvalidoException,
			ConversaoDeDataException {
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setDataNasc(dataNasc);
		setImagem(imagem);
		this.mural = new ArrayList();
	}
	
	public void addPost(Post post){
		mural.add(post);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws NomeUsuarioException {
		if (stringVazia(nome))
			throw new NomeUsuarioException();
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws EmailInvalidoException {
		if (emailInvalido(email))
			throw new EmailInvalidoException();
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	/**
	 * Recuperar string representativa da data no formato yyyy-MM-dd. <br>
	 * Sobre formatar data:
	 * http://stackoverflow.com/questions/22294235/how-to-convert
	 * -date-in-to-string-mm-dd-yyyy-format
	 * 
	 * @return data no formato yyyy-MM-dd.
	 */
	public String getDataNascFormatada() {
		DateFormat formatadorDeData = new SimpleDateFormat("yyyy-MM-dd");
		return formatadorDeData.format(dataNasc);
	}

	public void setDataNasc(String dataNasc) throws ConversaoDeDataException {
		this.dataNasc = ConversorDeData.getInstance().converterData(dataNasc);
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		if (stringVazia(imagem)) {
			if (stringVazia(this.imagem))
				this.imagem = imagemDefault;
		}
		else
			this.imagem = imagem;
	}
	
	public ArrayList<Post> getMural() {
		return mural;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return email;
	}

	/*
	 * Métodos internos.
	 */

	private boolean emailInvalido(String email) throws EmailInvalidoException {
		return !EmailValidator.getInstance().validateEmail(email);
	}

	private boolean stringVazia(String s) {
		return s == null || s.trim().equals("");
	}
}
