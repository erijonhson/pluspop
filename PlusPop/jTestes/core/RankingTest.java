package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import exception.ConversaoDeDataException;
import exception.CriaPostException;
import exception.EmailInvalidoException;
import exception.NomeUsuarioException;
import util.FabricaDeUsuario;

public class RankingTest {

	
	@Test
	public void UsuarioRankTest(){

		try {
			Usuario A = FabricaDeUsuario.getInstance().
					construirUsuario("A", "a@a.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario B = FabricaDeUsuario.getInstance().
					construirUsuario("B", "b@b.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario C = FabricaDeUsuario.getInstance().
					construirUsuario("C", "c@c.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario D = FabricaDeUsuario.getInstance().
					construirUsuario("D", "d@d.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario E = FabricaDeUsuario.getInstance().
					construirUsuario("E", "e@e.com", "aaaaa", "11/11/1111", "1.jpg");
			
			Ranking ranking = new Ranking();
			
			List<Usuario> usuarios = new ArrayList<Usuario>();
			
			usuarios.add(A);
			usuarios.add(B);
			usuarios.add(C);
			usuarios.add(D);
			usuarios.add(E);
			
			A.changePopularidade(1000);
			B.changePopularidade(500);
			C.changePopularidade(100);
			
			ranking.atualizaRank(usuarios);

			assertEquals(
					"Mais Populares: (1) A 1000; (2) B 500; (3) C 100; | "
					+ "Menos Populares: (1) D 0; (2) E 0; (3) C 100;", 
					ranking.getRankUsuario());
			
			C.changePopularidade(1000);
			ranking.atualizaRank(usuarios);

			assertEquals("Mais Populares: (1) C 1100; (2) A 1000; (3) B 500; | "
					+ "Menos Populares: (1) D 0; (2) E 0; (3) B 500;", 
					ranking.getRankUsuario());
			
			A.changePopularidade(1000);
			ranking.atualizaRank(usuarios);
			
			assertEquals("Mais Populares: (1) A 2000; (2) C 1100; (3) B 500; | "
					+ "Menos Populares: (1) D 0; (2) E 0; (3) B 500;", 
					ranking.getRankUsuario());
			
			D.changePopularidade(2002);
			ranking.atualizaRank(usuarios);
			
			assertEquals("Mais Populares: (1) D 2002; (2) A 2000; (3) C 1100; | "
					+ "Menos Populares: (1) E 0; (2) B 500; (3) C 1100;", 
					ranking.getRankUsuario());
			
			E.changePopularidade(1500);
			ranking.atualizaRank(usuarios);
			
			assertEquals("Mais Populares: (1) D 2002; (2) A 2000; (3) E 1500; | "
					+ "Menos Populares: (1) B 500; (2) C 1100; (3) E 1500;", 
					ranking.getRankUsuario());
			
			E.changePopularidade(501);
			ranking.atualizaRank(usuarios);
			
			assertEquals("Mais Populares: (1) D 2002; (2) E 2001; (3) A 2000; | "
					+ "Menos Populares: (1) B 500; (2) C 1100; (3) A 2000;", 
					ranking.getRankUsuario());
			
		} catch (ConversaoDeDataException | NomeUsuarioException
				| EmailInvalidoException e) {
			fail();
		}

	}
	
	@Test
	public void HashtagRankingTest(){
		try {
			
			List<Usuario> usuarios = new ArrayList<Usuario>();
			
			Usuario A = FabricaDeUsuario.getInstance().
					construirUsuario("A", "a@a.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario B = FabricaDeUsuario.getInstance().
					construirUsuario("B", "b@b.com", "aaaaa", "11/11/1111", "1.jpg");
			
			usuarios.add(A);
			usuarios.add(B);
			
			A.criaPost("teste #java", "11/12/1212 12:12:12");
			A.criaPost("teste #java", "11/12/1212 12:12:12");
			A.criaPost("teste #java", "11/12/1212 12:12:12");
			B.criaPost("teste #cpp", "11/12/1212 12:12:12");
			B.criaPost("teste #cpp", "11/12/1212 12:12:12");
			B.criaPost("teste #php", "11/12/1212 12:12:12");
			
			Ranking ranking = new Ranking();
			
			ranking.atualizaRank(usuarios);
			
			assertEquals(
					"Trending Topics:  (1) #java: 3; (2) #cpp: 2; (3) #php: 1;", 
					ranking.getRankHashtag());
			
			A.criaPost("teste #cpp", "11/12/1212 12:12:12");
			B.criaPost("teste #cpp", "11/12/1212 12:12:12");
			ranking.atualizaRank(usuarios);

			assertEquals(
					"Trending Topics:  (1) #cpp: 4; (2) #java: 3; (3) #php: 1;", 
					ranking.getRankHashtag());
			
			B.criaPost("teste #php", "11/12/1212 12:12:12");
			A.criaPost("teste #php", "11/12/1212 12:12:12");
			A.criaPost("teste #php", "11/12/1212 12:12:12");
			A.criaPost("teste #php", "11/12/1212 12:12:12");

			ranking.atualizaRank(usuarios);
			
			assertEquals(
					"Trending Topics:  (1) #php: 5; (2) #cpp: 4; (3) #java: 3;",
					ranking.getRankHashtag());
			
			B.criaPost("teste #js", "11/12/1212 12:12:12");
			A.criaPost("teste #js", "11/12/1212 12:12:12");
			B.criaPost("teste #js", "11/12/1212 12:12:12");
			B.criaPost("teste #js", "11/12/1212 12:12:12");
			A.criaPost("teste #php", "11/12/1212 12:12:12");
			A.criaPost("teste #js", "11/12/1212 12:12:12");
			B.criaPost("teste #js", "11/12/1212 12:12:12");
			B.criaPost("teste #js", "11/12/1212 12:12:12");
			ranking.atualizaRank(usuarios);
			
			assertEquals(
					"Trending Topics:  (1) #js: 7; (2) #php: 6; (3) #cpp: 4;",
					ranking.getRankHashtag());
		} catch (ConversaoDeDataException | NomeUsuarioException
				| EmailInvalidoException | CriaPostException e) {
			fail();
		}
		
	}

}
