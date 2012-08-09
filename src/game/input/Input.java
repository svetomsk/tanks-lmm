package game.input;
import game.graphics.MainView;

public class Input{
	private MainView mainView;
	
	private Joystick joystick; 
	private Pole pole;
	private float[] arr;
	public Input(MainView mv)
	{
		mainView = mv;
		
		joystick = new Joystick(mainView, this);
		pole = new Pole(mainView, this);
	}
	
	public void update(float[] array)
	{
		if(array == null) return;
		arr = array.clone();
		
		joystick.check(arr);
		pole.check(arr);
	}
	public void remove(int q)
	{
		arr[q] = -1;
		arr[q+1] = -1;
	}
}
