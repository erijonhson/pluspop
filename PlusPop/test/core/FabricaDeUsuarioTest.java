package core;

import static org.junit.Assert.*;

import org.junit.Test;

import util.FabricaDeUsuario;
import exception.ConversaoDeDataException;
import exception.EmailInvalidoException;
import exception.NomeUsuarioException;

public class FabricaDeUsuarioTest {

	@Test
	public void construtor() {
		try {
			FabricaDeUsuario.getInstance().
				construirUsuario("", "usuario@exemplo.com", "abc", "01/01/1989", "/opt/images");
			fail("Nome dx usuarix nao pode ser vazio era esperado.");
		} catch (NomeUsuarioException | EmailInvalidoException | ConversaoDeDataException e) {
			assertEquals("Nome dx usuarix nao pode ser vazio.", e.getMessage());
		}
		try {
			FabricaDeUsuario.getInstance().
				construirUsuario("    ", "usuario@exemplo.com", "abc", "01/01/1989", "/opt/images");
			fail("Nome dx usuarix nao pode ser vazio era esperado.");
		} catch (NomeUsuarioException | EmailInvalidoException | ConversaoDeDataException e) {
			assertEquals("Nome dx usuarix nao pode ser vazio.", e.getMessage());
		}
		try {
			FabricaDeUsuario.getInstance().
				construirUsuario("Usuario", "usuarioexemplo.com", "abc", "01/01/1989", "/opt/images");
			fail("Formato de e-mail esta invalido era esperado.");
		} catch (NomeUsuarioException | EmailInvalidoException | ConversaoDeDataException e) {
			assertEquals("Formato de e-mail esta invalido.", e.getMessage());
		}
		try {
			FabricaDeUsuario.getInstance().
				construirUsuario("Usuario", "usuario@exemplo.com", "abc", "1510/10/2010", "/opt/images");
			fail("Formato de data esta invalida era esperado.");
		} catch (NomeUsuarioException | EmailInvalidoException | ConversaoDeDataException e) {
			assertEquals("Formato de data esta invalida.", e.getMessage());
		}
		try {
			FabricaDeUsuario.getInstance().
				construirUsuario("Usuario", "usuario@exemplo.com", "abc", "01/04/199s", "/opt/images");
			fail("Formato de data esta invalida era esperado.");
		} catch (NomeUsuarioException | EmailInvalidoException | ConversaoDeDataException e) {
			assertEquals("Formato de data esta invalida.", e.getMessage());
		}
		try {
			FabricaDeUsuario.getInstance().
				construirUsuario("Usuario", "usuario@exemplo.com", "abc", "32/10/2010", "/opt/images");
			fail("Data nao existe era esperado.");
		} catch (NomeUsuarioException | EmailInvalidoException | ConversaoDeDataException e) {
			assertEquals("Data nao existe.", e.getMessage());
		}
	}

}
