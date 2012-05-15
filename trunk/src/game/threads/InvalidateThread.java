package game.threads;

import android.util.Log;
import game.graphics.MainView;

public class InvalidateThread extends Thread
{
	// вью для вызова отрисовки
	private MainView mv;
	
	// состояние потока
	private boolean state;
	
	// время сна
	private int sleep;
	
	// передаем в конструктор вью
	public InvalidateThread(MainView mv, int sleep)
	{
		this.mv = mv;
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
			mv.postInvalidate();
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
