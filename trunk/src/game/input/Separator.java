package game.input;
import game.tanks.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
public class Separator 
{
	private byte [][] mas;
	public static int X, Y; // кол-во по ширине и высоте
	private final Resources r; // объект для обращения к ресурсам
		
	public Separator(Resources res)
	{
		r = res;
	}
//	private final int COLOR_1;
//	private final int COLOR_2;
	
	public byte[][] interpritaitonPicture(int name) // интерпретируем картинку в массив byte
	{
		Bitmap pic = null;
		if(name == 1) { pic = BitmapFactory.decodeResource(r, R.drawable.map1); }
		//if(name == 2) { pic = BitmapFactory.decodeResource(r, R.drawable.map2); }
		
		Y = pic.getHeight();
		X = pic.getWidth();
		mas = new byte[X+2][Y+2];
		
		for(int q=0;q<X;q++)
		{
			for(int w=0;w<Y;w++)
			{
				mas[q+1][w+1] = 0;
				switch(pic.getPixel(q, w))
				{
				case -8421505: {mas[q+1][w+1] = 1; break;}
				case -4621737: {mas[q+1][w+1] = 2; break;}
				case -6694422: {mas[q+1][w+1] = 4; break;}
				}
			}
		}
		// заполняем рамку
		// по вертикали
		for(int q=0;q<X+2;q++)
		{
			mas[q][0] = 1;
			mas[q][Y+1] = 1;			
		}
		// по горизонтали
		for(int q=0;q<Y+2;q++)
		{
			mas[0][q] = 1;
			mas[X+1][q] = 1;			
		}
		
		return mas;
	}
}
