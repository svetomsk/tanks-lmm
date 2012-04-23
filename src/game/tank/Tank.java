package game.tank;

import game.main.Game;

import java.util.Calendar;

public class Tank {

	private int width;
	private int x,y;
	private Game g;
	private Weapon[][] weps; // карта размещения оружия
	private long lastMotion, delay, rideTime;
	public Tank(String type, int xtable, int ytable, Game game)
	{
		g = game;
		x = xtable;
		y = ytable;
		if(type.equalsIgnoreCase("Normal"))
		{
			width = 2;
			weps = new Weapon[3][3];
			placeWeapon("Lazer", 1, 1);
			delay = 0;
		}
		
		if(type.equalsIgnoreCase("Big"))
		{
			width = 3;
			weps = new Weapon[5][5];
			placeWeapon("Normal", 0, 0);
			placeWeapon("Normal", 4, 0);
			placeWeapon("Normal", 0, 4);
			placeWeapon("Normal", 4, 4);
			placeWeapon("Lazer", 2, 2);
			delay = 65;
		}
		
		if(type.equalsIgnoreCase("Boss"))
		{
			width = 9;
			weps = new Weapon[17][17];			
			for(int q=1;q<8;q++)
			{
				for(int w=1;w<8;w++)
				{
					placeWeapon("Normal", q*2, w*2);
				}
			}
			delay = 300;
		}
		// зачищаем площадку под появление танка.
		g.getField().clear(width, x, y);
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
				try
				{
					if(weps[q][w]!=null) canplace=false;
				}
				catch(Exception ex)
				{
					
				}
				
			}
		}
		// если можно разместить -> размещаем
		if(canplace) weps[xp][yp] = new Weapon(type, this, 1024, 640);		
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
					Weapon currentWep = weps[q][w];
					int loc = currentWep.getLoc();
					int r = currentWep.getStepLength();
					int gx = x+q/2 + (q)%2;
					int gy = y+w/2 + (q)%2;
					double a = Math.sqrt((tx-gx)*(tx-gx) + (ty-gy)*(ty-gy));
					long time = Calendar.getInstance().getTimeInMillis();
					if(!(gx == tx && gy == ty) && currentWep.isShootable(time))
					{
						if(currentWep.getType().equalsIgnoreCase("Normal"))
						{
							g.shoot(new Shell(gx, gy, (loc/2)*((q+1)%2), (loc/2)*((w+1)%2), (int)(r*(tx-gx)/a), (int)(r*(ty-gy)/a), this, loc));
							currentWep.shootTime(time);
						}
						if(currentWep.getType().equalsIgnoreCase("Lazer"))
						{
							g.shootLazer(new Lazer(gx, gy, (loc/2)*((q+1)%2), (loc/2)*((w+1)%2), (int)(r*(tx-gx)/a), (int)(r*(ty-gy)/a), this, loc));
							currentWep.shootTime(time);
						}
					}
				}
			}
		}
	}
	
	/*
	 * onTouch(event)
	 * {
	 * 		detector.onTouch(event);
	 * }
	 * GestureDetector and GestureListner()
	 * */
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
				break;
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
		lastMotion = rideTime;
	}
	
	private boolean canRide(int x, int y)
	{
		boolean canride = true;
		rideTime = Calendar.getInstance().getTimeInMillis();
		if(Math.abs(rideTime - lastMotion) < delay)
			canride = false;
		
		if(!g.getField().getMats().get(g.getField().get(x, y)).isDrivable()) canride = false;
		
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
