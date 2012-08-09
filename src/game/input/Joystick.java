package game.input;

import game.graphics.MainView;


public class Joystick {
	int x1, y1, x2, y2;
	private MainView mainView;
	private Input input;
	public Joystick(MainView mv, Input input)
	{
		mainView = mv;
		this.input = input;
		
		this.x1 = 0;
		this.x2 = mainView.getJoyHeight();
		this.y1 = mainView.getH()-mv.getJoyHeight();
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
				int mainDiagonal = (int)((arr[q] - x1) * (y2 - y1) - (arr[q+1] - y1) * (x2 - x1));
				int pobDiagonal = (int)((arr[q] - x1) * (y1 - y2) - (arr[q+1] - y2) * (x2 - x1));
				
				if(mainDiagonal > 0 && pobDiagonal > 0) mainView.getGame().getMainTank().move(1);
				if(mainDiagonal < 0 && pobDiagonal < 0) mainView.getGame().getMainTank().move(3);
				if(mainDiagonal < 0 && pobDiagonal > 0) mainView.getGame().getMainTank().move(2);
				if(mainDiagonal > 0 && pobDiagonal < 0) mainView.getGame().getMainTank().move(0);
				
				input.remove(q);
				return;
			}
		}
	}
}
