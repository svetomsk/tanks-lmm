package game.graphics;

import game.field.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View
{
	final int k = 10;
	float x=0, y=0;
	float curx=0, cury=0;
	float oldx = 0, oldy=0;
	float width, height;
	int length = 10; 
	Field f;
	public MainView(Context c, Field df)
	{
		super(c);
		f = df;
	}	
	
	public void onDraw(Canvas c)
	{
		render(c);
	}
	
	public void render(Canvas c)
	{
		renderField(c);	
	}	
	
	public void renderField(Canvas s)
	{
		Paint p = new Paint();
		for(int i = 0; i < f.height; i++)
		{
			for(int j = 0; j < f.width; j++)
			{
				p.setColor(revers(i, j, f));				
				s.drawRect(j*length*k + x, i*length*k + y, (j+1)*length*k + x, (i+1)*length*k + y, p);
			}
		}
	}
	public int revers(int i, int g, Field f)
	{
		switch(f.get(i, g))
		{
			case 0 : return Color.DKGRAY;
			case 1 : return Color.BLACK;
			case 2 : return Color.rgb(151, 76, 24);
		}
		return Color.RED;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) 
	{
		if(ev.getAction() == MotionEvent.ACTION_MOVE)
		{	
			x = oldx + ev.getX() - curx;
			y = oldy + ev.getY() - cury;
			if(x > 0) x = 0;
			if(y > 0) y = 0;	
			int w = f.width * k * length;
			int h = f.height * k * length;
			if(x < (width - w)) x = width - w;
			if(y < (height - h)) y = height - h;
			invalidate();			
		}
		if(ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			curx = ev.getX();
			cury = ev.getY();
			
		}
		if(ev.getAction() == MotionEvent.ACTION_UP)
		{
			oldx = x;
			oldy = y;
		}
		return true;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) 
	{		
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
	}
	
}
