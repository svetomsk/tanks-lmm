package game.tank;

import android.util.Log;
import game.field.*;
import game.graphics.MainView;

public class AbstractTank {

	private int width, length;
	private int v;
	private int x,y;
	private Field field;
	
	public AbstractTank(Field f, String type, int xtable, int ytable)
	{
		field = f;
		x = xtable;
		y = ytable;
		field.explode(xtable, ytable);
		if(type.equalsIgnoreCase("Normal"))
		{
			length = 1;
			width = 1;
			v=1;
			return;
		}
		if(type.equalsIgnoreCase("Big"))
		{
			length = 2;
			width = 3;
			v=1;
			return;
		}
	}
	
	public void goLeft()
	{
		for(int q=0;q<length;q++)
		{
			if(!canRide(x-1, y+q)) return; 
		}
		x--;
	}
	public void goRight()
	{
		for(int q=0;q<length;q++)
		{
			if(!canRide(x+width, y+q)) return;
		}
		x++;
	}
	public void goDown()
	{
		for(int q=0;q<width;q++)
		{
			if(!canRide(x+q, y+length)) return; 
		}
		y++;
	}
	public void goUp()
	{
		for(int q=0;q<width;q++)
		{
			if(!canRide(x+q, y-1)) 
			{
				return; 
			}
		}
		y--;
	}	
	
	private boolean canRide(int x, int y)
	{
		if(field.get(x, y) == 0) return true;
		return false;
	}	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getLength()
	{
		return length;
	}
	public int getWidth()
	{
		return width;
	}
}
