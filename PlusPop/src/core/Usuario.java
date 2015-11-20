package core;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.EmailValidator;
import core.popularidade.CelebridadePop;
import core.popularidade.ComportamentoSocial;
import core.popularidade.IconePop;
import core.popularidade.Normal;
import exception.ConteudoPostInexistenteException;
import exception.ConteudoPostNegativoException;
import exception.EmailInvalidoException;
import exception.NomeUsuarioException;
import exception.PostOutOfRangeException;
import exception.SemNotificacaoException;
import exception.SolicitacaoNaoEnviadaException;

/**
 * Usuário comum do +Pop.
 * 
 * @author Eri Jonhson
 * @author Laybson Plismenn
 * @author Ordan Santos
 */
public class Usuario implements Serializable, Comparable<Usuario>{

	private static final long serialVersionUID = -8892086465318181235L;
	private static final String imagemDefault = "resources/default.jpg";
	
	private String nome;
	private String email;
	private String senha;
	private LocalDate dataNasc;
	private String imagem;
	private List<Post> mural;
	private List<String> notificacoes;
	private Set<Usuario> amigos;
	private Set<Usuario> solicitacoesDeAmizade;
	private ComportamentoSocial comportamentoSocial;
	private int popularidade;
	private Feed feedPopular, feedRecente;
	
