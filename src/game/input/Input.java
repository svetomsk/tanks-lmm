package game.input;
import game.graphics.MainView;

public class Input{
	private MainView mainView;
	
	private Joystick joystick; 
	public Input(MainView mv)
	{
		mainView = mv;
		joystick = new Joystick(0, mainView.getH()-mv.getJoyHeight(), mv.getJoyHeight(), mainView.getH());
	}
	
	public void update(float[] arr)
	{
		joystick.checkOut(arr);
		joystick.check(arr);
	}
	
	public void change()
	{
		mainView.getGame().getMainTank().move(joystick.currentDirection());
	}

}
