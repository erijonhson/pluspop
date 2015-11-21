package core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import util.FabricaDePost;
import util.FabricaDeUsuario;
import exception.ConversaoDeDataException;
import exception.CriaPostException;
import exception.EmailInvalidoException;
import exception.NomeUsuarioException;

public class FeedTest {

	@Test
	public void test() {
		try {
			Usuario u = FabricaDeUsuario.getInstance().
					construirUsuario("u", "u@u.com", "uuuuu", "11/11/1111", "1.jpg");
			Usuario normal = FabricaDeUsuario.getInstance().
					construirUsuario("normal", "normal@u.com", "uuuuu", "11/11/1111", "1.jpg");
			Usuario pop = FabricaDeUsuario.getInstance().
					construirUsuario("u", "pop@u.com", "uuuuu", "11/11/1111", "1.jpg");
			pop.changePopularidade(500);
			Usuario icone = FabricaDeUsuario.getInstance().
					construirUsuario("u", "icone@u.com", "uuuuu", "11/11/1111", "1.jpg");
			icone.changePopularidade(1001);
			
			u.addAmigo(normal);
			u.addAmigo(pop);
			u.addAmigo(icone);
			
			u.updateFeed();
			List<Post> feed = new ArrayList<Post>();
			
			Post a = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1222 22:22:22");
			Post b = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1223 22:22:22");
			Post c = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1224 22:22:22");
			
			c.addPopularidade(1000);
			b.addPopularidade(2000);
			
			normal.addPost(a);
			normal.addPost(b);
			normal.addPost(c);
			
			feed.add(0, b);
			feed.add(0, c);
			
			u.updateFeed();
			assertEquals(feed, u.getFeed());
			
			Post d = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1225 22:22:22");
			Post e = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1226 22:22:22");
			Post f = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1227 22:22:22");
			Post g = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1228 22:22:22");
			Post h = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1229 22:22:22");
			
			e.addPopularidade(500);
			h.addPopularidade(200);
			g.addPopularidade(300);
			f.addPopularidade(400);
			
			pop.addPost(d);
			pop.addPost(e);
			pop.addPost(f);
			pop.addPost(g);
			pop.addPost(h);
			
			feed.add(0, e);
			feed.add(0, f);
			feed.add(0, g);
			feed.add(0, h);
			
			u.updateFeed();
			assertEquals(feed, u.getFeed());
			
			Post i = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1232 22:22:22");
			Post j = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1242 22:22:22");
			Post k = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1252 22:22:22");
			Post l = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1262 22:22:22");
			Post m = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1272 22:22:22");
			Post n = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1282 22:22:22");
			Post o = FabricaDePost.getInstance().construirPost("ateste #teste", "11/11/1292 22:22:22");
			
			j.addPopularidade(60);
			k.addPopularidade(50);
			l.addPopularidade(40);
			m.addPopularidade(30);
			n.addPopularidade(20);
			o.addPopularidade(10);
			
			icone.addPost(i);
			icone.addPost(j);
			icone.addPost(k);
			icone.addPost(l);
			icone.addPost(m);
			icone.addPost(n);
			icone.addPost(o);
			
			feed.add(0, j);
			feed.add(0, k);
			feed.add(0, l);
			feed.add(0, m);
			feed.add(0, n);
			feed.add(0, o);

			u.updateFeed();
			assertEquals(feed, u.getFeed());
			
			u.setFeedPorPopularidade();
			
			feed.clear();
			
			feed.add(b);
			feed.add(c);
			
			feed.add(e);
			feed.add(f);
			feed.add(g);
			feed.add(h);
			
			feed.add(j);
			feed.add(k);
			feed.add(l);
			feed.add(m);
			feed.add(n);
			feed.add(o);
			u.updateFeed();
			
			assertEquals(feed, u.getFeed());
			
		} catch (ConversaoDeDataException | NomeUsuarioException
				| EmailInvalidoException | CriaPostException e) {
			fail();
			e.printStackTrace();
		}
		
	}

}
