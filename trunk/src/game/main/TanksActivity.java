package game.main;

import game.field.Field;
import game.graphics.MainView;
import game.input.Separator;
import game.tanks.R;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class TanksActivity extends Activity {
	Field f;
	Separator sp;
	MainView mv;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);             
        // убираем верхушку 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main);  
    }
    // слушатель кнопки Start
    public void newgameButtonEvent(View w)
    {
    	sp = new Separator(getResources());
        try 
        {
			sp.interpritation(1);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
        try {
			f = new Field(sp.interpritation(1));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
        mv = new MainView(this, f, R.drawable.control);        
    	setContentView(mv);
    }
    
    // слушатель кнопки About
    public void aboutButtonEvent(View w)
    {
    	//showing information about game
    }
    // слушатель кнопки Exit
    public void exitButtonEvent(View w)
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
    		setContentView(R.layout.main);
    	}
    	return true;
    }
}