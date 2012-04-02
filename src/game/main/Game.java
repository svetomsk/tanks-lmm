package game.main;

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
	private List<AbstractShell> shells;
	
	public Game(Field f, int xm, int ym, String typem, MainView mv)
	{
		field = f;
		tfield = new TanksField(f.getWidth(), f.getHeight());
		mainTank = new AbstractTank(typem, xm, ym, this);
		tfield.spawnTank(mainTank);
		tfield.spawnTank(new AbstractTank("Big", 10, 4, this));
		view = mv;
		
//		shells = new List<AbstractShell>() {};
//		Log.d("000000000", "Shooting");
//		shoot(new AbstractShell(9, 9, 0, 0, 4, 4, mainTank, 16));
		
//		start();
	}
	
	private void start()
	{
		while(true)
		{
			for(int q=0;q<getShells().size();q++)
			{
				getShells().get(q).step();
//				if(field.get(getShells().get(q).getGX(), getShells().get(q).getGY()) != 0 ||)
			}		
			view.view();
		}	
	}
	
	public void shoot(AbstractShell shell)
	{
		shells.add(shell);
		Log.d("000000000", "Shooted");
	}

	public List<AbstractShell> getShells()
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
