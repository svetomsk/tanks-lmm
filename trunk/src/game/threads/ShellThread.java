package game.threads;

import game.main.Game;
import game.tank.Shell;
import android.util.Log;

public class ShellThread extends Thread
{
	// класс для обращения к снарядам
	private Game game;
	
	// состояние потока
	private boolean state;
	
	// время сна
	private int sleep;
	
	public ShellThread(Game game, int sleep)
	{
		this.game = game;
		state = false;
		this.sleep = sleep;
	}
	
	// метод изменения состояния потока
	public void setState(boolean value)
	{
		state = value;
	}
	
	// собственно тело потока
	public void run()
	{
		while(state)
		{			
			for(int i = 0; i < game.getShellCount(); i++)
			{
				Shell as = game.getShell(i);
				as.step();
				if(game.isExplode(as))
				{
					game.explode(as.getGX(), as.getGY(), as.getPower());
					game.removeShell(i);
				}
			}
			try 
			{
				Thread.sleep(sleep);
			} catch (InterruptedException e) 
			{
				Log.e("InvalidateThread", "InterrupterExceptino");
			}
		}
	}
}