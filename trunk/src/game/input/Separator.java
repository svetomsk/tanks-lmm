package game.input;

import game.materials.Materials;
import game.tanks.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Separator {
    private byte[][] mas;
    public static int X, Y; // кол-во по ширине и высоте
    private final Resources r; // объект для обращения к ресурсам

    public Separator(Resources res) {
	r = res;
    }

    // цвета с карты в формате ARGB
    private final int COLOR_BRICK = 0xffb97a57, COLOR_FUCKINBLUE = 0xff99d9ea,
	    COLOR_FUCKINGRAY = 0xff7f7f7f;

    private byte decodeColor(int c) {
	switch (c) {
	case COLOR_FUCKINGRAY:
	    return Materials.MAT_BLOCK_ADMINIUM;
	case COLOR_BRICK:
	    return Materials.MAT_BLOCK_BRICK;
	case COLOR_FUCKINBLUE:
	    return Materials.MAT_BLOCK_GRASS;
	default:
	    return 0;
	}

    }

    public byte[][] interpritaitonPicture(int name) // интерпретируем картинку в
						    // массив byte
    {
	Bitmap pic = null;
	BitmapFactory.Options opt = new BitmapFactory.Options();
	opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
	if (name == 1) {
	    pic = BitmapFactory.decodeResource(r, R.drawable.map1, opt);
	}
	// if(name == 2) { pic = BitmapFactory.decodeResource(r,
	// R.drawable.map2); }

	Y = pic.getHeight();
	X = pic.getWidth();
	mas = new byte[X + 2][Y + 2];

	for (int q = 0; q < X; q++) 
	    for (int w = 0; w < Y; w++) 
		mas[q+1][w+1] = decodeColor(pic.getPixel(q, w));
	    
	// заполняем рамку
	// по вертикали
	for (int q = 0; q < X + 2; q++)
	    mas[q][0] = mas[q][Y + 1] = 1;
	// по горизонтали
	for (int q = 0; q < Y + 2; q++)
	    mas[0][q] = mas[X + 1][q] = 1;

	return mas;
    }
}
