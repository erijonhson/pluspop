package core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import util.FabricaDePost;
import exception.AtualizaPerfilException;
import exception.CadastraUsuarioException;
import exception.ConversaoDeDataException;
import exception.CriaPostException;
import exception.EmailInvalidoException;
import exception.FechaSistemaException;
import exception.LoginException;
import exception.LogoutException;
import exception.NomeUsuarioException;
import exception.SemNotificacaoException;
import exception.SenhaInvalidaException;
import exception.SenhaProtegidaException;
import exception.SolicitacaoNaoEnviadaException;
import exception.UsuarioJaExisteException;
import exception.UsuarioJaLogadoException;
import exception.UsuarioNaoExisteException;
import exception.UsuarioNaoLogadoException;
import exception.ConteudoPostNegativoException;
import exception.PostInexistenteException;

/**
 * Controller único do +Pop.
 * 
 * @author Eri Jonhson
 * @author Laybson Plismenn
 * @author Ordan Santos
 */
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
			setUsuarioDaSessao(recuperarUsuario(email));
			if (!getUsuarioDaSessao().autenticarSenha(senha)) {
				retirarUsuarioDaSessao();
				throw new SenhaInvalidaException();
			}
		} catch (UsuarioNaoExisteException | SenhaInvalidaException | UsuarioJaLogadoException | UsuarioNaoLogadoException e) {
			throw new LoginException(e);
		}
	}

	public String getInfoUsuario(String atributo) 
			throws SenhaProtegidaException, UsuarioNaoExisteException, UsuarioNaoLogadoException {
		return getInfoUsuario(atributo, getUsuarioDaSessao().getEmail());
	}

	public void logout() throws LogoutException {
		if (!temUsuarioLogado())
			throw new LogoutException();
		retirarUsuarioDaSessao();
	}

	public void removeUsuario(String email) throws UsuarioNaoExisteException {
		Usuario usuario = recuperarUsuario(email);
		usuariosDoSistema.remove(usuario);
	}

	public void fechaSistema() throws FechaSistemaException {
		if (temUsuarioLogado())
			throw new FechaSistemaException();
	}

	public void atualizaPerfil(String atributo, String valor) 
			throws AtualizaPerfilException {
		try {
			switch (atributo.toUpperCase()) {
				case "NOME":
					getUsuarioDaSessao().setNome(valor);
					break;
				case "DATA DE NASCIMENTO":
					getUsuarioDaSessao().setDataNasc(valor);
					break;
				case "E-MAIL":
					getUsuarioDaSessao().setEmail(valor);
					break;
				case "FOTO":
					getUsuarioDaSessao().setImagem(valor);
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
			if (atributo.equalsIgnoreCase("SENHA")) {
				if (!getUsuarioDaSessao().autenticarSenha(velhaSenha))
					throw new SenhaInvalidaException("A senha fornecida esta incorreta.");
				getUsuarioDaSessao().setSenha(senha);
			}
		} catch(UsuarioNaoLogadoException | SenhaInvalidaException e) {
			throw new AtualizaPerfilException(e);
		}
	}
	
	public void adicionaAmigo(String amigoEmail) 
			throws UsuarioNaoLogadoException, UsuarioNaoExisteException{
		
		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Usuario usuario = this.getUsuarioDaSessao();
		
		String notificacao = usuario.getNome() + " quer sua amizade.";
		
		amigo.addSolicitacaoAmizade(usuario);
		amigo.addNotificacao(notificacao);
	}
	
	public void rejeitaAmizade(String amigoEmail) 
			throws UsuarioNaoExisteException, SolicitacaoNaoEnviadaException, UsuarioNaoLogadoException{
		
		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Usuario usuario = this.getUsuarioDaSessao();
		
		String notificacao = usuario.getNome() + " rejeitou sua amizade.";
		
		usuario.rejeitaAmizade(amigo);
		amigo.addNotificacao(notificacao);
	}
	
	public void aceitaAmizade(String amigoEmail) 
			throws UsuarioNaoExisteException, SolicitacaoNaoEnviadaException, UsuarioNaoLogadoException{
		
		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Usuario usuario = this.getUsuarioDaSessao();
		
		String notificacao = usuario.getNome() + " aceitou sua amizade.";
		
		usuario.aceitaAmizade(amigo);
		amigo.addAmigo(usuario);
		
		amigo.addNotificacao(notificacao);
	}
	
	public void removeAmigo(String amigoEmail) 
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException{
		
		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Usuario usuario = this.getUsuarioDaSessao();
		
		String notificacao = usuario.getNome() + " removeu a sua amizade.";
		
		usuario.removeAmigo(amigo);
		amigo.removeAmigo(usuario);
		
		amigo.addNotificacao(notificacao);
		
	}
	
	public int getQtdAmigos() 
			throws UsuarioNaoLogadoException{
		
		return this.getUsuarioDaSessao().getQtdAmigos();
	}
	
	public void curtirPost(String amigoEmail, int post) 
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException{
		
		Usuario amigo = this.recuperarUsuario(amigoEmail);
		amigo.postCurtido(this.getUsuarioDaSessao(), post);
	}
	
	public int getNotificacoes() throws UsuarioNaoLogadoException{
		return this.getUsuarioDaSessao().getQtdNotificacoes();
	}
	
	public String getNextNotificacao() 
			throws UsuarioNaoLogadoException, SemNotificacaoException{
		return this.getUsuarioDaSessao().getNextNotificacao();
	}

	public void criaPost(String mensagem, Date data) 
			throws CriaPostException, UsuarioNaoLogadoException {
		
		FabricaDePost fabrica = new FabricaDePost();
		Post post = fabrica.construirPost(mensagem, data);
		getUsuarioDaSessao().addPost(post);
	}

	public String getPost(int post) 
			throws UsuarioNaoLogadoException {
		return getUsuarioDaSessao().getMural().get(post).toString();
	}

	public String getPost(String atributo, int post) 
			throws UsuarioNaoLogadoException {		
		switch (atributo.toUpperCase()) {
		case "MENSAGEM":
			return getUsuarioDaSessao().getMural().get(post).getConteudo();
		case "HASHTAGS":
			return getUsuarioDaSessao().getMural().get(post).getHashtags();
		default:// "DATA":
			return getUsuarioDaSessao().getMural().get(post).getMomento();
		}
	}

	public String getConteudoPost(int indice, int post) 
			throws Exception {
		if (indice < 0)
			throw new ConteudoPostNegativoException(null);
		if (indice >= getUsuarioDaSessao().getMural().get(post).getConteudoSize())
			throw new PostInexistenteException(indice + " " + getUsuarioDaSessao().getMural().get(post).getConteudoSize());
		return getUsuarioDaSessao().getMural().get(post).getConteudoPost(indice);
	}

	/*
	 * Métodos internos
	 */

	private Usuario getUsuarioDaSessao() throws UsuarioNaoLogadoException {
		if (!temUsuarioLogado())
			throw new UsuarioNaoLogadoException();
		return usuarioDaSessao;
	}

	private void setUsuarioDaSessao(Usuario usuario) throws UsuarioJaLogadoException {
		if (temUsuarioLogado())
			throw new UsuarioJaLogadoException(usuarioDaSessao.getNome());
		usuarioDaSessao = usuario;
	}

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

	private Usuario retirarUsuarioDaSessao() {
		return usuarioDaSessao = null;
	}
	
}
