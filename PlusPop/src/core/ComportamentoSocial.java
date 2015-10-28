package core;

import java.util.List;

/**
 * Interface que trata a dinamicidade nas curtidas e rejeições
 * 
 * @author ordan
 */
public interface ComportamentoSocial {

	public void curtir(Post post);

	public void rejeitar(Post post);
	
	public List<Post> compartilhar(List<Post> posts);

}
