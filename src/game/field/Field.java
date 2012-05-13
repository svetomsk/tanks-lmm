package game.field;

import android.content.res.Resources;
import android.util.Log;
import game.materials.Materials;

public class Field 
{
	private byte [][] pole; // собственно поле
	private int [][] hardMap;
	public int width, height; // ширина и высота
	private Materials mats;
	
	public Field(byte[][] array, Resources r)
	{
		pole = array;
		width = array.length;
		height = array[0].length;
		hardMap = new int[width][height];
		mats = new Materials();
		mats.init(r);	
		for(int q=0;q<width;q++)
		{
			for(int w=0;w<height;w++)
			{
				hardMap[q][w] = mats.get(pole[q][w]).getHardness(); 
			}
		}
	}
	
	public int get(int x, int y) // возвращает содержимое определенной ячейки
	{
		return pole[x][y];
	}	
	public void explode(int x, int y, int power) // взрыв
	{
		hardMap[x][y]-=power;
		if(hardMap[x][y]>0)return;
		if(pole[x][y] == 1){ hardMap[x][y]=0; return;}
		pole[x][y] = 0;
		return;
	}
	
	public void clear(int width, int x, int y)
	{
		for(int q = 0; q < width; q++)
		{
			for(int w = 0; w < width; w++)
			{
				pole[x+q][y+w] = 0;
			}
		}
	}
	
	public boolean isExplodable(int x, int y) // проверка на ударяемость. не воздух
	{
		return mats.get(pole[x][y]).isExplodable();
	}
	public boolean isPermeableToLight(int x, int y) // проверка на ударяемость. не воздух
	{
		return mats.get(pole[x][y]).isPermeableToLight();
	}

	public Materials getMats()
	{
		return mats;
	}
	public int getWidth() // ширина
	{
		return width;
	}
	
	public int getHeight() // высота
	{
		return height;
	}
}
