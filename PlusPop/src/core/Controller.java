package core;

import java.util.ArrayList;
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
import exception.PostOutOfRangeException;
import exception.SemNotificacaoException;
import exception.SenhaInvalidaException;
import exception.SenhaProtegidaException;
import exception.SolicitacaoNaoEnviadaException;
import exception.UsuarioJaExisteException;
import exception.UsuarioJaLogadoException;
import exception.UsuarioNaoExisteException;
import exception.UsuarioNaoLogadoException;

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
	private Ranking ranking;

	public Controller() {
		usuariosDoSistema = new ArrayList<Usuario>();
		ranking = new Ranking();
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
			Usuario usuario = recuperarUsuario(email);
			if (!usuario.autenticarSenha(senha)) 
				throw new SenhaInvalidaException();
			setUsuarioDaSessao(usuario);
		} catch (UsuarioNaoExisteException | SenhaInvalidaException | UsuarioJaLogadoException e) {
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
			Usuario usuario = getUsuarioDaSessao();
			switch (atributo.toUpperCase()) {
				case "NOME":
					usuario.setNome(valor);
					break;
				case "DATA DE NASCIMENTO":
					usuario.setDataNasc(valor);
					break;
				case "E-MAIL":
					usuario.setEmail(valor);
					break;
				case "FOTO":
					usuario.setImagem(valor);
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
			Usuario usuarioDaSessao = getUsuarioDaSessao();
			if (atributo.equalsIgnoreCase("SENHA")) {
				if (!usuarioDaSessao.autenticarSenha(velhaSenha))
					throw new SenhaInvalidaException("A senha fornecida esta incorreta.");
				usuarioDaSessao.setSenha(senha);
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

		usuario.rejeitaAmizade(amigo);
		String notificacao = usuario.getNome() + " rejeitou sua amizade.";

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
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException, PostOutOfRangeException{

		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Post postAmigo = amigo.getPostByIndex(post);
		Usuario usuario = getUsuarioDaSessao();

		int oldPop = postAmigo.getPopularidade();

		usuario.curtir(postAmigo);

		amigo.changePopularidade(postAmigo.getPopularidade() - oldPop);

		String notificacao = usuario.getNome() + " curtiu seu post de "
				+ postAmigo.getMomento() + ".";
		amigo.addNotificacao(notificacao);
	}
	
	public void rejeitarPost(String amigoEmail, int post) 
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException, PostOutOfRangeException {

		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Post postAmigo = amigo.getPostByIndex(post);
		Usuario usuario = getUsuarioDaSessao();

		int oldPop = postAmigo.getPopularidade();

		usuario.rejeitar(postAmigo);

		amigo.changePopularidade(postAmigo.getPopularidade() - oldPop);

		String notificacao = usuario.getNome() + " rejeitou seu post de "
				+ postAmigo.getMomento() + ".";
		amigo.addNotificacao(notificacao);
	}
	
	public int getNotificacoes() throws UsuarioNaoLogadoException{
		return this.getUsuarioDaSessao().getQtdNotificacoes();
	}
	
	public String getNextNotificacao() 
			throws UsuarioNaoLogadoException, SemNotificacaoException{
		return this.getUsuarioDaSessao().getNextNotificacao();
	}

	public void criaPost(String mensagem, String dataHora) 
			throws CriaPostException, UsuarioNaoLogadoException {
		Post post = FabricaDePost.getInstance().construirPost(mensagem, dataHora);
		getUsuarioDaSessao().addPost(post);
	}

	public String getPost(int post) 
			throws UsuarioNaoLogadoException {
		return getUsuarioDaSessao().getPost(post);
	}

	public String getPost(String atributo, int post) 
			throws UsuarioNaoLogadoException {		
		return getUsuarioDaSessao().getPost(atributo, post);
	}

	public String getConteudoPost(int indice, int post) 
			throws Exception {
		return getUsuarioDaSessao().getConteudoPost(indice, post);
	}
	
	public void adicionaPops(int pops) 
			throws Exception {
		getUsuarioDaSessao().changePopularidade(pops);
	}
	
	public String getPopularidade() 
			throws Exception{
		return getUsuarioDaSessao().getComportamentoSocial();		
	}
	
	public int getPopsPost(int post) 
			throws PostOutOfRangeException {
		return usuarioDaSessao.getPostByIndex(post).getPopularidade();
	}
	
	public int qtdCurtidasDePost(int post) 
			throws PostOutOfRangeException {
		return usuarioDaSessao.getPostByIndex(post).getCurtidas();
	}
	
	public int qtdRejeicoesDePost(int post) 
			throws PostOutOfRangeException {
		return usuarioDaSessao.getPostByIndex(post).getRejeicoes();
	}
	
	public int getPopsUsuario(String usuario) 
			throws UsuarioNaoExisteException {
		return this.recuperarUsuario(usuario).getPopularidade();//popController.getPopsUsuario();
	}
	
	public int getPopsUsuario() 
			throws UsuarioNaoLogadoException {
		return usuarioDaSessao.getPopularidade();
	}
	
	public String atualizaRanking() {
		this.ranking.atualizaRank(this.usuariosDoSistema);
		return this.ranking.getRankUsuario();
	}
	
	public String atualizaTrendingTopics() {
		this.ranking.atualizaRank(this.usuariosDoSistema);
		return this.ranking.getRankHashtag();
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
