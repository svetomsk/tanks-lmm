package game.main;

import game.field.*;
import android.content.Context;
import android.util.Log;
import android.view.View;

public class TanksView extends View{
	private final Field field;
	private final TanksActivity ctx;
	
	private int width, height;

	public TanksView(Context context) 
	{
		super(context);
		ctx = (TanksActivity)context;
		field = ctx.f;		
		setFocusable(true);
		setFocusableInTouchMode(true);		
//		Thread t = new Thread(this)
//		{
//			TanksView tv = this;
//			while(true)
//			{			
//				tv.render;
//				wait(600);
//			}
//		}
//		t.start();
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		//Log.d("Size changed", null);		
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
	}
	
//	private void render()
//	{
//		
//	}
}
