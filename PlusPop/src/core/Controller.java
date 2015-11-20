package core;

import java.time.LocalDate;

import exception.AtualizaPerfilException;
import exception.CadastraUsuarioException;
import exception.ConversaoDeDataException;
import exception.CriaPostException;
import exception.EmailInvalidoException;
import exception.ErroNaConsultaDePopsException;
import exception.FechaSistemaException;
import exception.HashTagException;
import exception.LoginException;
import exception.LogoutException;
import exception.NomeUsuarioException;
import exception.PostOutOfRangeException;
import exception.SemNotificacaoException;
import exception.SenhaInvalidaException;
import exception.SenhaProtegidaException;
import exception.SolicitacaoNaoEnviadaException;
import exception.UsuarioAindaEstaLogado;
import exception.UsuarioJaLogadoException;
import exception.UsuarioNaoExisteException;
import exception.UsuarioNaoLogadoException;
import util.ConversorDeData;

/**
 * Controller do +Pop.
 * 
 * @author Eri Jonhson
 * @author Laybson Plismenn
 * @author Ordan Santos
 */
public class Controller {

	private Usuario usuarioDaSessao;
	private DepositoDeUsuarios usuariosDoSistema;

	/**
	 * Construtor do controller.
	 */
	public Controller() {
		this.usuariosDoSistema = new DepositoDeUsuarios();
		retirarUsuarioDaSessao();
	}

	/**
	 * Recebe informações acerca um novo usuário, e delega o DepositoDeUsuarios a cadastrar um novo usuário com essas informações.
	 * @param nome do usuario
	 * @param email do usuario
	 * @param senha do usuario
	 * @param dataNasc do usuario
	 * @param foto do usuario
	 * @return email do usuario criado
	 * @throws CadastraUsuarioException
	 */
	public String cadastraUsuario(String nome, String email, String senha,
			String dataNasc, String imagem) throws CadastraUsuarioException {
		return usuariosDoSistema.cadastraUsuario(nome, email, senha, dataNasc, imagem);
	}

	/**
	 * Delega o DepositoDeUsuarios a retornar um atributo do usuario de email fornecido. 
	 * @param atributo "NOME", "DATA DE NASCIMENTO", "SENHA" ou "FOTO"
	 * @param email do usuario
	 * @return Atributo pedido
	 * @throws SenhaProtegidaException
	 * @throws UsuarioNaoExisteException
	 */
	public String getInfoUsuario(String atributo, String email) 
			throws SenhaProtegidaException, UsuarioNaoExisteException {
		return this.usuariosDoSistema.getInfoUsuario(atributo, email);
	}

	/**
	 * Loga um usuario no sistema.
	 * @param email do usuario
	 * @param senha do usuario
	 * @throws LoginException
	 */
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

	/**
	 * Retorna um atributo do usuário da sessão.
	 * @param atributo "NOME", "DATA DE NASCIMENTO", "SENHA" ou "FOTO"
	 * @return Atributo pedido
	 * @throws SenhaProtegidaException
	 * @throws UsuarioNaoExisteException
	 * @throws UsuarioNaoLogadoException
	 */
	public String getInfoUsuario(String atributo) 
			throws SenhaProtegidaException, UsuarioNaoExisteException, UsuarioNaoLogadoException {
		return getInfoUsuario(atributo, getUsuarioDaSessao().getEmail());
	}

	/**
	 * Desloga um usuario logado do sistema.
	 * @throws LogoutException
	 */
	public void logout() throws LogoutException {
		if (!temUsuarioLogado())
			throw new LogoutException();
		retirarUsuarioDaSessao();
	}

	/**
	 * Delega o DepositoDeUsuarios a remover do sistema o usuario com email recebido
	 * @param email do usuario
	 * @throws UsuarioNaoExisteException
	 */
	public void removeUsuario(String email) throws UsuarioNaoExisteException {
		this.usuariosDoSistema.removeUsuario(email);
	}

	/**
	 * Salva o estado atual do sistema em arquivos e o fecha.
	 * @throws FechaSistemaException
	 */
	public void fechaSistema() throws FechaSistemaException {
		if (temUsuarioLogado())
			throw new FechaSistemaException();
	}

