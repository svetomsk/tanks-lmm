package game.tank;

import game.tank.*;

public class Lazer {
	private int stepx, stepy;
	private int gx, gy;
	private int lx, ly;
	private Tank at;
	private int loc;
	private int power;
	
	public Lazer(int gx, int gy, int lx, int ly, int stepx, int stepy, Tank at, int loc)
	{
		this.stepx = stepx;
		this.stepy = stepy;
		
		this.gx = gx;
		this.gy = gy;
		this.lx = lx;
		this.ly = ly;
		
		this.at = at;
		this.loc = loc;
		
		power = 20;
	}
	
	// единичное перемещение заряда
	public void step()
	{
		lx+=stepx;
		ly+=stepy;
		
		if(lx>=loc) { lx-=loc; gx++; }
		if(lx<0) { lx+=loc; gx--; }
		if(ly>=loc) { ly-=loc; gy++; }
		if(ly<0) { ly+=loc; gy--; }
	}
	
	// вспомогательные get-методы
	public Tank getTank()
	{
		return at;
	}
	public int getPower()
	{
		return power;
	}
	public int getGX()
	{
		return gx;
	}
	public int getGY()
	{
		return gy;
	}
	public int getLX()
	{
		return lx;
	}
	public int getLY()
	{
		return ly;
	}
	public int getSize()
	{
		return loc;
	}

}
