package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Fabrica para gerar clones de objetos. <br>
 * A única restrição é que sejam objetos serializáveis. <br>
 * Sobre singleton: http://www.devmedia.com.br/trabalhando-com-singleton-java/23632 <br>
 * 
 * @author Eri Jonhson
 */
public class FabricaDeClone {

	private static FabricaDeClone instance;

	public static synchronized FabricaDeClone getInstance() {
		if (instance == null)
			instance = new FabricaDeClone();
		return instance;
	}

	/**
	 * Clonar um objeto serializável.
	 * 
	 * @author Guilherme Trovo <br>
	 * Disponível em: http://www.guj.com.br/java/250021-resolvido-clonar-objeto
	 */
	public Object cloneSerializable(Serializable obj) {
		ObjectOutputStream out = null;
		ObjectInputStream in = null;

		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bout);

			out.writeObject(obj);
			out.close();

			ByteArrayInputStream bin = new ByteArrayInputStream(
					bout.toByteArray());
			in = new ObjectInputStream(bin);
			Object copy = in.readObject();

			in.close();

			return copy;
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Erro improvavel aconteceu.");
		} finally {
			try {
				if (out != null) {
					out.close();
				}

				if (in != null) {
					in.close();
				}
			} catch (IOException ignore) {
			}
		}

		return null;
	}

}
