package game.main;
import game.graphics.*;
import game.tanks.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TanksActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
    }
    
    public void newgameButtonEvent(View w)
    {
    	// actions to start new game
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