package game.input;

public class Zone {

	int pid = -1;
	public Zone()
	{
		
	}
	
	public void checkOut(float[] arr)
	{
		for(int q=0;q<arr.length;q+=3)
		{
			if(pid == arr[q])
			{
				if(!isInZone(arr[q+1], arr[q+2])) pid=-1;
			}
		}
	}
	
	public boolean isInZone(float x, float y)
	{
		return false;
	}
	
	public void check(float[] arr)
	{
		
	}
	public boolean isHolded()
	{
		return pid!=-1;
	}
}
