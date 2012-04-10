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
	private AbstractWeapon[][] weps; // карта размещения оружия 
	
	public AbstractTank(String type, int xtable, int ytable, Game game)
	{
		g = game;
		x = xtable;
		y = ytable;
		if(type.equalsIgnoreCase("Normal"))
		{
			width = 2;
			v=1;
			weps = new AbstractWeapon[3][3];
			placeWeapon("Normal", 1, 1);
		}
		if(type.equalsIgnoreCase("Big"))
		{
			width = 3;
			v=1;
			weps = new AbstractWeapon[5][5];
		}
		if(type.equalsIgnoreCase("Boss"))
		{
			width = 9;
			v = 1;
			weps = new AbstractWeapon[17][17];
			for(int q=1;q<8;q++)
			{
				for(int w=1;w<8;w++)
				{
					placeWeapon("Normal", q*2, w*2);
				}
			}
		}
		// зачищаем площадку под появление танка
		for(int q=0;q<width;q++)
			for(int w=0;w<width;w++)
			{
				g.explode(x+q, y+w, 3);
			}
	}
	
	// метод размещения оружия на ранке
	public void placeWeapon(String type, int xp, int yp)
	{
		boolean canplace=true;
		// проверяем на возможность размещения оружия в данной точке
		for(int q=xp-1;q<xp+2;q++)
		{
			for(int w=yp-1;w<yp+2;w++)
			{
				if(weps[q][w]!=null) canplace=false;
			}
		}
		// если можно разместить -> размещаем
		if(canplace) weps[xp][yp] = new AbstractWeapon(type, this, 256, 160);		
	}
	
	// выстрел
	public void shoot(int tx, int ty)
	{
		for(int q=0;q<weps.length;q++)
		{
			for(int w=0;w<weps[0].length;w++)
			{
				if(weps[q][w]!=null)
				{
					int loc = weps[q][w].getLoc();
					int r = weps[q][w].getStepLength();
					int gx = x+q/2+1;
					int gy = y+w/2+1;
					double a = Math.sqrt((tx-gx)*(tx-gx) + (ty-gy)*(ty-gy));
					if(!(gx == tx && gy == ty))
						g.shoot(new AbstractShell(gx, gy, (loc/2)*((q+1)%2), (loc/2)*((w+1)%2), (int)(r*(tx-gx)/a), (int)(r*(ty-gy)/a), this, loc));
				}
			}
		}
	}
	// движение танка
	public void move(int value)
	{
		for(int q = 0; q < width; q++)
		{
			int a = 0, b = 0;
			switch(value)
			{
			case 0: 
			{
				a = x+width; 
				b = y+q;
				break;
			}
			case 1: 
			{
				a = x + q;
				b = y - 1;
				break;
			}
			case 2: 
			{
				a = x - 1; 
				b = y + q;
			}
			case 3:
			{
				a = x+q; 
				b= y+width;
				break;
			}
			}		
			if(!canRide(a, b)) return;
		}
		g.getTField().go(this, value);
		switch(value)
		{
			case 0: {x++; break;}
			case 1: {y--; break;}
			case 2: {x--; break;}
			case 3: {y++; break;}
		}
	}
	
	private boolean canRide(int x, int y)
	{
		boolean canride = true;
		if(g.getField().get(x, y) != 0) canride = false;
		if(g.getTField().get(x, y) != null) canride = false;
		return canride;
	}	
	
	// вспомагательные get-методы
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
