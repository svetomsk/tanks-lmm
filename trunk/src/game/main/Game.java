package game.main;

import game.field.Field;
import game.field.TanksField;
import game.graphics.MainView;
import game.input.Input;
import game.tank.Shell;
import game.tank.Tank;

import java.util.ArrayList;

public class Game {
	
	private Tank mainTank;
	private Field field;
	private TanksField tfield;
	private ArrayList<Shell> shells;
	
	private Input input;
	private MainView mv;
	
	public Game(Field f, int xm, int ym, String typem, MainView mv)
	{
		field = f;
		tfield = new TanksField(f.getWidth(), f.getHeight());
		mainTank = new Tank(typem, xm, ym, this);
		tfield.spawnTank(mainTank);
		tfield.spawnTank(new Tank("Big", 10, 3, this));
		shells = new ArrayList<Shell>();
		
		input = new Input(mv);
		this.mv = mv;
	}

	public int getShellCount()
	{
		return shells.size();
	}
	
	public void shellStep(int index)
	{
		shells.get(index).step();
	}
	
	public Shell getShell(int index)
	{
		return shells.get(index);
	}
	public Input getInput()
	{
		return input;
	}
	public MainView getMainView()
	{
		return mv;
	}
	
	public void removeShell(int index)
	{
		shells.remove(index);
	}

	// проверяем надо ли взрывать
	public boolean isExplode(Shell as)
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
