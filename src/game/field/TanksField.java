package game.field;

import game.tank.AbstractTank;

public class TanksField {
	private AbstractTank[][] arr; // массив танков
	
	public TanksField(int width, int height)
	{
		arr = new AbstractTank[width][height];
	}
	
	public void spawnTank(AbstractTank at) // появление танка
	{
		int width = at.getWidth(); // ширина танка
		// координаты верхнего левого угла
		int x = at.getX(); // OY координата 
		int y = at.getY(); // OX координата
		// заполняем ячейки ссылкой на танк
		for(int q=0;q<width;q++)
		{
			for(int w=0;w<width;w++)
			{
				arr[q+x][w+y] = at;
			}
		}
	}
	
	public void explode(AbstractTank at) // уничтожение танка
	{
		if(at == null) return;
		int x = at.getX();
		int y = at.getY();
		int width = at.getWidth();
		// удаляем ссылки на танк
		for(int i = 0; i < width; i++)
		{
			for(int g = 0; g < width; g++)
			{
				arr[x+i][y+g] = null;
			}
		}
	}
	
	public void go(AbstractTank at, int vector) // обрабатываем движение
	{
		// координаты верхнего левого угла 
		int x = at.getX();
		int y = at.getY();
		int width = at.getWidth();
		
		for(int q = 0; q < width; q++)
		{
			switch(vector)
			{
			case 0: 
			{
				arr[x+width][y+q] = at;
				arr[x][y+q] = null;
				break;
			}
			case 1: 
			{
				arr[x+q][y-1] = at;
				arr[x+q][y+width-1] = null;
				break;
			}
			case 2: 
			{
				arr[x-1][y+q] = at;
				arr[x+width-1][y+q] = null;
				break;
			}
			case 3: 
			{
				arr[x+q][y+width] = at;
				arr[x+q][y] = null;
				break;
			}
			}
		}
	}
	
	
	
	public AbstractTank get(int x, int y) // возвращаем ссылку на танк в данной точке
	{
		return arr[x][y];
	}
}
