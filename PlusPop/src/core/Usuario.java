package core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

	public Usuario(String nome, String email, String senha, String dataNasc, String imagem)
			throws NomeUsuarioException, EmailInvalidoException,
			ConversaoDeDataException {
		if (nomeVazio(nome))
			throw new NomeUsuarioException();
		if (emailInvalido(email))
			throw new EmailInvalidoException();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNasc = ConversorDeData.getInstance().converterData(dataNasc);
		if (nomeVazio(imagem))
			this.imagem = imagemDefault;
		else
			this.imagem = imagem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
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

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
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

	private boolean nomeVazio(String nome) {
		return nome == null || nome.trim().equals("");
	}
}