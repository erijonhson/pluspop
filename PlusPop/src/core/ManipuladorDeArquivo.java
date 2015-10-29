package core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ManipuladorDeArquivo {
	
	public static void save (List<Post> posts) throws FileNotFoundException{
		

		PrintWriter pw = new PrintWriter("teste.txt");
		
		for (int i = 0; i < posts.size(); i++){
			pw.print(posts.get(i).toFileFormat());
		}
		
		pw.close();
	}
	
}
