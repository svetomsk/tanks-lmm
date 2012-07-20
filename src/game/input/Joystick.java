package game.input;

import android.util.Log;

public class Joystick extends Zone{
	
	float pid = -1;
	int x1, y1, x2, y2;
	private JoystickUp jup;
	private JoystickDown jdn;
	private JoystickLeft jlt;
	private JoystickRight jrt;
	public Joystick(int x1, int y1, int x2, int y2)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		jup = new JoystickUp(x1, y1, x2, y2);
		jdn = new JoystickDown(x1, y1, x2, y2);
		jlt = new JoystickLeft(x1, y1, x2, y2);
		jrt = new JoystickRight(x1, y1, x2, y2);
	}
	
	public void checkOut(float[] arr)
	{
//		for(int q=0;q<arr.length;q+=3)
//		{
//			if(pid == arr[q])
//			{
//				if(!isInZone(arr[q+1], arr[q+2])) pid=-1;
//				return;
//			}
//		}
		pid = -1;
	}
	
	public boolean isInZone(float x, float y)
	{
		return x<x2 && x>x1 && y<y2 && y>y1;
	}
	
	public void check(float[] arr)
	{
		if(arr != null)
		for(int q=0;q<arr.length;q+=3)
		{
			if(isInZone(arr[q+1], arr[q+2])) 
			{
				pid = arr[q];
				jup.check(arr[q], arr[q+1], arr[q+2]);
				jdn.check(arr[q], arr[q+1], arr[q+2]);
				jlt.check(arr[q], arr[q+1], arr[q+2]);
				jrt.check(arr[q], arr[q+1], arr[q+2]);
				return;
			}
		}
	}
	public boolean isHolded()
	{
		return pid!=-1;
	}
	
	public int currentDirection()
	{
		if(pid==-1) return -1;
		if(jup.isHolded()) return 1;
		if(jdn.isHolded()) return 3;
		if(jrt.isHolded()) return 0;
		if(jlt.isHolded()) return 2;
		return -1;
	}

}
