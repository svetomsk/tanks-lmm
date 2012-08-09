package game.input;

import game.graphics.MainView;

public class Pole {
	
	int x1, y1, x2, y2;
	private MainView mainView;
	private Input input;
	public Pole(MainView mv, Input input)
	{
		mainView = mv;
		this.input = input;
		
		this.x1 = 0;
		this.x2 = mainView.getW();
		this.y1 = 0;
		this.y2 = mainView.getH();
	}
	
	public boolean isInZone(float x, float y)
	{
		return x<x2 && x>x1 && y<y2 && y>y1;
	}
	
	public void check(float[] arr)
	{
		for(int q=0;q<arr.length;q+=2)
		{
			if(isInZone(arr[q], arr[q+1])) 
			{
				mainView.shoot(arr[q], arr[q+1]);
				input.remove(q);
				return;
			}
		}
	}

}
