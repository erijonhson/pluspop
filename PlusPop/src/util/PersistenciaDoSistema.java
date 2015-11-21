package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import core.DepositoDeUsuarios;

/**
 * Classe que escreve um objeto no arquivo
 * @author ordan
 *
 */
public class PersistenciaDoSistema {
	
	private static final String objectfilename = "./arquivos/system.dat";
	
	/**
	 * Salva o depósito de usuários em um arquivo
	 * @param depositoDeUsuarios
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void save(DepositoDeUsuarios depositoDeUsuarios) throws FileNotFoundException, IOException{
		
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(objectfilename))){
			oos.writeObject(depositoDeUsuarios);
		}
		
	}
	
	/**
	 * Carrega o depósito de usuários
	 * @return
	 */
	public DepositoDeUsuarios load(){

		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objectfilename))){
			DepositoDeUsuarios deposito = (DepositoDeUsuarios) ois.readObject();
			return deposito;
		/*
		 * Mesmo vazio deve retornar algo
		 */
		} catch (Exception e){
			return new DepositoDeUsuarios();
		}
		
	}
	
}
