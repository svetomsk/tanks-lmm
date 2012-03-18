package game.main;

import game.field.Field;
import game.graphics.MainView;
import game.input.Separator;
import game.tanks.R;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TanksActivity extends Activity {
	Field f;
	Separator sp;
	MainView mw;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);          
        
    }
    
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
        mw = new MainView(this, f);
    	setContentView(mw);
    }
    
    public void aboutButtonEvent(View w)
    {
    	//showing information about game
    }
    
    public void exitButtonEvent(View w)
    {
    	// may be saving or simply exit
    	System.exit(0);
    }
}