package game.tank;

public class Weapon {
	private String type;
	private Tank t;
	private int loc;
	private int stepLength;
	private long lastShoot, delay;
	private int angle;
	
	public Weapon(String type, Tank t, int loc, int stepLength)
	{
		this.t = t;
		this.type = type;
		this.loc = loc;
		this.stepLength = stepLength;
		this.angle = -90;

		if(type.equalsIgnoreCase("Normal")) {delay = 256;}
		
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
	public void setAngle(int a)
	{
		angle = a;		
	}
	
	public int getAngle()
	{
		return angle;
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
