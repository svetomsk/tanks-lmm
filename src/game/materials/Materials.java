package game.materials;

import game.tanks.R;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class Materials {
	
	private Material[] arr = new Material[5];
	
	public void init(Resources r)
	{
		arr[0] = new Material(1, false, true, true, BitmapFactory.decodeResource(r, R.drawable.floor_default));
		arr[1] = new Material(9999, true, false, false, BitmapFactory.decodeResource(r, R.drawable.block_adminium));
		arr[2] = new Material(8, true, false, false, BitmapFactory.decodeResource(r, R.drawable.block_brick));
		arr[3] = new Material(1, false, true, true, BitmapFactory.decodeResource(r, R.drawable.floor_grass));
		arr[4] = new Material(1, true, true, false, BitmapFactory.decodeResource(r, R.drawable.block_glass));
	}
	public Material get(int i)
	{
		return arr[i];
	}

}
