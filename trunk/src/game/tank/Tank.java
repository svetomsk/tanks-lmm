package game.tank;

import game.main.Game;

import java.util.Calendar;

import android.util.Log;

public class Tank {

	private int width;
	private int x,y;
	private Game g;
	private Weapon[][] weps; // карта размещения оружия
	private long lastMotion, delay, rideTime;
	private int lastDir=1;
	private String type;
	private int life;
	public Tank(String type, int xtable, int ytable, Game game)
	{
		g = game;
		x = xtable;
		y = ytable;	
		this.type = type;
		if(type.equalsIgnoreCase("Normal"))
		{
			width = 2;
			weps = new Weapon[3][3];
			placeWeapon("Normal", 1, 1);
			delay = 0;
			life = 16;
		}
		
		if(type.equalsIgnoreCase("Big"))
		{
			width = 3;
			weps = new Weapon[5][5];
			placeWeapon("Normal", 0, 0);
			placeWeapon("Normal", 4, 0);
			placeWeapon("Normal", 0, 4);
			placeWeapon("Normal", 4, 4);
			placeWeapon("Normal", 2, 2);
			delay = 65;
			life = 64;
		}
		
		if(type.equalsIgnoreCase("Boss"))
		{
			width = 9;
			weps = new Weapon[17][17];			
			for(int q=1;q<8;q+=2)
			{
				for(int w=1;w<7;w+=2)
				{
					placeWeapon("Normal", q*2, w*2);
				}
			}
			delay = 0;
			life = 256;
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
					int gy = y+w/2 + (w)%2;
					double a = Math.sqrt((tx-gx)*(tx-gx) + (ty-gy)*(ty-gy));
					long time = Calendar.getInstance().getTimeInMillis();
					if(!(gx == tx && gy == ty) && currentWep.isShootable(time))
					{
						if(currentWep.getType().equalsIgnoreCase("Normal"))
						{
							g.shoot(new Shell(gx, gy, (loc/2)*((q+1)%2), (loc/2)*((w+1)%2), (int)(r*(tx-gx)/a), (int)(r*(ty-gy)/a), this, loc));
							currentWep.shootTime(time);
						}
					}
					currentWep.setAngle((int)((ty-gy >0 ? Math.acos((tx-gx)/a):-Math.acos((tx-gx)/a))*180/Math.PI)); // это угол находит
				}
			}
		}
	}
	
	// движение танка
	public void move(int value)
	{
		if(value == -1) return;
		if(lastDir != value)
		{
			if(value == lastDir - 1 || (value == 3 && lastDir == 0))
			{
				turnRight();
				return;
			}
			if(value == lastDir + 1 || (value == 0 && lastDir == 3))
			{
				turnLeft();
				return;
			}
			if(Math.abs(lastDir-value) == 2)
			{
				turnLeft();
				return;
			}
		}
		
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
	
	private void turnRight()
	{
		lastDir--;
		if(lastDir==-1) lastDir = 3;
		
		Weapon[][] newWeps = new Weapon[weps.length][weps[0].length];
		for(int q=0;q<newWeps.length;q++)
		{
			for(int w=0;w<newWeps[0].length;w++)
			{
				newWeps[q][w] = weps[w][weps[0].length-q-1];
				if(newWeps[q][w] != null) newWeps[q][w].setAngle(newWeps[q][w].getAngle()+90);
			}
		}
		weps = newWeps;
	}
	
	private void turnLeft()
	{
		lastDir++;
		if(lastDir== 4 ) lastDir = 0;		

		Weapon[][] newWeps = new Weapon[weps.length][weps[0].length];
		for(int q=0;q<newWeps.length;q++)
		{
			for(int w=0;w<newWeps[0].length;w++)
			{
				newWeps[q][w] = weps[weps.length-w-1][q];
				if(newWeps[q][w] != null) newWeps[q][w].setAngle(newWeps[q][w].getAngle()-90);
			}
		}
		weps = newWeps;		
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
	
	public void damage(int value)
	{
		life-=value;
		// if(life <= 0) breaktank(); 
	}
	
	// вспомагательные get-методы
	public int getLife()
	{
		return life;
	}
	public Weapon[][] getWeps()
	{
		return weps;
	}
	public String getType()
	{
		return type;
	}
	public int getDir()
	{
		return lastDir;
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
