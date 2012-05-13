package game.graphics;

import game.tanks.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PicLibrary {
	
	private Bitmap tank_Normal;
	private Bitmap tank_Big;
	private Bitmap tank_Boss;
	
	private Bitmap weapon_Normal;
	
	public PicLibrary(Resources res)
	{
		tank_Normal = BitmapFactory.decodeResource(res, R.drawable.tank_normal);
		tank_Big = BitmapFactory.decodeResource(res, R.drawable.tank_boss);
		tank_Boss = BitmapFactory.decodeResource(res, R.drawable.tank_boss);
		
		weapon_Normal = BitmapFactory.decodeResource(res, R.drawable.weapon_normal);
	}
	public Bitmap getBitmapTank(String type)
	{
		if(type.equalsIgnoreCase("Normal")) return tank_Normal;
		if(type.equalsIgnoreCase("Big")) return tank_Big;
		if(type.equalsIgnoreCase("Boss")) return tank_Boss;
		return null;
	}
	public Bitmap getBitmapWeapon(String type)
	{
		if(type.equalsIgnoreCase("Normal")) return weapon_Normal;
		return null;
	}

}
