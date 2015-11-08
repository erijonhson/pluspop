package util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import core.Post;

public class ManipuladorDeArquivoTest {

	private static String src = "teste.txt";

	@Test
	public void testeSimples() throws Exception {
		List<Post> posts = new ArrayList<Post>();
		posts.add(FabricaDePost.getInstance().construirPost("teste#gfgf",
				"11/11/1111 11:11:11"));
		posts.add(FabricaDePost.getInstance().construirPost("teste#gfga",
				"11/11/1111 11:11:11"));

		File srcFile = new File(src);
		assertFalse(srcFile.exists());

		ManipuladorDeArquivo.save(posts, src);

		assertTrue(srcFile.exists());
		srcFile.delete();
		assertFalse(srcFile.exists());
	}

}
