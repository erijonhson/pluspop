package exception;

public class ErroNaConsultaDePopsException extends Exception{

	private static final long serialVersionUID = 1242577961873174088L;
	
	private static final String msg = "Erro na consulta de Pops.";
	
	public ErroNaConsultaDePopsException() {
		super (msg);
	}
	
	public ErroNaConsultaDePopsException (Exception e){
		super (msg + " " + e.getMessage(), e.getCause());
	}
}
