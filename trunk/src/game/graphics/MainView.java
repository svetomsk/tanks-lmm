package game.graphics;

import game.field.Field;
import game.main.Game;
import game.tank.Shell;
import game.tank.Tank;
import game.tank.Weapon;
import game.tanks.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class MainView extends View
{
	private float x=0, y=0; // координаты для скроллирования
	private float curx=0, cury=0; // текущие координаты от MotionEvent'а
	private float px = 0, py = 0; // кооридинаты для джойстика
	private float width, height;
	private int length = 17; // коэфициент масштабирования
	private int FPS = 300; // frames per second
	private Game game;
	private PicLibrary piclib;
	private Matrix transl, scale, rotation, fieldMatrix;
	private GestureDetector gdScroll; // слушатель жестов
	private int pointerCount;
	private Field df;
	
	private Bitmap js; // битмэп для джойстика
	
	private int cw = 200, ch = 200; // собственно джойстик, его ширина и высота
	
	private boolean move = false; // контроллер движения
	
	public MainView(Context c, Field df)
	{
		super(c);
		this.df = df;
		piclib = new PicLibrary(getResources());	            
        game = new Game(df, 3, 10, "Normal", this);
		// создаем джойстик
		js = BitmapFactory.decodeResource(getResources(), R.drawable.control);
		fieldMatrix = new Matrix();
		transl = new Matrix();
		rotation = new Matrix();
		scale = new Matrix();
		addListeners();
	}	
	
	public void createNewGame()
	{
		game = new Game(df, 3, 10, "Normal", this);
	}
	
	// возвращаем Game для потоков
	public Game getGame()
	{
		return game;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) 
	{		
		pointerCount = ev.getPointerCount();
		if(pointerCount == 1)
		{
			if(ev.getAction() == MotionEvent.ACTION_UP && move)
			{
				move = false;
			}
			gdScroll.onTouchEvent(ev);
		}
		
		return true;
	}
	
	// создаем простого слушателя нажатий и движений
	private void addListeners()
	{
		OnGestureListener sol = new OnGestureListener()
		{
			// одинарный клик
			@Override
			public boolean onSingleTapUp(MotionEvent ev) 
			{		
				curx = ev.getX();
				cury = ev.getY();
				if(!(curx < cw && cury > height - ch)) // если тыкнул на джойстик
				{
					game.getMainTank().shoot((int)((-x+ev.getX())/length), (int)((-y+ev.getY())/length));
				}				
				return true;
			}
			
			// прикосновение
			@Override
			public boolean onDown(MotionEvent e) 
			{
				curx = e.getX();
				cury = e.getY();
				if(curx < cw && cury > height - ch)
				{
					move = true;
					px = curx;
					py = cury;					
				}
				return true;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) 
			{
				
				return false;
			}

			// прокрутка
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) 
			{
				if(!move && pointerCount == 1)
				{
					x -= distanceX;
					y -= distanceY;
					game.getMainTank().shoot((int)((-x+e2.getX())/length), (int)((-y+e2.getY())/length));
					if(x > 0) x = 0;
					if(y > 0) y = 0;	
					int w = game.getField().width * length;
					int h = game.getField().height * length;
					if(x < (width - w)) x = width - w;
					if(y < (height - h)) y = height - h;
				}
				else
				{
					px -= distanceX;
					py -= distanceY;					
				}
				return true;
			}

			@Override
			public void onShowPress(MotionEvent ev) 
			{
				
			}			

			@Override
			public void onLongPress(MotionEvent e) 
			{
//				game.getMainTank().shoot((int)((-x+ev.getX())/length), (int)((-y+ev.getY())/length));				
			}
		};
		gdScroll = new GestureDetector(sol);
	}
	
	// рисуем точку для джойстика
	public void renderPoint(Canvas c)
	{
		if(move)
		{
			Paint p = new Paint();
			p.setColor(Color.BLUE);
			c.drawCircle(px, py, 15, p);
		}
	}
	
	public int getFPS()
	{
		return FPS;
	}
	
	// собственно прорисовка
	public void onDraw(Canvas c)
	{
		render(c);
	}
	
	public void render(Canvas c)
	{
		if(move)
		{
			replaceTank();
		}
		renderField(c);	// рисуем поле
		renderShells(c); // рисуем снаряды
		renderJoystick(c); // рисуем джойстик
		renderPoint(c); // для джойстика	
		renderTanks(c); // рисуем танки
	}	
		
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
				fieldMatrix.setTranslate(x+i*length, y+j*length);
				s.drawBitmap(game.getField().getMats().get(game.getField().get(i, j)).getTexture(), null, new Rect((int)x + i * length,(int) y + j * length,(int) x + (i+1)*length,(int) y + (j+1)*length), null);
			}
		}	
	}
	
	// рисуем танки
	public void renderTanks(Canvas s)
	{
		for(int q=0;q<game.getField().getHeight();q++)
		{
			for(int w=0;w<game.getField().getWidth();w++)
			{
				if(game.getTField().get(w, q) != null && game.getTField().get(w, q).getX() == w && game.getTField().get(w, q).getY() == q)
				{
					renderTank(s, q, w);
				}
			}
		}
	}
	
	public void renderTank(Canvas s, int q, int w)
	{
		Bitmap bm = piclib.getBitmapTank(game.getTField().get(w, q).getType());
		transl.setTranslate(w*length+x, q*length+y);
		rotation.setRotate((float)((-game.getTField().get(w, q).getDir()+1)*90), bm.getHeight()/2, bm.getWidth()/2);
		scale.setScale((float)1.0*game.getTField().get(w, q).getWidth()*length/bm.getWidth(), (float)1.0*game.getTField().get(w, q).getWidth()*length/bm.getHeight());
		transl.setConcat(transl, scale);
		transl.setConcat(transl, rotation);
		s.drawBitmap(bm, transl, null);
		Tank t = game.getTField().get(w, q);
		for(int i=0;i<t.getWeps().length;i++)
		{
			for(int j=0;j<t.getWeps()[0].length;j++)
			{
				if(t.getWeps()[i][j] != null)
				{
					Weapon curW = t.getWeps()[i][j];
					bm = piclib.getBitmapWeapon(curW.getType());
					transl.setTranslate((t.getX()+i/2)*length-(int)((1-(i%2))*length/2.0)+x, (int)(t.getY()+j/2)*length-(int)((1-(j%2))*length/2.0)+y);
					rotation.setRotate((float)(curW.getAngle()), bm.getWidth()/2, bm.getHeight()/2);
					scale.setScale((float)2.0*length/bm.getWidth(), (float)2.0*length/bm.getHeight());
					transl.setConcat(transl, scale);
					transl.setConcat(transl, rotation);
					s.drawBitmap(bm, transl, null);
				}
			}
		}
	
	}
	// рисуем снаряды
	public void renderShells(Canvas s)
	{
		Paint p = new Paint();
		p.setColor(Color.RED);
		for(int q=0;q<game.getShells().size();q++)
		{
			try
			{
				Shell sh = game.getShells().get(q);
				float xs = x+sh.getGX()*length+(sh.getLX()*length)/sh.getSize();
				float ys = y+sh.getGY()*length+(sh.getLY()*length)/sh.getSize();
				s.drawCircle(xs, ys, 2, p);
			}catch(Exception exc)
			{
				
			}
		}
	}
	
	// рисуем джойстик
	public void renderJoystick(Canvas s) // добавляем джойстик
	{		
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
	
	// движение танка
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
		int mainDiagonal = (int)((px - x1) * (y2 - y1) - (py - y1) * (x2 - x1));
		int pobDiagonal = (int)((px - x1) * (y1 - y2) - (py - y2) * (x2 - x1));
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
