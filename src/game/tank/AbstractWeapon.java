package game.tank;

public class AbstractWeapon {
	private String type;
	private AbstractTank t;
	
	public AbstractWeapon(String type, AbstractTank t)
	{
		this.t = t;
		this.type = type;
	}
	
}
