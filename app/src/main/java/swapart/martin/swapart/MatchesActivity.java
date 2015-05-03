package swapart.martin.swapart;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import swapart.martin.swapartmockup.R;


public class MatchesActivity extends Activity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        /*File imgFile = new File("imageyoulike.png");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.Matches_likedPicture);

            myImage.setImageBitmap(myBitmap);

        }*/


        /*Bitmap b = null;
        FileInputStream fis;
        try {
            fis = openFileInput("imageyoulike.png");
            b = BitmapFactory.decodeStream(fis);
            fis.close();

        }
        catch (FileNotFoundException e) {
            //Log.d(TAG, "file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            //Log.d(TAG, "io exception");
            e.printStackTrace();
        }

        ImageView myImage = (ImageView) findViewById(R.id.Matches_likedPicture);

        myImage.setImageBitmap(b);*/

        SharedPreferences prefs = getSharedPreferences("User_Object", MODE_PRIVATE);
        int imgNumber = prefs.getInt("likedImageNumber", 0);

        ImageView myImage = (ImageView) findViewById(R.id.Matches_likedPicture);
        myImage.setImageResource(R.drawable.art1 + imgNumber);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_art, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
