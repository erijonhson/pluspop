package core;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import util.FabricaDePost;
import easyaccept.EasyAccept;
import exception.AtualizaPerfilException;
import exception.CadastraUsuarioException;
import exception.CriaPostException;
import exception.FechaSistemaException;
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
		List<Post> posts = new ArrayList<Post>();
		posts.add(FabricaDePost.getInstance().construirPost("teste#gfgf", "11/11/1111 11:11:11"));
		posts.add(FabricaDePost.getInstance().construirPost("teste#gfga", "11/11/1111 11:11:11"));
		
		try {
			ManipuladorDeArquivo.save(posts);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		args = new String[] { "core.Facade",
				"resources/Scripts de Teste/usecase_1.txt",
				"resources/Scripts de Teste/usecase_2.txt",
				"resources/Scripts de Teste/usecase_3.txt",
				"resources/Scripts de Teste/usecase_4.txt",
				"resources/Scripts de Teste/usecase_5.txt",
				"resources/Scripts de Teste/usecase_6.txt",
				"resources/Scripts de Teste/usecase_7.txt",
				
		};
		EasyAccept.main(args);
	}

	public void iniciaSistema() {

	}

	public String cadastraUsuario(String nome, String email, String senha, String dataNasc, String imagem) 
			throws CadastraUsuarioException {
		return popController.cadastraUsuario(nome, email, senha, dataNasc, imagem);
	}

	public String cadastraUsuario(String nome, String email, String senha, String dataNasc) 
			throws CadastraUsuarioException {
		return popController.cadastraUsuario(nome, email, senha, dataNasc, null);
	}

	public String getInfoUsuario(String atributo, String usuario) 
			throws SenhaProtegidaException, UsuarioNaoExisteException {
		return popController.getInfoUsuario(atributo, usuario);
	}

	public String getInfoUsuario(String atributo) 
			throws SenhaProtegidaException, UsuarioNaoExisteException, UsuarioNaoLogadoException {
		return popController.getInfoUsuario(atributo);
	}

	public void login(String email, String senha) 
			throws LoginException {
		popController.login(email, senha);
	}

	public void logout() throws LogoutException {
		popController.logout();
	}

	public void removeUsuario(String usuario) 
			throws UsuarioNaoExisteException {
		popController.removeUsuario(usuario);
	}

	public void fechaSistema() 
			throws FechaSistemaException {
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
			throws SenhaProtegidaException, UsuarioNaoExisteException, UsuarioNaoLogadoException {
		return popController.getPost(atributo, post);
	}

	public String getPost(int post) 
			throws SenhaProtegidaException, UsuarioNaoExisteException, UsuarioNaoLogadoException {
		return popController.getPost(post);
	}

	public String getConteudoPost(int indice, int post) 
			throws Exception{
		return popController.getConteudoPost(indice, post);
	}

	public void adicionaAmigo(String amigoEmail) 
			throws UsuarioNaoLogadoException, UsuarioNaoExisteException {
		this.popController.adicionaAmigo(amigoEmail);
	}	

	public int getNotificacoes() 
			throws UsuarioNaoLogadoException {
		return this.popController.getNotificacoes();
	}

	public String getNextNotificacao() 
			throws UsuarioNaoLogadoException, SemNotificacaoException {
		return this.popController.getNextNotificacao();
	}

	public void rejeitaAmizade(String usuario) 
			throws UsuarioNaoExisteException, SolicitacaoNaoEnviadaException, UsuarioNaoLogadoException {
		popController.rejeitaAmizade(usuario);
	}

	public int getQtdAmigos() 
			throws UsuarioNaoLogadoException {
		return popController.getQtdAmigos();
	}

	public void aceitaAmizade(String usuario) 
			throws UsuarioNaoExisteException, SolicitacaoNaoEnviadaException, UsuarioNaoLogadoException {
		
		popController.aceitaAmizade(usuario);
	}

	public void curtirPost(String amigo, int post) 
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException, PostOutOfRangeException {
		popController.curtirPost(amigo, post);
	}

	public void removeAmigo(String usuario) 
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException {
		
		popController.removeAmigo(usuario);
	}
	
	public void adicionaPops(int pops)
			throws Exception {
		popController.adicionaPops(pops);
	}
	
	public String getPopularidade() 
			throws Exception {
		return popController.getPopularidade();		
	}
	
	public void rejeitarPost(String amigoEmail, int post) 
			throws UsuarioNaoExisteException, UsuarioNaoLogadoException, PostOutOfRangeException {
		popController.rejeitarPost(amigoEmail, post);
	}
	
	public int getPopsPost(int post) 
			throws PostOutOfRangeException {
		return popController.getPopsPost(post);
	}
	
	public int qtdCurtidasDePost(int post) 
			throws PostOutOfRangeException {
		return popController.qtdCurtidasDePost(post);
	}
	
	public int qtdRejeicoesDePost(int post) 
			throws PostOutOfRangeException {
		return popController.qtdRejeicoesDePost(post);
	}
	
	public int getPopsUsuario(String usuario) 
			throws UsuarioNaoExisteException {
		return popController.getPopsUsuario(usuario);
	}
	
	public int getPopsUsuario() 
			throws UsuarioNaoLogadoException {
		return popController.getPopsUsuario();
	}
	
	public String atualizaRanking() {
		return popController.atualizaRanking();
	}
	
	public String atualizaTrendingTopics() {
		return popController.atualizaTrendingTopics();
	}
	
	

}
