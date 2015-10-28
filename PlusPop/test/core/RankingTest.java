package core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import util.FabricaDePost;
import exception.ConversaoDeDataException;
import exception.CriaPostException;
import exception.EmailInvalidoException;
import exception.NomeUsuarioException;

public class RankingTest {

	
	@Test
	public void UsuarioRankTest(){

		try {
			Usuario A = new Usuario("A", "a@a.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario B = new Usuario("B", "b@b.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario C = new Usuario("C", "c@c.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario D = new Usuario("D", "d@d.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario E = new Usuario("E", "e@e.com", "aaaaa", "11/11/1111", "1.jpg");
			
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

			assertEquals("A, B, C", ranking.getRankUsuario());
			
			C.changePopularidade(1000);
			ranking.atualizaRank(usuarios);

			assertEquals("C, A, B", ranking.getRankUsuario());
			
			A.changePopularidade(1000);
			ranking.atualizaRank(usuarios);
			
			assertEquals("A, C, B", ranking.getRankUsuario());
			
			D.changePopularidade(2002);
			ranking.atualizaRank(usuarios);
			
			assertEquals("D, A, C", ranking.getRankUsuario());
			
			E.changePopularidade(1500);
			ranking.atualizaRank(usuarios);
			
			assertEquals("D, A, E", ranking.getRankUsuario());
			
			E.changePopularidade(501);
			ranking.atualizaRank(usuarios);
			
			assertEquals("D, E, A", ranking.getRankUsuario());
			
		} catch (ConversaoDeDataException | NomeUsuarioException
				| EmailInvalidoException e) {
			fail();
		}

	}
	
	@Test
	public void HashtagRankingTest(){
		try {
			
			List<Usuario> usuarios = new ArrayList<Usuario>();
			
			Usuario A = new Usuario("A", "a@a.com", "aaaaa", "11/11/1111", "1.jpg");
			Usuario B = new Usuario("B", "b@b.com", "aaaaa", "11/11/1111", "1.jpg");
			
			usuarios.add(A);
			usuarios.add(B);
			
			A.addPost(FabricaDePost.getInstance().construirPost("teste #java", "11/12/1212 12:12:12"));
			A.addPost(FabricaDePost.getInstance().construirPost("teste #java", "11/12/1212 12:12:12"));
			A.addPost(FabricaDePost.getInstance().construirPost("teste #java", "11/12/1212 12:12:12"));
			B.addPost(FabricaDePost.getInstance().construirPost("teste #cpp", "11/12/1212 12:12:12"));
			B.addPost(FabricaDePost.getInstance().construirPost("teste #cpp", "11/12/1212 12:12:12"));
			B.addPost(FabricaDePost.getInstance().construirPost("teste #php", "11/12/1212 12:12:12"));
			
			Ranking ranking = new Ranking();
			
			ranking.atualizaRank(usuarios);
			
			assertEquals(ranking.getRankHashtag(), "#java, #cpp, #php");
			
			A.addPost(FabricaDePost.getInstance().construirPost("teste #cpp", "11/12/1212 12:12:12"));
			B.addPost(FabricaDePost.getInstance().construirPost("teste #cpp", "11/12/1212 12:12:12"));
			ranking.atualizaRank(usuarios);

			assertEquals(ranking.getRankHashtag(), "#cpp, #java, #php");
			
			B.addPost(FabricaDePost.getInstance().construirPost("teste #php", "11/12/1212 12:12:12"));
			A.addPost(FabricaDePost.getInstance().construirPost("teste #php", "11/12/1212 12:12:12"));
			A.addPost(FabricaDePost.getInstance().construirPost("teste #php", "11/12/1212 12:12:12"));
			A.addPost(FabricaDePost.getInstance().construirPost("teste #php", "11/12/1212 12:12:12"));

			ranking.atualizaRank(usuarios);
			
			assertEquals(ranking.getRankHashtag(), "#php, #cpp, #java");
			
			B.addPost(FabricaDePost.getInstance().construirPost("teste #js", "11/12/1212 12:12:12"));
			A.addPost(FabricaDePost.getInstance().construirPost("teste #js", "11/12/1212 12:12:12"));
			B.addPost(FabricaDePost.getInstance().construirPost("teste #js", "11/12/1212 12:12:12"));
			B.addPost(FabricaDePost.getInstance().construirPost("teste #js", "11/12/1212 12:12:12"));
			A.addPost(FabricaDePost.getInstance().construirPost("teste #php", "11/12/1212 12:12:12"));
			A.addPost(FabricaDePost.getInstance().construirPost("teste #js", "11/12/1212 12:12:12"));
			B.addPost(FabricaDePost.getInstance().construirPost("teste #js", "11/12/1212 12:12:12"));
			B.addPost(FabricaDePost.getInstance().construirPost("teste #js", "11/12/1212 12:12:12"));
			ranking.atualizaRank(usuarios);
			
			assertEquals(ranking.getRankHashtag(), "#js, #php, #cpp");
		} catch (ConversaoDeDataException | NomeUsuarioException
				| EmailInvalidoException | CriaPostException e) {
			fail();
		}
		
	}

}
