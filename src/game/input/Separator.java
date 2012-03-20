package game.input;
import game.tanks.R;

import java.io.*;

import android.content.res.Resources;
public class Separator 
{
	private byte [][] mas;
	public static int X, Y; // кол-во по ширине и высоте
	private final Resources r;
		
	public Separator(Resources res)
	{
		r = res;
	}
	public byte[][] interpritation(int name) throws IOException // входные данные в массив byte
	{	
		String [] array = null;
		switch(name)
		{
			case 1: { array = r.getStringArray(R.array.map1); }
		}		
		X = Integer.valueOf(array[0]);
		Y = Integer.valueOf(array[1]);
		mas = new byte[Y+2][X+2];
		String work = array[2];
		int t = 0;
		for(int i = 1; i <= Y; i++)
		{
			for(int g = 1; g <= X; g++)
			{
				char a = work.charAt(t);
				if(a == ' ') {g--;}
				if(a == 'o') mas[i][g] = 0;
				if(a == 'w') mas[i][g] = 2;				
				t++;
			}
		}
		for(int q=0;q<Y+2;q++)
		{
			mas[q][0] = 1;
			mas[q][X+1] = 1;			
		}
		for(int q=0;q<X+2;q++)
		{
			mas[0][q] = 1;
			mas[Y+1][q] = 1;			
		}
		return mas;
	}
}
