package game.main;

import game.field.Field;
import game.graphics.MainView;
import game.graphics.Renderer;
import game.graphics.SurfaceView;
import game.input.Separator;
import game.tanks.R;
import game.threads.ThreadManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class TanksActivity extends Activity{
	private Field f;
	private Separator sp;
	private MainView mv;
	private SurfaceView sv;
	private boolean isMain;
	// говорящее название
	private ThreadManager tmanager;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);             
        // убираем верхушку 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main);  
        
        sp = new Separator(getResources());
		f = new Field(sp.interpritaitonPicture(1), getResources());
        mv = new MainView(this, f, this.getWindowManager().getDefaultDisplay().getWidth(), this.getWindowManager().getDefaultDisplay().getHeight());        
		isMain = false;
		
		sv = new SurfaceView(this);
		sv.setRenderer(new Renderer());
		
    }
    // слушатель кнопки Start
    public void newgameButtonEvent(View w)
    {
    	setContentView(mv);
		isMain = true;		
		mv.createNewGame();
        tmanager = new ThreadManager(mv, mv.getGame());
        tmanager.reborn();
    }
    
    // слушатель кнопки About
    public void aboutButtonEvent(View w)
    {
    	//showing information about game
    }
    // слушатель кнопки Exit
    public void exitButtonEvent(View w) throws InterruptedException
    {
    	// may be saving or simply exit
    	System.exit(0);
    }
    
    // аккуратный выход в меню. 
    @Override 
    public boolean onKeyDown(int keykode, KeyEvent ke)
    {
    	if(keykode == KeyEvent.KEYCODE_BACK)
    	{
			if(isMain)
			{
    		    showDialogWindow();
			}
    	}
    	return true;
    }
    
    public void showDialogWindow()
    {
    	new AlertDialog.Builder(this).setTitle("Pause").setItems(R.array.menu_items,
    	new DialogInterface.OnClickListener() 
    	{			
			@Override
			public void onClick(DialogInterface dialog, int i) 
			{
				if(i == 0)
				{
					dialog.cancel();
					isMain = true;
				}
				else
				{
					setContentView(R.layout.main);
				 	try 
					{
						tmanager.destroy();
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
					isMain = false;
				}
			}
    	}).show();
    }
}
