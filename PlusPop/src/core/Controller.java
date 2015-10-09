package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import exception.AtualizaPerfilException;
import exception.CadastraUsuarioException;
import exception.ConversaoDeDataException;
import exception.EmailInvalidoException;
import exception.FechaSistemaException;
import exception.LoginException;
import exception.LogoutException;
import exception.NomeUsuarioException;
import exception.SenhaInvalidaException;
import exception.SenhaProtegidaException;
import exception.UsuarioJaExisteException;
import exception.UsuarioJaLogadoException;
import exception.UsuarioNaoExisteException;
import exception.UsuarioNaoLogadoException;

public class Controller {

	private List<Usuario> usuariosDoSistema;
	private Usuario usuarioDaSessao;

	public Controller() {
		usuariosDoSistema = new ArrayList<Usuario>();
		retirarUsuarioDaSessao();
	}

	public String cadastraUsuario(String nome, String email, String senha,
			String dataNasc, String imagem) throws CadastraUsuarioException {
		try {
			verificarSeUsuarioExiste(email);
			Usuario novoUsuario = new Usuario(nome, email, senha, dataNasc, imagem);
			usuariosDoSistema.add(novoUsuario);
			return novoUsuario.getEmail();
		} catch (ConversaoDeDataException | UsuarioJaExisteException | 
				NomeUsuarioException | EmailInvalidoException e) {
			throw new CadastraUsuarioException(e);
		}
	}

	public String getInfoUsuario(String atributo, String email) 
			throws SenhaProtegidaException, UsuarioNaoExisteException {
		Usuario usuario = recuperarUsuario(email);
		switch (atributo.toUpperCase()) {
			case "NOME":
				return usuario.getNome();
			case "DATA DE NASCIMENTO":
				return usuario.getDataNascFormatada();
			case "SENHA":
				throw new SenhaProtegidaException();
			case "FOTO":
				return usuario.getImagem();
			default:
				return "Atributo invalido.";
		}
	}

	public void login(String email, String senha) throws LoginException {
		try {
			if (temUsuarioLogado())
				throw new UsuarioJaLogadoException(usuarioDaSessao.getNome());
			usuarioDaSessao = recuperarUsuario(email);
			if (!senhaCorreta(senha)) {
				retirarUsuarioDaSessao();
				throw new SenhaInvalidaException();
			}
		} catch (UsuarioNaoExisteException | SenhaInvalidaException | UsuarioJaLogadoException e) {
			throw new LoginException(e);
		}
	}

	public String getInfoUsuario(String atributo) 
			throws SenhaProtegidaException, UsuarioNaoExisteException, UsuarioNaoLogadoException {
		if (!temUsuarioLogado())
			throw new UsuarioNaoLogadoException();
		return getInfoUsuario(atributo, usuarioDaSessao.getEmail());
	}

	public void logout() throws LogoutException {
		if (!temUsuarioLogado())
			throw new LogoutException();
		retirarUsuarioDaSessao();
	}

	public void removeUsuario(String emailUsuario) throws UsuarioNaoExisteException {
		Usuario usuario = recuperarUsuario(emailUsuario);
		usuariosDoSistema.remove(usuario);
	}

	public void fechaSistema() throws FechaSistemaException {
		if (temUsuarioLogado())
			throw new FechaSistemaException();
	}

	public void atualizaPerfil(String atributo, String valor) 
			throws AtualizaPerfilException {
		try {
			if (!temUsuarioLogado())
				throw new UsuarioNaoLogadoException();
			switch (atributo.toUpperCase()) {
				case "NOME":
					usuarioDaSessao.setNome(valor);
					break;
				case "DATA DE NASCIMENTO":
					usuarioDaSessao.setDataNasc(valor);
					break;
				case "E-MAIL":
					usuarioDaSessao.setEmail(valor);
					break;
				case "FOTO":
					usuarioDaSessao.setImagem(valor);
					break;
			}
		} catch (UsuarioNaoLogadoException | NomeUsuarioException | 
				  ConversaoDeDataException | EmailInvalidoException e) {
			throw new AtualizaPerfilException(e);
		}
	}

	public void atualizaPerfil(String atributo, String senha, String velhaSenha) 
			throws AtualizaPerfilException {
		try {
			if (!temUsuarioLogado())
				throw new UsuarioNaoLogadoException();
			if (atributo.equalsIgnoreCase("SENHA")) {
				if (!senhaCorreta(velhaSenha))
					throw new SenhaInvalidaException();
				usuarioDaSessao.setSenha(senha);
			}
		} catch(UsuarioNaoLogadoException | SenhaInvalidaException e) {
			throw new AtualizaPerfilException(e);
		}
	}

	/*
	 * Métodos internos
	 */

	private void verificarSeUsuarioExiste(String email) throws UsuarioJaExisteException {
		try {
			recuperarUsuario(email);
			throw new UsuarioJaExisteException();
		} catch (UsuarioNaoExisteException unee) {
		}
	}

	private Usuario recuperarUsuario(String email) throws UsuarioNaoExisteException {
		Iterator<Usuario> iterador = usuariosDoSistema.iterator();
		while (iterador.hasNext()) {
			Usuario usuario = iterador.next();
			if (usuario.getEmail().equalsIgnoreCase(email))
				return usuario;
		}
		throw new UsuarioNaoExisteException(email);
	}

	private boolean temUsuarioLogado() {
		return usuarioDaSessao != null;
	}

	private boolean senhaCorreta(String senha) {
		return usuarioDaSessao.getSenha().equals(senha);
	}

	private Usuario retirarUsuarioDaSessao() {
		return usuarioDaSessao = null;
	}
}
