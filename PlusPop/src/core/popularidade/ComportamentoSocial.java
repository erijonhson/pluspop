package core.popularidade;

import java.io.Serializable;

import core.Post;
import exception.HashTagException;

/**
 * Interface que trata a dinamicidade nas curtidas e rejeições
 * 
 * @author ordan
 */
public interface ComportamentoSocial extends Serializable{

	public void curtir(Post post) throws HashTagException;

	public void rejeitar(Post post) throws HashTagException;
	
	public int qtdParaCompartilhar();

}
