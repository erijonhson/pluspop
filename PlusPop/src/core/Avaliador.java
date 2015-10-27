package core;

/**
 * Interface que trata a dinamicidade nas curtidas e rejeições 
 * @author ordan
 *
 */
public interface Avaliador {
	
	public void curtir(Post post);
	
	public void rejeitar(Post post);
	
}
