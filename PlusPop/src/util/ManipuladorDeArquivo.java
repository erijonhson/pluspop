package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import core.Post;

public class ManipuladorDeArquivo {

	public static void save(List<Post> posts, String src)
			throws FileNotFoundException {

		PrintWriter pw = new PrintWriter(src);

		for (int i = 0; i < posts.size(); i++) {
			pw.print(posts.get(i).toFileFormat());
		}

		pw.close();
	}

}
