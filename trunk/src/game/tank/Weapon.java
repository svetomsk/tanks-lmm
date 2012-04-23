package game.tank;

public class Weapon {
	private String type;
	private Tank t;
	private int loc;
	private int stepLength;
	private long lastShoot, delay;
	
	public Weapon(String type, Tank t, int loc, int stepLength)
	{
		this.t = t;
		this.type = type;
		this.loc = loc;
		this.stepLength = stepLength;

		if(type.equalsIgnoreCase("Normal")) {delay = 1000;}
		if(type.equalsIgnoreCase("Lazer")) {delay = 0;}
		
		lastShoot = -1;
	}
	
	public void shootTime(long time)
	{
		lastShoot = time;
	}
	
	public boolean isShootable(long time)
	{
		if(Math.abs(time - lastShoot) >= delay)
			return true;
		return false;
	}
	
	public int getLoc()
	{
		return loc;
	}
	
	public int getStepLength()
	{
		
		return stepLength;
	}
	public String getType()
	{
		return type;
	}
}
