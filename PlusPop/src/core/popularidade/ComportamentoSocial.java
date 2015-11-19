package core.popularidade;

import java.util.List;

import core.Post;

/**
 * Interface que trata a dinamicidade nas curtidas e rejeições
 * 
 * @author ordan
 */
public interface ComportamentoSocial {

	public void curtir(Post post);

	public void rejeitar(Post post);
	
	public int qtdParaCompartilhar();	

}
