package game.threads;

import game.main.Game;
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
//			int n = game.getShells().size();	
//			for(int i = 0; i < n; i++)
//			{
//				try
//				{
//					game.shellStep(i);
//					if(game.isExplode(game.getShell(i)))
//					{
//						game.explode(game.getShell(i).getGX(), game.getShell(i).getGY(), game.getShell(i).getPower());
//						game.removeShell(i);
//					}
//				}catch(Exception excs) {}
//			}
			game.getInput().update(game.getMainView().getPointers());
			game.getInput().change();
			checkShells();
			game.getMainView().postInvalidate();
			try 
			{
				Thread.sleep(sleep);
			} catch (InterruptedException e) 
			{
				Log.e("InvalidateThread", "InterrupterExceptino");
			}
		}
	}
	private void checkShells()
	{	
		int n = game.getShells().size();	
		for(int i = 0; i < n; i++)
		{
			try
			{
				game.shellStep(i);
				if(game.isExplode(game.getShell(i)))
				{
					game.explode(game.getShell(i).getGX(), game.getShell(i).getGY(), game.getShell(i).getPower());
					game.removeShell(i);
				}
			}catch(Exception excs) {}
		}		
	}
}