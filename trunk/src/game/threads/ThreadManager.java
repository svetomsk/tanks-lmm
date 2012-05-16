package game.threads;
import game.graphics.MainView;
import game.main.Game;

public class ThreadManager 
{
	// отрисовывающий поток
	private InvalidateThread it;
	
	// поток снарядов
	private ShellThread st;
	
	MainView m; 
	Game g;
	
	public ThreadManager(MainView mv, Game game)
	{		
		m = mv;
		g = game;
	}
	
	public void reborn()
	{		
		it = new InvalidateThread(m, 200/m.getFPS());
		st = new ShellThread(g, 50);
		it.setState(true);
		st.setState(true);
		it.start();
		st.start();
	}
	
	public void destroy() throws InterruptedException
	{
		it.setState(false);
		it.join();
		st.setState(false);
		st.join();
	}
}
