package game.materials;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;

public class Material {
	private int hardness;
	private boolean isExplodable;
	private boolean isPermeableToLight;
	private boolean isDrivable;
	private byte life;
	private Bitmap texture;
		
	public Material(int hardness, boolean isExplodable, boolean isPermeableToLight, boolean isDrivable, Bitmap texture)
	{
		this.hardness = hardness;
		this.isExplodable = isExplodable;
		this.isPermeableToLight = isPermeableToLight;
		this.isDrivable = isDrivable;
		this.texture = texture;		
	}
	
	public int getHardness() { return hardness; }
	public boolean isExplodable() { return isExplodable; }
	public boolean isPermeableToLight() { return isPermeableToLight; }
	public boolean isDrivable () { return isDrivable; }
	public Bitmap getTexture() { return texture; }
	public int getLife() { return life; }
	
	//юзай extend
}
