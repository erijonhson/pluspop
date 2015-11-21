package core;

import java.io.FileNotFoundException;

import easyaccept.EasyAccept;
import exception.AtualizaPerfilException;
import exception.CadastraUsuarioException;
import exception.CriaPostException;
import exception.ErroBaixarPostException;
import exception.ErroNaConsultaDePopsException;
import exception.FechaSistemaException;
import exception.HashTagException;
import exception.LoginException;
import exception.LogoutException;
import exception.PostOutOfRangeException;
import exception.SemNotificacaoException;
import exception.SenhaProtegidaException;
import exception.SolicitacaoNaoEnviadaException;
import exception.UsuarioNaoExisteException;
import exception.UsuarioNaoLogadoException;

/**
 * Facade do +Pop.
 * 
 * @author Eri Jonhson
 * @author Laybson Plismenn
 * @author Ordan Santos
 */
public class Facade {

	private Controller popController;

	public Facade() {
		popController = new Controller();
	}

	public static void main(String[] args) throws CriaPostException {
		args = new String[] { "core.Facade",
				"./testes/usecase_1.txt",
				"./testes/usecase_2.txt",
				"./testes/usecase_3.txt",
				"./testes/usecase_4.txt",
				"./testes/usecase_5.txt",
				"./testes/usecase_6.txt",
				"./testes/usecase_7.txt",
				"./testes/usecase_8.txt",
				"./testes/usecase_9.txt"
				//"./testes/usecase_10.txt"
		};
		EasyAccept.main(args);
	}

	public void iniciaSistema() {

	}

	public String cadastraUsuario(String nome, String email, String senha,
			String dataNasc, String imagem) throws CadastraUsuarioException {
		return popController.cadastraUsuario(nome, email, senha, dataNasc,
				imagem);
	}

	public String cadastraUsuario(String nome, String email, String senha,
			String dataNasc) throws CadastraUsuarioException {
		return popController
				.cadastraUsuario(nome, email, senha, dataNasc, null);
	}

	public String getInfoUsuario(String atributo, String usuario)
			throws SenhaProtegidaException, UsuarioNaoExisteException {
		return popController.getInfoUsuario(atributo, usuario);
	}

	public String getInfoUsuario(String atributo)
			throws SenhaProtegidaException, UsuarioNaoExisteException,
			UsuarioNaoLogadoException {
		return popController.getInfoUsuario(atributo);
	}

	public void login(String email, String senha) throws LoginException {
		popController.login(email, senha);
	}

	public void logout() throws LogoutException {
		popController.logout();
	}

	public void removeUsuario(String usuario) throws UsuarioNaoExisteException {
		popController.removeUsuario(usuario);
	}

	public void fechaSistema() throws FechaSistemaException {
		popController.fechaSistema();
	}

	public void atualizaPerfil(String atributo, String valor)
			throws AtualizaPerfilException {
		popController.atualizaPerfil(atributo, valor);
	}

	public void atualizaPerfil(String atributo, String valor, String velhaSenha)
			throws AtualizaPerfilException {
		popController.atualizaPerfil(atributo, valor, velhaSenha);
	}

	public void criaPost(String mensagem, String dataHora)
			throws CriaPostException, UsuarioNaoLogadoException {
		popController.criaPost(mensagem, dataHora);
	}

	public String getPost(String atributo, int post)
			throws SenhaProtegidaException, UsuarioNaoExisteException,
			UsuarioNaoLogadoException {
		return popController.getPost(atributo, post);
	}

	public String getPost(int post) throws SenhaProtegidaException,
			UsuarioNaoExisteException, UsuarioNaoLogadoException {
		return popController.getPost(post);
	}

	public String getConteudoPost(int indice, int post) throws Exception {
		return popController.getConteudoPost(indice, post);
	}

	public void adicionaAmigo(String amigoEmail)
			throws UsuarioNaoLogadoException, UsuarioNaoExisteException {
		this.popController.adicionaAmigo(amigoEmail);
	}

	public int getNotificacoes() throws UsuarioNaoLogadoException {
		return this.popController.getNotificacoes();
	}

	public String getNextNotificacao() throws UsuarioNaoLogadoException,
			SemNotificacaoException {
		return this.popController.getNextNotificacao();
	}

	public void rejeitaAmizade(String usuario)
			throws UsuarioNaoExisteException, SolicitacaoNaoEnviadaException,
			UsuarioNaoLogadoException {
		popController.rejeitaAmizade(usuario);
	}

	public int getQtdAmigos() throws UsuarioNaoLogadoException {
		return popController.getQtdAmigos();
	}

	public void aceitaAmizade(String usuario) throws UsuarioNaoExisteException,
			SolicitacaoNaoEnviadaException, UsuarioNaoLogadoException {

		popController.aceitaAmizade(usuario);
	}

	public void curtirPost(String amigo, int post)
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException,
			PostOutOfRangeException, HashTagException {
		popController.curtirPost(amigo, post);
	}

	public void removeAmigo(String usuario) throws UsuarioNaoExisteException,
			UsuarioNaoLogadoException {

		popController.removeAmigo(usuario);
	}

	public void adicionaPops(int pops) throws Exception {
		popController.adicionaPops(pops);
	}

	public String getPopularidade() throws Exception {
		return popController.getPopularidade();
	}

	public void rejeitarPost(String amigoEmail, int post)
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException,
			PostOutOfRangeException, HashTagException {
		popController.rejeitarPost(amigoEmail, post);
	}

	public int getPopsPost(int post) throws PostOutOfRangeException {
		return popController.getPopsPost(post);
	}

	public int qtdCurtidasDePost(int post) throws PostOutOfRangeException {
		return popController.qtdCurtidasDePost(post);
	}

	public int qtdRejeicoesDePost(int post) throws PostOutOfRangeException {
		return popController.qtdRejeicoesDePost(post);
	}

	public int getPopsUsuario(String usuario) throws UsuarioNaoExisteException,
			ErroNaConsultaDePopsException {
		return popController.getPopsUsuario(usuario);
	}

	public int getPopsUsuario() throws UsuarioNaoLogadoException {
		return popController.getPopsUsuario();
	}

	public String atualizaRanking() {
		return popController.atualizaRanking();
	}

	public String atualizaTrendingTopics() {
		return popController.atualizaTrendingTopics();
	}
	
	public void atualizaFeed() throws UsuarioNaoLogadoException{
		popController.atualizaFeed();
	}
	
	public String getPostFeedNoticiasRecentes(int idx) throws UsuarioNaoLogadoException{
		return popController.getPostFeedNoticiasRecentes(idx);
	}
	
	public String getPostFeedNoticiasMaisPopulares (int idx) throws UsuarioNaoLogadoException{
		return popController.getPostFeedNoticiasMaisPopulares(idx);
	}
	
	public void baixaPosts() throws FileNotFoundException, UsuarioNaoLogadoException, ErroBaixarPostException{
		popController.baixaPosts();
	}
}
