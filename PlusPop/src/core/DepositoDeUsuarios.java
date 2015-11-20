package core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.FabricaDeUsuario;
import exception.CadastraUsuarioException;
import exception.ConversaoDeDataException;
import exception.EmailInvalidoException;
import exception.NomeUsuarioException;
import exception.SenhaProtegidaException;
import exception.UsuarioJaExisteException;
import exception.UsuarioNaoExisteException;

public class DepositoDeUsuarios {
	
	private List<Usuario> usuarios;
	private Ranking ranking;
	
	public DepositoDeUsuarios () {
		
		this.usuarios = new ArrayList<Usuario>();
		this.ranking = new Ranking();
	}
	
	public String cadastraUsuario(String nome, String email, String senha,
			String dataNasc, String imagem) throws CadastraUsuarioException {
		try {
			verificarSeUsuarioExiste(email);
			Usuario novoUsuario = FabricaDeUsuario.getInstance().
					construirUsuario(nome, email, senha, dataNasc, imagem);
			this.usuarios.add(novoUsuario);
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
	
	public void removeUsuario(String email) throws UsuarioNaoExisteException {
		Usuario usuario = recuperarUsuario(email);
		this.usuarios.remove(usuario);
	}
	
	public String atualizaRanking() {
		this.ranking.atualizaRank(this.usuarios);
		return this.ranking.getRankUsuario();
	}
	
	public String atualizaTrendingTopics() {
		this.ranking.atualizaRank(this.usuarios);
		return this.ranking.getRankHashtag();
	}
	
	public Usuario recuperarUsuario(String email) throws UsuarioNaoExisteException {
		Iterator<Usuario> iterador = this.usuarios.iterator();
		while (iterador.hasNext()) {
			Usuario usuario = iterador.next();
			if (usuario.getEmail().equalsIgnoreCase(email))
				return usuario;
		}
		throw new UsuarioNaoExisteException(email);
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
	

}
