package swapart.martin.swapart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        int size = prefs.getInt("numberOfMatches", 0);

        /*boolean justfoundmatch = prefs.getBoolean("justfoundmatch", false);

        if (justfoundmatch) {
            SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();
            editor.putBoolean("justfoundmatch", false);
            editor.commit();

            startActivity(new Intent(MatchesListActivity.this, MatchesActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }*/

        if (size > 0)
        {
            for (int i = 1; i <= size; i++)
            {
                String tmpTitle = prefs.getString("title_"+i, "N/A");
                String tmpArtist = prefs.getString("artist_"+i, "N/A");
                String tmpYear = prefs.getString("year_"+i, "N/A");
                String tmpDimension = prefs.getString("dimension_"+i, "N/A");
                String tmpType = prefs.getString("type_"+i, "N/A");
                String timestamp = prefs.getString("timestamp_"+i, "N/A");

                //Getting image
                Bitmap tmpBitmap = null;

                int userimgnumber = prefs.getInt("ownimgnumber_"+i ,0);

                String name = "savedImage" + userimgnumber + ".jpg";
                try{
                    FileInputStream fis = context.openFileInput(name);
                    Bitmap b = BitmapFactory.decodeStream(fis);
                    fis.close();
                    tmpBitmap = b;
                    //MatchObjectArrayList.add(new MatchObject(b, b, "lol"));
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                String likedimage = "matchedImage" + i + ".jpg";
                try{
                    FileInputStream fis = context.openFileInput(likedimage);
                    Bitmap b = BitmapFactory.decodeStream(fis);
                    fis.close();
                    //tmpBitmap = b;
                    MatchObjectArrayList.add(new MatchObject(tmpBitmap, b, timestamp));
                }
                catch(Exception e){
                    e.printStackTrace();
                }



                listview = (ListView) findViewById(R.id.matchesObjectlistView);

                listview.setAdapter(new MatchObjectAdapter(MatchesListActivity.this, MatchObjectArrayList));

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {

                        SharedPreferences prefs = getSharedPreferences("User_Object", MODE_PRIVATE);
                        int imgnum = prefs.getInt("imgnumber_"+(position+1), 0);
                        int imgown = prefs.getInt("ownimgnumber_"+(position+1), 0);


                        SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();
                        editor.putInt("likedNumber", imgnum);
                        editor.putInt("numberformatchactivity", imgown);
                        editor.commit();

                        startActivity(new Intent(MatchesListActivity.this, MatchesActivity.class));
                        //finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        //likednumber

                        //String item = ((TextView)view).getText().toString();

                        //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

                    }
                });

                //listview.setAdapter(new MatchObjectAdapter(MatchesListActivity.this, MatchObjectArrayList));

            }
        }

        findViewById(R.id.homeMatchesList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchesListActivity.this, GalleryActivity.class));
                finish();
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
            }
        });
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
