package swapart.martin.swapart;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

    final Context context = this;
    Bitmap b;
    Bitmap tmpBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);



        ImageView liked_image = (ImageView) findViewById(R.id.matches_liked_picture);

        SharedPreferences prefs = getSharedPreferences("User_Object", MODE_PRIVATE);
        int artNumber = prefs.getInt("likedNumber", 0);

        liked_image.setImageResource(R.drawable.art1 + (artNumber-1));


        String name = "savedImage1.jpg";
        try{
            FileInputStream fis = context.openFileInput(name);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            tmpBitmap = b;

        }
        catch(Exception e){
            e.printStackTrace();
        }

        //Set own picture in matched art
        ImageView own_image = (ImageView) findViewById(R.id.matches_own_picture);
        own_image.setImageBitmap(tmpBitmap);

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
