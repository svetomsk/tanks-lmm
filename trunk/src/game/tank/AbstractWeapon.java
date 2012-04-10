package game.tank;

public class AbstractWeapon {
	private String type;
	private AbstractTank t;
	private int loc;
	private int stepLength;
	
	public AbstractWeapon(String type, AbstractTank t, int loc, int stepLength)
	{
		this.t = t;
		this.type = type;
		this.loc = loc;
		this.stepLength = stepLength;
	}
	
	public int getLoc()
	{
		return loc;
	}
	
	public int getStepLength(){
		
		return stepLength;
	}
}
