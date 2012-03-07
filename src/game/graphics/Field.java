package game.graphics;
import java.io.*;

import android.util.Log;
import game.input.*;
public class Field 
{
	private int width, height; // ширина и высота
	private byte pole [][]; // собственно массив с полем.
	private int row, col; // ширина колонки и столбика
	private Separator sep; // класс, разбирающий входные данные
	
	public Field(int width, int height)
	{
		this.width = width;
		this.height = height;
		sep = new Separator();
	}
	
	public void genMap(char source) throws IOException // создаем карту
	{
		String name = "map" + source + ".txt";
		byte [][] temp = sep.interpritation(name); // обработываем ввод
		setRow(); setCol(); // устанавливаем ширину и высоту ячейки
		pole = new byte[col * sep.Y][row * sep.X];
		for(int i = 0; i < sep.Y; i++)
		{
			for(int j = 0; j < sep.X; j++)
			{
				for(int g = i*col; g < (i + 1) * col; g++)
				{
					for(int h = j*row; h < (j + 1) * row; h++)
					{
						pole[g][h] = temp[i][j];
					}
				}
			}
		}		
	}
	
	private void setRow()
	{
		row = height;
		while(row % sep.X != 0)
			row--;
		row /= sep.X;
	}
	
	private void setCol() 
	{
		col = width;
		while(col % sep.Y != 0)
			col--;
		col /= sep.Y;
	}
}
