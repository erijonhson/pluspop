package core.popularidade;

import core.Post;
import exception.HashTagException;

/**
 * Interface que trata a dinamicidade nas curtidas e rejeições
 * 
 * @author ordan
 */
public interface ComportamentoSocial {

	public void curtir(Post post) throws HashTagException;

	public void rejeitar(Post post) throws HashTagException;
	
	public int qtdParaCompartilhar();

}
