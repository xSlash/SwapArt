package swapart.martin.swapart;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.FileInputStream;
import java.util.ArrayList;

import swapart.martin.swapartmockup.R;

public class MatchesListActivity extends Activity {

    private ListView listview;
    final Context context = this;

    private ArrayList<MatchObject> MatchObjectArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_list);

        SharedPreferences prefs = getSharedPreferences("User_Object", MODE_PRIVATE);

        int size = prefs.getInt("arrayListSize", 0);

        if (size > 0)
        {
            for (int i = 1; i <= size; i++)
            {
                String tmpTitle = prefs.getString("title_"+i, "N/A");
                String tmpArtist = prefs.getString("artist_"+i, "N/A");
                String tmpYear = prefs.getString("year_"+i, "N/A");
                String tmpDimension = prefs.getString("dimension_"+i, "N/A");
                String tmpType = prefs.getString("type_"+i, "N/A");

                //Getting image
                Bitmap tmpBitmap;

                String name = "savedImage" + i + ".jpg";
                try{
                    FileInputStream fis = context.openFileInput(name);
                    Bitmap b = BitmapFactory.decodeStream(fis);
                    fis.close();
                    tmpBitmap = b;
                    MatchObjectArrayList.add(new MatchObject(b, b, "lol"));
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                listview = (ListView) findViewById(R.id.matchesObjectlistView);

                listview.setAdapter(new MatchObjectAdapter(MatchesListActivity.this, MatchObjectArrayList));

                //listview.setAdapter(new MatchObjectAdapter(MatchesListActivity.this, MatchObjectArrayList));

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_matches_list, menu);
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
