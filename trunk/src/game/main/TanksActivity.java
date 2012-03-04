package game.main;
import game.graphics.*;
import game.tanks.R;
import android.app.Activity;
import android.os.Bundle;
import java.io.*;
public class TanksActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // пробный вариант записи в файл. не работает.
        final String TESTSTRING = new String("Hello Android!");
        try {
        	FileOutputStream fOut = openFileOutput("samplefile.txt", MODE_WORLD_READABLE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(TESTSTRING);
			osw.flush();
			osw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}