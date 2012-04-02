package game.field;

import game.tank.AbstractTank;

public class TanksField {
	private AbstractTank[][] arr;
	
	public TanksField(int width, int height)
	{
		arr = new AbstractTank[width][height];
	}
	
	public void spawnTank(AbstractTank at)
	{
		int width = at.getWidth();
		int x = at.getX();
		int y = at.getY();
		for(int q=0;q<width;q++)
		{
			for(int w=0;w<width;w++)
			{
				arr[q+x][w+y] = at;
			}
		}
	}
	public void goLeft(AbstractTank at)
	{
		int x = at.getX();
		int y = at.getY();
		int width = at.getWidth();
		
		for(int q=0;q<width;q++)
		{
			arr[x-1][y+q] = at;
			arr[x+width-1][y+q] = null;
		}
	}
	public void goRight(AbstractTank at)
	{
		int x = at.getX();
		int y = at.getY();
		int width = at.getWidth();
		
		for(int q=0;q<width;q++)
		{
			arr[x+width][y+q] = at;
			arr[x][y+q] = null;
		}
	}
	public void goUp(AbstractTank at)
	{
		int x = at.getX();
		int y = at.getY();
		int width = at.getWidth();
		
		for(int q=0;q<width;q++)
		{
			arr[x+q][y-1] = at;
			arr[x+q][y+width-1] = null;
		}
	}
	public void goDown(AbstractTank at)
	{
		int x = at.getX();
		int y = at.getY();
		int width = at.getWidth();
		
		for(int q=0;q<width;q++)
		{
			arr[x+q][y+width] = at;
			arr[x+q][y] = null;
		}
	}
	
	
	
	public AbstractTank get(int x, int y)
	{
		return arr[x][y];
	}
}
