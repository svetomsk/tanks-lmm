package game.graphics;

import game.field.Field;
import game.main.Game;
import game.tank.AbstractShell;
import game.tank.AbstractTank;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View
{
	float x=0, y=0;
	float curx=0, cury=0;
	float oldx = 0, oldy=0;
	float width, height;
	int length = 13;
	int FPS = 50; // frames per second
	Game game;
	
	Bitmap js; // битмэп для джойстика
	
	int control, cw = 200, ch = 200; // собственно джойстик, его ширина и высота
	
	public MainView(Context c, Field df, int control)
	{
		super(c);
		game = new Game(df, 3, 10, "Boss", this);
		this.control = control;
		mf = new Matrix();
		// запускаем поток, обновляющий View
		repaintThread();
	}
	
	
	public void repaintThread()
	{		
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while(true)
				{
					postInvalidate();
					try
					{
						Thread.sleep(200/FPS);
					}catch(InterruptedException exs)
					{
						Log.d("EXCEPTION", "LINE 55 MV");
					}
				}
			}
		}).start();
	}
	// собственно прорисовка
	public void onDraw(Canvas c)
	{
		render(c);
	}
	
	public void render(Canvas c)
	{
		renderField(c);	// рисуем поле
		renderTanks(c); // рисуем танки
		renderShells(c); // рисуем снаряды
		renderJoystick(c); // рисуем джойстик
	}	
	
	private Matrix mf;
	public void renderField(Canvas s)
	{
		int beginx = ( (int) -(x/length) );
		int beginy = (int) -(y/length);
		int endx = Math.min( ( (int)((-x+width)/length)+1 ), game.getField().getWidth() );
		int endy = Math.min( ( (int)((-y+height)/length)+1 ), game.getField().getHeight() );
		for(int i = beginx; i < endx; i++)
		{
			for(int j = beginy; j < endy; j++)
			{
				mf.setTranslate(x+i*length, y+j*length);
				s.drawBitmap(game.getField().getMats().get(game.getField().get(i, j)).getTexture(), null, new Rect((int)x + i * length,(int) y + j * length,(int) x + (i+1)*length,(int) y + (j+1)*length), null);
			}
		}	
	}
	
	public void renderTanks(Canvas s)
	{
		Paint p = new Paint();
		p.setColor(Color.GREEN);
		
		for(int q=0;q<game.getField().getHeight();q++)
		{
			for(int w=0;w<game.getField().getWidth();w++)
			{
				if(game.getTField().get(w, q) != null) s.drawRect(w*length + x, q*length + y, (w+1)*length + x, (q+1)*length + y, p);
			}
		}
	}
	public void renderShells(Canvas s)
	{
		Paint p = new Paint();
		p.setColor(Color.RED);
		for(int q=0;q<game.getShells().size();q++)
		{
			try
			{
				float xs = x+game.getShells().get(q).getGX()*length+(game.getShells().get(q).getLX()*length)/game.getShells().get(q).getSize();
				float ys = y+game.getShells().get(q).getGY()*length+(game.getShells().get(q).getLY()*length)/game.getShells().get(q).getSize();
				s.drawCircle(xs, ys, 2, p);
			}catch(ArrayIndexOutOfBoundsException exc)
			{
				
			}
		}
	}
	
	
	public void renderJoystick(Canvas s) // добавляем джойстик
	{		
		js = BitmapFactory.decodeResource(getResources(), control);
		s.drawBitmap(js, null, new Rect(0, (int)height - ch, cw, (int)height), null);
	}
	
	public int revers(int i, int g, Field f) // преобразование числа в соответствующий цвет
	{
		switch(f.get(g, i))
		{
			case 0 : return Color.BLACK;	
			case 1 : return Color.DKGRAY;
			case 2 : return Color.rgb(151, 76, 24);
		}
		return Color.RED;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) 
	{		
		if(ev.getPointerCount() <= 2)
		{	
			if(ev.getAction() == MotionEvent.ACTION_MOVE) // сложные потуги с промоткой
			{	
				onActionMoveEvent(ev);
			}
			if(ev.getAction() == MotionEvent.ACTION_DOWN)
			{
				onActionDown(ev);
			}
			if(ev.getAction() == MotionEvent.ACTION_UP)
			{
				onActionUp(ev);
			}
		}
		return true;
	}
	
	private void onActionMoveEvent(MotionEvent ev)
	{
		x = oldx + ev.getX() - curx;
		y = oldy + ev.getY() - cury;
		if(x > 0) x = 0;
		if(y > 0) y = 0;	
		int w = game.getField().width * length;
		int h = game.getField().height * length;
		if(x < (width - w)) x = width - w;
		if(y < (height - h)) y = height - h;
	}
	
	private void onActionDown(MotionEvent ev)
	{
		game.getMainTank().shoot((int)((-x+ev.getX())/length), (int)((-y+ev.getY())/length));
		curx = ev.getX();
		cury = ev.getY();
		if(curx < cw && cury > height - ch) // если тыкнул на джойстик
		{
			replaceTank(); // перемещение танка
		}
	}
	
	private void onActionUp(MotionEvent me)
	{
		oldx = x;
		oldy = y;
	}
	
	private void replaceTank()
	{
		int turn = controlAction(curx, cury);
		switch(turn)
		{
		case 1: {game.getMainTank().move(1); break;}
		case 2: {game.getMainTank().move(0); break;}
		case 3: {game.getMainTank().move(3); break;}
		case 4: {game.getMainTank().move(2); break;}
		}
	}
	
	private int controlAction(float curx, float cury) // проверяем куда именно нажали на джойстике
	{
		int x1 = 0; int x2 = cw;
		int y1 = (int)height - ch; int y2 = (int)height;
		int mainDiagonal = (int)((curx - x1) * (y2 - y1) - (cury - y1) * (x2 - x1));
		int pobDiagonal = (int)((curx - x1) * (y1 - y2) - (cury - y2) * (x2 - x1));
		if(mainDiagonal > 0 && pobDiagonal > 0)
			return 1;
		if(mainDiagonal > 0 && pobDiagonal < 0)
			return 2;
		if(mainDiagonal < 0 && pobDiagonal < 0)
			return 3;
		return 4;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) // формируем размеры экрана
	{		
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
	}
	
}
