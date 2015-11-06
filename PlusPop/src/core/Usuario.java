package core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.popularidade.CelebridadePop;
import core.popularidade.ComportamentoSocial;
import core.popularidade.IconePop;
import core.popularidade.Normal;
import util.ConversorDeData;
import util.EmailValidator;
import exception.ConteudoPostInexistenteException;
import exception.ConteudoPostNegativoException;
import exception.ConversaoDeDataException;
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
	private Date dataNasc;
	private String imagem;
	private List<Post> mural;
	private List<String> notificacoes;
	private Set<Usuario> amigos;
	private Set<Usuario> solicitacoesDeAmizade;
	private ComportamentoSocial comportamentoSocial;
	private int popularidade;
	private Feed feed;
	
	public Usuario(String nome, String email, String senha, String dataNasc, String imagem)
			throws NomeUsuarioException, EmailInvalidoException,
			ConversaoDeDataException {
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setDataNasc(dataNasc);
		setImagem(imagem);
		setAvaliadorNormal();
		this.mural = new ArrayList<Post>();
		this.notificacoes = new ArrayList<String>();
		this.amigos = new HashSet<Usuario>();
		this.solicitacoesDeAmizade = new HashSet<Usuario>();
		this.popularidade = 0;
		this.feed = new Feed();
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

	public void addSolicitacaoAmizade(Usuario amigo){
		this.solicitacoesDeAmizade.add(amigo);
	}
	
	public void rejeitaAmizade(Usuario amigo) 
			throws SolicitacaoNaoEnviadaException{
		
		if (!this.solicitacoesDeAmizade.contains(amigo)) 
			throw new SolicitacaoNaoEnviadaException(amigo.getNome());
		
		this.solicitacoesDeAmizade.remove(amigo);
	}

	public void aceitaAmizade(Usuario amigo) 
			throws SolicitacaoNaoEnviadaException{
		
		if (!this.solicitacoesDeAmizade.contains(amigo))
			throw new SolicitacaoNaoEnviadaException(amigo.getNome());
		
		this.addAmigo(amigo);
		this.solicitacoesDeAmizade.remove(amigo);
	}

	public void removeAmigo(Usuario amigo){
		this.amigos.remove(amigo);
	}

	public void addAmigo(Usuario amigo){
		this.amigos.add(amigo);
	}

	public void curtir(Post post){
		comportamentoSocial.curtir(post);
	}
	
	public void rejeitar(Post post){
		comportamentoSocial.rejeitar(post);
	}

	public int getQtdAmigos(){
		return this.amigos.size();
	}

	public void addNotificacao(String notificacao){
		this.notificacoes.add(notificacao);
	}

	public int getQtdNotificacoes(){
		return this.notificacoes.size();
	}

	public String getNextNotificacao() throws SemNotificacaoException{
		if (this.notificacoes.size() == 0) throw new SemNotificacaoException();
		String notificacao = this.notificacoes.get(0);
		this.notificacoes.remove(0);
		return notificacao;
	}

	public Post getPostByIndex(int index) 
			throws PostOutOfRangeException{
		if (index >= mural.size()) throw new PostOutOfRangeException(index+" "+mural.size());
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

	public List<Post> compartilhar(){
		return comportamentoSocial.compartilhar(this.mural);
	}

	public List<Post> getFeed(){
		return this.feed.getFeed();
	}

	public void updateFeed(){
		this.feed.update(this.amigos);
	}

	public void setFeedPorTempo(){
		this.feed.setComparatorPorTempo();
	}

	public void setFeedPorPopularidade(){
		this.feed.setComparatorPorPopularidade();
	}
	
	public String getComportamentoSocial() {
		return comportamentoSocial.toString();
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

	public int compareTo(Usuario o) {
		if (this.getPopularidade() == o.getPopularidade())
			return -this.getEmail().compareTo(o.getEmail());
		else
			return -(this.getPopularidade() - o.getPopularidade());
	}

}
