package game.input;
import game.tanks.R;

import java.io.*;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
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
//	private final int COLOR_1;
//	private final int COLOR_2;
	
	public byte[][] interpritaitonPicture(int name) 
	{
		Bitmap pic = null;
		if(name == 1) { pic = BitmapFactory.decodeResource(r, R.drawable.map1); }
	//	if(name == 2) { pic = BitmapFactory.decodeResource(r, R.drawable.map2); }
		
		Y = pic.getHeight();
		X = pic.getWidth();
		mas = new byte[Y+2][X+2];
		Log.d("0000000000000000", "L, W: "+pic.getHeight()+", "+pic.getWidth());
		
		for(int q=0;q<Y;q++)
		{
			for(int w=0;w<X;w++)
			{
				mas[q+1][w+1] = 0;
//				Log.d("111111111111", ""+pic.getPixel(q, w)+";");
				if(pic.getPixel(w, q) == -8421505 ) { mas[q+1][w+1] = 1;}
				if(pic.getPixel(w, q) == -4621737 ) { mas[q+1][w+1] = 2;}
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
