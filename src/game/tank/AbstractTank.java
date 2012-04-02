package game.tank;

import android.util.Log;
import game.field.*;
import game.graphics.MainView;
import game.main.Game;

public class AbstractTank {

	private int width;
	private int v;
	private int x,y;
	private Game g;
	private AbstractWeapon[][] weps;	 
	
	public AbstractTank(String type, int xtable, int ytable, Game game)
	{
		g = game;
		x = xtable;
		y = ytable;
		g.getField().explode(xtable, ytable);
		if(type.equalsIgnoreCase("Normal"))
		{
			width = 2;
			v=1;
			weps = new AbstractWeapon[1][1];
			return;
		}
		if(type.equalsIgnoreCase("Big"))
		{
			width = 3;
			v=1;
			weps = new AbstractWeapon[5][5];
			return;
		}
	}
	
	public void placeWeapon(String type, int xp, int yp)
	{
		boolean canplace=true;
		for(int q=xp-1;q<xp+2;q++)
		{
			for(int w=yp-1;w<yp+2;w++)
			{
				if(weps[q][w]!=null) canplace=false;
			}
		}
		if(canplace) weps[xp][yp] = new AbstractWeapon(type, this);		
	}
	
	public void shoot(int x, int y)
	{
		for(int q=0;q<weps.length;q++)
		{
			for(int w=0;w<weps[0].length;w++)
			{
				if(weps[q][w]!=null)
				{
//					
				}
			}
		}
	}
	
	public void goLeft()
	{
		for(int q=0;q<width;q++)
		{
			if(!canRide(x-1, y+q)) return; 
		}
		g.getTField().goLeft(this);
		x--;
	}
	public void goRight()
	{
		for(int q=0;q<width;q++)
		{
			if(!canRide(x+width, y+q)) return;
		}
		g.getTField().goRight(this);
		x++;
	}
	public void goDown()
	{
		for(int q=0;q<width;q++)
		{
			if(!canRide(x+q, y+width)) return; 
		}
		g.getTField().goDown(this);
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
		g.getTField().goUp(this);
		y--;
	}	
	
	private boolean canRide(int x, int y)
	{
		boolean canride = true;
		if(g.getField().get(x, y) != 0) canride = false;
		if(g.getTField().get(x, y) != null) canride = false;
		return canride;
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
		return width;
	}
	public int getWidth()
	{
		return width;
	}
}
