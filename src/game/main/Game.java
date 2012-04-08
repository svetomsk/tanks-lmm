package game.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.util.Log;

import game.field.Field;
import game.field.TanksField;
import game.graphics.MainView;
import game.tank.AbstractShell;
import game.tank.AbstractTank;

public class Game {
	
	private MainView view;
	private AbstractTank mainTank;
	private Field field;
	private TanksField tfield;
	private ArrayList<AbstractShell> shells;
	
	public Game(Field f, int xm, int ym, String typem, MainView mv)
	{
		field = f;
		tfield = new TanksField(f.getWidth(), f.getHeight());
		mainTank = new AbstractTank(typem, xm, ym, this);
		tfield.spawnTank(mainTank);
		tfield.spawnTank(new AbstractTank("Big", 10, 3, this));
		view = mv;
		
		shells = new ArrayList<AbstractShell>() {};
//		Log.d("000000000", "Shooting");
		shoot(new AbstractShell(3, 3, 0, 0, 1 , 1, mainTank, 3));
		shoot(new AbstractShell(2, 3, 0, 0, 1, 1, mainTank, 3));
		shellThread();
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
						AbstractShell as = shells.get(i);
						as.step();
						if(isExplode(as))
						{
							explode(as.getGX(), as.getGY(), as.getPower());
//							shells.remove(i);
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
	
	private boolean isExplode(AbstractShell as)
	{
		if(field.isExplodable(as.getGX(), as.getGY())) return true;
		if(tfield.get(as.getGX(), as.getGY()) != null ) return true;
		return false;
	}
	
	public void explode(int x, int y, int power)
	{
		field.explode(x, y, power);
//		tfield.explode(tfield.get(x, y));
	}
	
	public void shoot(AbstractShell shell)
	{
		shells.add(shell);
		Log.d("000000000", "Shooted");
	}

	public ArrayList<AbstractShell> getShells()
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
	public AbstractTank getMainTank()
	{
		return mainTank;
	}
}
