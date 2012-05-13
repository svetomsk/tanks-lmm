package game.main;

import game.field.Field;
import game.field.TanksField;
import game.graphics.MainView;
import game.tank.Shell;
import game.tank.Tank;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import android.util.Log;

public class Game {
	
	private MainView view;
	private Tank mainTank;
	private Field field;
	private TanksField tfield;
	private ArrayList<Shell> shells;
	
	public Game(Field f, int xm, int ym, String typem, MainView mv)
	{
		field = f;
		tfield = new TanksField(f.getWidth(), f.getHeight());
		mainTank = new Tank(typem, xm, ym, this);
		tfield.spawnTank(mainTank);
		tfield.spawnTank(new Tank("Big", 10, 3, this));
		view = mv;
		shells = new ArrayList<Shell>();
		shellThread(); // запускаем поток, который отрисовывает снаряды
	}
	
	public void shellThread()
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				while(true)
				{
					for(int i = 0; i < shells.size(); i++)
					{
						Shell as = shells.get(i);
						as.step();
						if(isExplode(as))
						{
							explode(as.getGX(), as.getGY(), as.getPower());
							shells.remove(i);
						}
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						Log.d("EXCEPTION", "LINE 67 G");
					}
				}
			}
		}).start();
	}
	
	// проверяем надо ли взрывать
	private boolean isExplode(Shell as)
	{
		if(field.isExplodable(as.getGX(), as.getGY())) return true;
		if(tfield.get(as.getGX(), as.getGY()) != null  && tfield.get(as.getGX(), as.getGY()) != as.getTank()) return true;
		return false;
	}
	// взрываем
	public void explode(int x, int y, int power)
	{
		field.explode(x, y, power);
		tfield.explode(tfield.get(x, y), power);
	}
	
	// выстрел
	public void shoot(Shell shell)
	{
		shells.add(shell);
	}

	// вспомогательные методы
	public ArrayList<Shell> getShells()
	{
		return shells;
	}
	public Field getField()
	{
		return field;
	}
	public TanksField getTField()
	{
		return tfield;
	}
	public Tank getMainTank()
	{
		return mainTank;
	}
}
