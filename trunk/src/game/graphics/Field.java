package game.graphics;
import java.io.*;

import android.util.Log;
import game.input.*;
public class Field 
{
	private int width, height; // ширина и высота экрана в пикселях
	private byte pole [][]; // зона игры. один пиксель - одна ячейка.
	private int row, col; // ширина и высота одного моноблока
	private Separator sep; // класс, отвечающий за чтение из файла и интерпретацию входных данных
	
	public Field(int width, int height)
	{
		this.width = width;
		this.height = height;
		sep = new Separator();
	}
	
	public void genMap(char source) throws IOException // создание карты
	{
		String name = "map" + source + ".txt";
		byte [][] temp = sep.interpritation(name); // собственно интерпетация
		setRow(); setCol(); // устанавливаем высоту и ширину моноблоков
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
		
		//РАЗОБРАТЬСЯ С РАБОТОЙ С ФАЙЛОВОЙ СИСТЕМОЙ
		FileWriter fw = new FileWriter("out.txt");
		Log.d("name", "lsfjsa;fjsaf");
		for(int i = 0; i < pole.length; i++)
		{
			for(int g = 0; g < pole[0].length; g++)
			{
				Log.d(Integer.toString(i) + " " + Integer.toString(g), Byte.toString(pole[i][g]));
			}
			//fw.write("\r\n");
		}
		fw.close();
	}
	
	private void setRow() // определяем высоту моноблока
	{
		row = height;
		while(row % sep.X != 0)
			row--;
		row /= sep.X;
	}
	
	private void setCol() // определяем ширину моноблока
	{
		col = width;
		while(col % sep.Y != 0)
			col--;
		col /= sep.Y;
	}
}
