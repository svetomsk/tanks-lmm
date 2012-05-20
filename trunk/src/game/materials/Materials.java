package game.materials;

import game.tanks.R;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class Materials {
	
	private Material[] arr = new Material[5];
	
	public static final int MAT_FLOOR_DEFAULT = 0,
		 	    MAT_BLOCK_ADMINIUM = 1,
		            MAT_BLOCK_BRICK = 2,
		            MAT_FLOOR_GRASS = 3,
		            MAT_BLOCK_GRASS = 4;		 
	
	public void init(Resources r)
	{
		arr[MAT_FLOOR_DEFAULT] = new Material(1, false, true, true, BitmapFactory.decodeResource(r, R.drawable.floor_default));
		arr[MAT_BLOCK_ADMINIUM] = new Material(9999, true, false, false, BitmapFactory.decodeResource(r, R.drawable.block_adminium));
		arr[MAT_BLOCK_BRICK] = new Material(8, true, false, false, BitmapFactory.decodeResource(r, R.drawable.block_brick));
		arr[MAT_FLOOR_GRASS] = new Material(1, false, true, true, BitmapFactory.decodeResource(r, R.drawable.floor_grass));
		arr[MAT_BLOCK_GRASS] = new Material(1, true, true, false, BitmapFactory.decodeResource(r, R.drawable.block_glass));
	}
	public Material get(int i)
	{
		return arr[i];
	}

}
