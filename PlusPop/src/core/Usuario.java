package core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import util.ConversorDeData;
import util.EmailValidator;
import exception.ConversaoDeDataException;
import exception.EmailInvalidoException;
import exception.NomeUsuarioException;
import exception.SemNotificacaoException;
import exception.SolicitacaoNaoEnviadaException;

/**
 * Usuário comum do +Pop.
 * 
 * @author Eri Jonhson
 * @author Laybson Plismenn
 * @author Ordan Santos
 */
public class Usuario implements Serializable {

	private static final long serialVersionUID = -8892086465318181235L;
	private static final String imagemDefault = "resources/default.jpg";
	
	private String nome;
	private String email;
	private String senha;
	private Date dataNasc;
	private String imagem;
	private ArrayList<Post> mural;
	private ArrayList<String> notificacoes;
	private HashSet<String> amigos;
	private HashSet<String> solicitacoesDeAmizade;
	
	public Usuario(String nome, String email, String senha, String dataNasc, String imagem)
			throws NomeUsuarioException, EmailInvalidoException,
			ConversaoDeDataException {
		setNome(nome);
		setEmail(email);
		setSenha(senha);
		setDataNasc(dataNasc);
		setImagem(imagem);
		this.mural = new ArrayList();
		this.notificacoes = new ArrayList<String>();
		this.amigos = new HashSet<>();
		this.solicitacoesDeAmizade = new HashSet<String>();
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
	
	public ArrayList<Post> getMural() {
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
	
	public void addSolicitacaoAmizade(Usuario amigo){
		this.solicitacoesDeAmizade.add(amigo.getEmail());
	}
	
	public void rejeitaAmizade(Usuario amigo) 
			throws SolicitacaoNaoEnviadaException{
		
		if (!this.solicitacoesDeAmizade.contains(amigo.getEmail())) 
			throw new SolicitacaoNaoEnviadaException(amigo.getNome());
		
		this.solicitacoesDeAmizade.remove(amigo.getEmail());
	}
	
	public void aceitaAmizade(Usuario amigo) 
			throws SolicitacaoNaoEnviadaException{
		
		if (!this.solicitacoesDeAmizade.contains(amigo.getEmail()))
			throw new SolicitacaoNaoEnviadaException(amigo.getNome());
		
		this.addAmigo(amigo);
		this.solicitacoesDeAmizade.remove(amigo.getEmail());
	}
	
	public void removeAmigo(Usuario amigo){
		this.amigos.remove(amigo.getEmail());
	}
	
	public void addAmigo(Usuario amigo){
		this.amigos.add(amigo.getEmail());
	}
	
	public void postCurtido(Usuario amigo, int post){
		String momento = this.getPostByIndex(post).getMomento();
		String notificacao = amigo.getNome() + " curtiu seu post de " + momento + ".";
		this.addNotificacao(notificacao);
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
	
	public Post getPostByIndex(int index){
		return mural.get(index);
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
}
