package game.input;
import game.graphics.MainView;
import android.util.Log;

public class Input{
	private MainView MainView;
	
	private Joystick joystick; 
	public Input(MainView mv)
	{
		MainView = mv;
		joystick = new Joystick(0, 480-mv.getJoyHeight(), mv.getJoyHeight(), 480);
	}
	
	public void update(float[] arr)
	{
		joystick.checkOut(arr);
		joystick.check(arr);
	}
	
	public void change()
	{
		MainView.getGame().getMainTank().move(joystick.currentDirection());
	}

}