	/**
	 * Construtor de Usuário
	 * @param nome do usuario
	 * @param email do usuario
	 * @param senha do usuario
	 * @param dataNasc do usuario
	 * @param foto do usuario
	 */
	public Usuario(String nome, String email, String senha, LocalDate dataNasc, String imagem) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNasc = dataNasc;
		this.imagem = imagem;
		this.comportamentoSocial = new Normal();
		this.mural = new ArrayList<Post>();
		this.notificacoes = new ArrayList<String>();
		this.amigos = new HashSet<Usuario>();
		this.solicitacoesDeAmizade = new HashSet<Usuario>();
		this.popularidade = 0;
		this.feedRecente = new Feed(new ComparatorFeedRecente());
		this.feedPopular = new Feed(new ComparatorFeedPopular());
	}

	/**
	 * Adiciona um post dado à lista de mural.
	 * @param post a ser adicionado.
	 */
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

	/**
	 * Verificar autenticidade da senha do {@link Usuario}.
	 * @param senha
	 * @return <tt>true</tt> se senha correta.
	 */
	public boolean autenticarSenha(String senha) {
		return this.senha.equals(senha);
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataNasc() {
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
		return dataNasc.toString();
	}

	public void setDataNasc(LocalDate dataNasc) {
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
	
	public List<Post> getMural() {
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

	/**
	 * Recuperar informações de um {@link Post}.
	 * 
	 * @param post
	 *            número do post.
	 * @return string representativa do {@link Post}
	 */
	public String getPost(int post) {
		return this.mural.get(post).toString();
	}

	/**
	 * Recuperar informação de algum atributo de um {@link Post}.
	 * 
	 * @param atributo
	 *            a recuperar
	 * @param post
	 *            número do post
	 * @return atributo específico
	 */
	public String getPost(String atributo, int post) {
		switch (atributo.toUpperCase()) {
		case "MENSAGEM":
			return this.mural.get(post).getConteudo();
		case "HASHTAGS":
			return this.mural.get(post).getStringOfHashtags();
		default:// "DATA":
			return this.mural.get(post).getMomento();
		}
	}

	/**
	 * Recuperar informação de algum atributo de um {@link Post}.
	 * 
	 * @param indice
	 *            do atributo a recuperar
	 * @param numeroDoPost
	 * @return atributo específico
	 */
	public String getConteudoPost(int indice, int numeroDoPost)
			throws ConteudoPostNegativoException,
			ConteudoPostInexistenteException {
		Post post = this.mural.get(numeroDoPost);
		if (indice < 0)
			throw new ConteudoPostNegativoException();
		if (indice >= post.getConteudoSize())
			throw new ConteudoPostInexistenteException(indice, post.getConteudoSize());
		return post.getConteudoPost(indice);
	}

	/**
	 * Adiciona um Usuario recebido ao set de solicitações de amizade.
	 * @param Usuario a ser adicionado às solicitações
	 */
	public void addSolicitacaoAmizade(Usuario amigo){
		this.solicitacoesDeAmizade.add(amigo);
	}
	
	/**
	 * Remove usuario recebido do set de solicitações de amizade.
	 * @param usuario a ser removido
	 * @throws SolicitacaoNaoEnviadaException
	 */
	public void rejeitaAmizade(Usuario amigo) 
			throws SolicitacaoNaoEnviadaException{
		
		if (!this.solicitacoesDeAmizade.contains(amigo)) 
			throw new SolicitacaoNaoEnviadaException(amigo.getNome());
		
		this.solicitacoesDeAmizade.remove(amigo);
	}

	/**
	 * Adiciona usuario recebido à lista de amigos, remove usuario recebido do set de solicitações de amizade
	 * @param novo usuario amigo
	 * @throws SolicitacaoNaoEnviadaException
	 */
	public void aceitaAmizade(Usuario amigo) 
			throws SolicitacaoNaoEnviadaException{
		
		if (!this.solicitacoesDeAmizade.contains(amigo))
			throw new SolicitacaoNaoEnviadaException(amigo.getNome());
		
		this.addAmigo(amigo);
		this.solicitacoesDeAmizade.remove(amigo);
	}

	/**
	 * Remove o usuario recebido da lista de amigos
	 * @param amigo a ser removido
	 */
	public void removeAmigo(Usuario amigo){
		this.amigos.remove(amigo);
	}

	/**
	 * Adiciona usuario recebido à lista de amigos
	 * @param amigo
	 */
	public void addAmigo(Usuario amigo){
		this.amigos.add(amigo);
	}

	/**
	 * Delega ao comportamento social a função de curtir um post recebido
	 * @param post a ser curtido
	 */
	public void curtir(Post post){
		comportamentoSocial.curtir(post);
	}
	
	/**
	 * Delega ao comportamento social a função de rejeitar um post recebido
	 * @param post a ser rejeitado
	 */
	public void rejeitar(Post post){
		comportamentoSocial.rejeitar(post);
	}

	public int getQtdAmigos(){
		return this.amigos.size();
	}

	/**
	 * Adiciona uma notificação à fila de notificações
	 * @param notificacao a ser adicionada
	 */
	public void addNotificacao(String notificacao){
		this.notificacoes.add(notificacao);
	}

	public int getQtdNotificacoes(){
		return this.notificacoes.size();
	}

	/**
	 * Retorna a próxima notificação da fila de notificações.
	 * @return próxima notificação da fila
	 * @throws SemNotificacaoException
	 */
	public String getNextNotificacao() throws SemNotificacaoException{
		if (this.notificacoes.size() == 0) throw new SemNotificacaoException();
		String notificacao = this.notificacoes.get(0);
		this.notificacoes.remove(0);
		return notificacao;
	}

	/**
	 * Retorna o post de indice recebido do mural.
	 * @param indice do post
	 * @return post
	 * @throws PostOutOfRangeException
	 */
	public Post getPostByIndex(int index) 
			throws PostOutOfRangeException{
		if (index >= mural.size()) throw new PostOutOfRangeException(index, mural.size());
		return mural.get(index);
	}

	public void setAvaliadorNormal(){
		this.comportamentoSocial = new Normal();
	}

	public void setAvaliadorCelebridadePop(){
		this.comportamentoSocial = new CelebridadePop();
	}

	public void setAvaliadorIconePop(){
		this.comportamentoSocial = new IconePop();
	}

	/**
	 * Altera a popularidade do usuario, provoca troca dinâmica de comportamento social, de acordo com a quantidade de popularidade.
	 * @param quantidade de popularidade a ser alterada.
	 */
	public void changePopularidade(int delta){
		this.popularidade += delta;
		if (this.popularidade < 500)
			setAvaliadorNormal();
		else
			if (this.popularidade <= 1000)
				setAvaliadorCelebridadePop();
			else
				setAvaliadorIconePop();
	}

	public int getPopularidade(){
		return this.popularidade;
	}

	public List<Post> getPosts(){
		return this.mural;
	}

	/**
	 *  
	 * @return os posts a serem compartilhados com os amigos
	 */
	public List<Post> compartilhar(){
		
		List<Post> postsRecentes = new ArrayList<Post>();
		int qtd = comportamentoSocial.qtdParaCompartilhar();
		
		for (Post post : mural){
			if (postsRecentes.size() < qtd){
				postsRecentes.add(post);
			} else{
				if (postsRecentes.get(qtd - 1).compareTempo(post) < 0){
					postsRecentes.remove(qtd - 1);
					postsRecentes.add(post);
				}
			}
			Collections.sort(postsRecentes, (pa, pb) -> -pa.compareTempo(pb));
		}
		
		return postsRecentes;
	}

	/**
	 * Atualiza os feeds de noticias recentes e populares.
	 */
	public void updateFeed(){
		this.feedRecente.update(this.amigos);
		this.feedPopular.update(this.amigos);
	}
	
	public String getComportamentoSocial() {
		return comportamentoSocial.toString();
	}

	/*
	 * Métodos internos.
	 */

	private boolean emailInvalido(String email) {
		return !EmailValidator.getInstance().validateEmail(email);
	}

	private boolean stringVazia(String s) {
		return s == null || s.trim().equals("");
	}

	/**
	 * Compara um usuário pela sua popularidade
	 */
	public int compareTo(Usuario o) {
		if (this.getPopularidade() == o.getPopularidade())
			return -this.getEmail().compareTo(o.getEmail());
		else
			return -(this.getPopularidade() - o.getPopularidade());
	}

	/**
	 * Retorna um post com um determinado índex pela ordem dos mais recentes<br/>
	 * <b><p>0 - menos recente </b></p>
	 * <b><p>N - mais recente  </br></p>
	 * @param idx índice do post
	 * @return a String do post
	 */
	public String getPostFeedNoticiasRecentes(int idx){
		Post post = feedRecente.getPost (idx);
		return post.toString();
	}
	
	/**
	 * Retorna um post com um determinado índex pela ordem dos mais populares<br/>
	 * <b><p>0 - menos popular </b></p>
	 * <b><p>N - mais popular  </br></p>
	 * @param idx índice do post
	 * @return a String do post
	 */
	public String getPostFeedNoticiasMaisPopulares(int idx) {
		Post post = feedPopular.getPost(idx);
		return post.toString();
	}

}