	/**
	 * Atualiza um atributo do usuário da sessão.
	 * @param atributo "NOME", "DATA DE NASCIMENTO", "SENHA" ou "FOTO"
	 * @param valor do atributo
	 * @throws AtualizaPerfilException
	 */
	public void atualizaPerfil(String atributo, String valor) 
			throws AtualizaPerfilException {
		try {
			Usuario usuario = getUsuarioDaSessao();
			switch (atributo.toUpperCase()) {
				case "NOME":
					usuario.setNome(valor);
					break;
				case "DATA DE NASCIMENTO":
					LocalDate dataNasc = ConversorDeData.getInstance().converterData(valor);
					usuario.setDataNasc(dataNasc);
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

	/**
	 * Atualiza a senha do usuário da sessão.
	 * @param atributo "SENHA"
	 * @param senha nova
	 * @param velhaSenha senha antiga
	 * @throws AtualizaPerfilException
	 */
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
	
	/**
	 * Recupera o amigo (usuario com email fornecido), adiciona ao usuario da sessão uma solicitação de amizade, e adiciona ao amigo uma notificação de amizade.
	 * @param amigoEmail email do amigo
	 * @throws UsuarioNaoLogadoException
	 * @throws UsuarioNaoExisteException
	 */
	public void adicionaAmigo(String amigoEmail) 
			throws UsuarioNaoLogadoException, UsuarioNaoExisteException{
		
		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Usuario usuario = this.getUsuarioDaSessao();
		
		String notificacao = usuario.getNome() + " quer sua amizade.";
		
		amigo.addSolicitacaoAmizade(usuario);
		amigo.addNotificacao(notificacao);
	}
	
	/**
	 * Recupera o amigo rejeitado (usuario com email fornecido), o usuario da sessão rejeita a solicitação de amizade, e adiciona ao amigo uma notificação rejeição de amizade.
	 * @param amigoEmail email do amigo rejeitado
	 * @throws UsuarioNaoExisteException
	 * @throws SolicitacaoNaoEnviadaException
	 * @throws UsuarioNaoLogadoException
	 */
	public void rejeitaAmizade(String amigoEmail) 
			throws UsuarioNaoExisteException, SolicitacaoNaoEnviadaException, UsuarioNaoLogadoException{
		
		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Usuario usuario = this.getUsuarioDaSessao();

		usuario.rejeitaAmizade(amigo);
		String notificacao = usuario.getNome() + " rejeitou sua amizade.";

		amigo.addNotificacao(notificacao);
	}
	
	/**
	 * Recupera o amigo aceito (usuario com email fornecido),  e adiciona ao amigo uma notificação aceitação de amizade, o usuario da sessão aceita a solicitação de amizade e adiciona o amigo.
	 * @param amigoEmail email do amigo aceito
	 * @throws UsuarioNaoExisteException
	 * @throws SolicitacaoNaoEnviadaException
	 * @throws UsuarioNaoLogadoException
	 */
	public void aceitaAmizade(String amigoEmail) 
			throws UsuarioNaoExisteException, SolicitacaoNaoEnviadaException, UsuarioNaoLogadoException{
		
		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Usuario usuario = this.getUsuarioDaSessao();
		
		String notificacao = usuario.getNome() + " aceitou sua amizade.";
		
		usuario.aceitaAmizade(amigo);
		amigo.addAmigo(usuario);
		
		amigo.addNotificacao(notificacao);
	}
	
	/**
	 * Recupera o amigo a ser excluido (usuario com email fornecido), remove a representação de amizade do usuario com o amigo, remove a representação de amizade do amigo com o usuario, envia uma notificação de fim de amizade para o amigo.
	 * @param amigoEmail email de amigo a ser excluido
	 * @throws UsuarioNaoExisteException
	 * @throws UsuarioNaoLogadoException
	 */
	public void removeAmigo(String amigoEmail) 
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException{
		
		Usuario amigo = this.recuperarUsuario(amigoEmail);
		Usuario usuario = this.getUsuarioDaSessao();
		
		String notificacao = usuario.getNome() + " removeu a sua amizade.";
		
		usuario.removeAmigo(amigo);
		amigo.removeAmigo(usuario);
		
		amigo.addNotificacao(notificacao);
		
	}
	
	/**
	 * Retorna a quantidade de amigos do usuario da sessão
	 * @return quantidade de amigos do usuario logado
	 * @throws UsuarioNaoLogadoException
	 */
	public int getQtdAmigos() 
			throws UsuarioNaoLogadoException{
		
		return this.getUsuarioDaSessao().getQtdAmigos();
	}
	
	/**
	 * Recupera o autor do post (usuario com email fornecido), recupera o post de indice dado, curte o post, adiciona os pops necessarios ao autor do post e envia uma notificação ao mesmo. 
	 * @param amigoEmail email do autor do post a ser curtido
	 * @param post índice do post a ser curtido
	 * @throws UsuarioNaoExisteException
	 * @throws UsuarioNaoLogadoException
	 * @throws PostOutOfRangeException
	 * @throws HashTagException 
	 */
	public void curtirPost(String amigoEmail, int post) 
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException, PostOutOfRangeException, HashTagException{

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
	
	/**
	 * Recupera o autor do post (usuario com email fornecido), recupera o post de indice dado, delega a rejeição do post, remove os pops necessarios ao autor do post e envia uma notificação ao mesmo. 
	 * @param amigoEmail email do autor do post a ser rejeitado
	 * @param post índice do post a ser rejeitado
	 * @throws UsuarioNaoExisteException
	 * @throws UsuarioNaoLogadoException
	 * @throws PostOutOfRangeException
	 * @throws HashTagException 
	 */
	public void rejeitarPost(String amigoEmail, int post) 
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException, PostOutOfRangeException, HashTagException {

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
	
	/**
	 * Retorna a quantidade de notificações do usuario logado
	 * @return Quantidade de notificações do usuario logado
	 * @throws UsuarioNaoLogadoException
	 */
	public int getNotificacoes() throws UsuarioNaoLogadoException{
		return this.getUsuarioDaSessao().getQtdNotificacoes();
	}
	
	/**
	 * Retorna a proxima notificação da fila de notificações.
	 * @return Proxima notificação da fila de notificações.
	 * @throws UsuarioNaoLogadoException
	 * @throws SemNotificacaoException
	 */
	public String getNextNotificacao() 
			throws UsuarioNaoLogadoException, SemNotificacaoException{
		return this.getUsuarioDaSessao().getNextNotificacao();
	}

	/**
	 * Delega à FabriacaDePost a criação de um post com mensagem e data fornecidos, e o adiciona ao usuário da sessão.
	 * @param mensagem String de dados componentes do post
	 * @param dataHora String representativa de data
	 * @throws CriaPostException
	 * @throws UsuarioNaoLogadoException
	 */
	public void criaPost(String mensagem, String dataHora) 
			throws CriaPostException, UsuarioNaoLogadoException {
		getUsuarioDaSessao().criaPost(mensagem, dataHora);
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
	
	/**
	 * Adiciona a quantidade dada de pops ao usuario logado
	 * @param quantidade de pops
	 * @throws Exception
	 */
	public void adicionaPops(int pops) 
			throws Exception {
		getUsuarioDaSessao().changePopularidade(pops);
	}
	
	public String getPopularidade() 
			throws Exception{
		return getUsuarioDaSessao().getComportamentoSocial();		
	}
	
	/**
	 * Retorna a quantidade de pops do post de indice dado.
	 * @param indice do post
	 * @return quantidade de pops do post de indice dado.
	 * @throws PostOutOfRangeException
	 */
	public int getPopsPost(int post) 
			throws PostOutOfRangeException {
		return usuarioDaSessao.getPostByIndex(post).getPopularidade();
	}
	
	/**
	 * Retorna a quantidade de curtidas do post de indice dado.
	 * @param indice do post
	 * @return quantidade de curtidas do post de indice dado.
	 * @throws PostOutOfRangeException
	 */
	public int qtdCurtidasDePost(int post) 
			throws PostOutOfRangeException {
		return usuarioDaSessao.getPostByIndex(post).getCurtidas();
	}
	
	/**
	 * Retorna a quantidade de rejeições do post de indice dado.
	 * @param indice do post
	 * @return quantidade de rejeições do post de indice dado.
	 * @throws PostOutOfRangeException
	 */
	public int qtdRejeicoesDePost(int post) 
			throws PostOutOfRangeException {
		return usuarioDaSessao.getPostByIndex(post).getRejeicoes();
	}
	
	/**
	 * Recupera o usuario de email dado e retorna a sua quantidade de pops
	 * @param email do usuario
	 * @return quantidade de pops do usuario
	 * @throws UsuarioNaoExisteException
	 * @throws ErroNaConsultaDePopsException
	 */
	public int getPopsUsuario(String usuario) 
			throws UsuarioNaoExisteException, ErroNaConsultaDePopsException {
		
		if (this.temUsuarioLogado())
			throw new ErroNaConsultaDePopsException(new UsuarioAindaEstaLogado());
	
		return this.recuperarUsuario(usuario).getPopularidade();
	}
	
	public int getPopsUsuario() 
			throws UsuarioNaoLogadoException {
		return usuarioDaSessao.getPopularidade();
	}
	
	/**
	 * Delega ao DepositoDeUsuarios a atalização do ranking de usuarios
	 * @return String representando o ranking de usuarios
	 */
	public String atualizaRanking() {
		return this.usuariosDoSistema.atualizaRanking();
	}
	
	/**
	 * Delega ao DepositoDeUsuarios a atalização dos Trending Topics
	 * @return String representando os Trending Topics
	 */
	public String atualizaTrendingTopics() {
		return this.usuariosDoSistema.atualizaTrendingTopics();
	}
	
	/**
	 * Delega ao DepositoDeUsuarios a recuperação do usuario de email dado.
	 * @param email do usuario a ser recuperado
	 * @return Usuario de email dado
	 * @throws UsuarioNaoExisteException
	 */
	public Usuario recuperarUsuario(String email) throws UsuarioNaoExisteException {
		return this.usuariosDoSistema.recuperarUsuario(email);
	}

	/**
	 * Delega o update do feed do usuario logado
	 * @throws UsuarioNaoLogadoException
	 */
	public void atualizaFeed() throws UsuarioNaoLogadoException {
		Usuario usuario = getUsuarioDaSessao();
		usuario.updateFeed();
	}
	
	/**
	 * Retorna o post de indice dado do feed de noticias recentes do usuario logado
	 * @param indice do post
	 * @return Mensagem do post
	 * @throws UsuarioNaoLogadoException
	 */
	public String getPostFeedNoticiasRecentes(int idx) throws UsuarioNaoLogadoException{
		Usuario usuario = getUsuarioDaSessao();
		return usuario.getPostFeedNoticiasRecentes(idx);
	}

	/**
	 * Retorna o post de indice dado do feed de noticias mais popularesS do usuario logado
	 * @param indice do post
	 * @return Mensagem do post
	 * @throws UsuarioNaoLogadoException
	 */

	public String getPostFeedNoticiasMaisPopulares(int idx) throws UsuarioNaoLogadoException {
		 Usuario usuario = getUsuarioDaSessao();
		 return usuario.getPostFeedNoticiasMaisPopulares(idx);
	}

	/*
	 * Métodos internos
	 */

	/**
	 * Retorna o usuario da sessão
	 * @return Usuario logado
	 * @throws UsuarioNaoLogadoException
	 */
	private Usuario getUsuarioDaSessao() throws UsuarioNaoLogadoException {
		if (!temUsuarioLogado())
			throw new UsuarioNaoLogadoException();
		return usuarioDaSessao;
	}

	/**
	 * Adiciona usuario na sessão
	 * @param usuario a ser logado
	 * @throws UsuarioJaLogadoException
	 */
	private void setUsuarioDaSessao(Usuario usuario) throws UsuarioJaLogadoException {
		if (temUsuarioLogado())
			throw new UsuarioJaLogadoException(usuarioDaSessao.getNome());
		usuarioDaSessao = usuario;
	}

	private boolean temUsuarioLogado() {
		return usuarioDaSessao != null;
	}

	private Usuario retirarUsuarioDaSessao() {
		return usuarioDaSessao = null;
	}
	
}
