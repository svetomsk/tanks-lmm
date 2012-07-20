package game.input;

public class JoystickUp extends Zone{
	
	public float pid = -1;
	int x1, y1, x2, y2;
	public JoystickUp(int x1, int y1, int x2, int y2)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
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
//		pid = -1;
	}
	
	public boolean isInZone(float x, float y)
	{
		int mainDiagonal = (int)((x - x1) * (y2 - y1) - (y - y1) * (x2 - x1));
		int pobDiagonal = (int)((x - x1) * (y1 - y2) - (y - y2) * (x2 - x1));
		return mainDiagonal > 0 && pobDiagonal > 0;
	}
	
	public void check(float id, float x, float y)
	{
		if(isInZone(x, y))
		{
			pid = id;
			return;
		}
		pid = -1;
	}
	public boolean isHolded()
	{
		return pid!=-1;
	}
}