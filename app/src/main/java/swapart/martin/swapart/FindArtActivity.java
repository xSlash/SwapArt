package swapart.martin.swapart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import swapart.martin.swapartmockup.R;


public class FindArtActivity extends Activity implements SeekBar.OnSeekBarChangeListener{

    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i = 9;
    private final Context context = this;
    //private SeekBar distanceBar;
    //private TextView distancetextview = (TextView)findViewById(R.id.distanceTV);

    @InjectView(R.id.frame) SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_find_art was the original
        setContentView(R.layout.activity_find_art2);

        ButterKnife.inject(this);

        al = new ArrayList<>();

        al.add("picture 1");
        al.add("picture 2");
        al.add("picture 3");
        al.add("picture 4");
        al.add("picture 5");
        al.add("picture 6");
        al.add("picture 7");
        al.add("picture 8");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.swipeart_element, R.id.art_element_image, al );


        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                //Toast.makeText(FindArt.this, "Left!", Toast.LENGTH_LONG).show();
                makeToast(FindArtActivity.this, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(FindArtActivity.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                al.add("picture ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                makeToast(FindArtActivity.this, "Clicked!");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_art, menu);
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

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.right)
    public void right() {
        /**
         * Trigger the right event manually.
         */
        flingContainer.getTopCardListener().selectRight();
    }

    @OnClick(R.id.left)
    public void left() {
        flingContainer.getTopCardListener().selectLeft();
    }

    @OnClick(R.id.homeFindArt)
    public void dismissActivity() { finish();}

    @OnClick(R.id.settingsFindArt)
    public void startActivity() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_settings);
        dialog.setTitle("Menu");

        Button updateButton = (Button) dialog.findViewById(R.id.checkForUpdates);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://dl.dropboxusercontent.com/u/12052609/SwapArt.apk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button settingsButton = (Button) dialog.findViewById(R.id.matchcriteriaButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog2 = new Dialog(context);
                dialog2.setContentView(R.layout.popup_settings_app);
                dialog2.setTitle("Swapart Settings");

                final TextView distancetext = (TextView)dialog2.findViewById(R.id.distanceTV);
                SeekBar distanceBar = (SeekBar)dialog2.findViewById(R.id.seekBar); // make seekbar object
                //distanceBar.setOnSeekBarChangeListener(FindArtActivity.this);


                distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                        //Do something here with new value
                        distancetext.setText("Distance: "+(progress + 1) + " km");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                        //textAction.setText("starting to track touch");

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                        seekBar.setSecondaryProgress(seekBar.getProgress());
                        //textAction.setText("ended tracking touch");
                    }
                });

                //Renting period bar
                final TextView rentingtext = (TextView)dialog2.findViewById(R.id.rentperiodtext);
                final SeekBar rentingperiodBar = (SeekBar)dialog2.findViewById(R.id.rentperiodBar); // make seekbar object
                //distanceBar.setOnSeekBarChangeListener(FindArtActivity.this);


                rentingperiodBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                        //Do something here with new value
                        if (rentingperiodBar.getProgress() <= 5) {
                            rentingperiodBar.setProgress(0);
                            rentingtext.setText("Rent period: 1-7 days");
                        }
                        else if (rentingperiodBar.getProgress() > 5 && rentingperiodBar.getProgress() <=35){
                            rentingperiodBar.setProgress(25);
                            rentingtext.setText("Rent period: 1-4 weeks");
                        }
                        else if (rentingperiodBar.getProgress() > 35 && rentingperiodBar.getProgress() <=65){
                            rentingperiodBar.setProgress(50);
                            rentingtext.setText("Rent period: 1-6 months");
                        }

                        else if (rentingperiodBar.getProgress() > 65 && rentingperiodBar.getProgress() <=90){
                            rentingperiodBar.setProgress(75);
                            rentingtext.setText("Rent period: 6-12 months");
                        }

                        else if (rentingperiodBar.getProgress() > 90){
                            rentingperiodBar.setProgress(100);
                            rentingtext.setText("Rent period: +1 year");
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                        //textAction.setText("starting to track touch");

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                        seekBar.setSecondaryProgress(seekBar.getProgress());
                        //textAction.setText("ended tracking touch");
                    }
                });


                dialog2.show();
            }
        });

        dialog.show();
        //startActivity(new Intent(FindArtActivity.this, SettingsActivity.class));
    }

    @Override
    public void onProgressChanged(SeekBar distanceBar, int progress,
                                  boolean fromUser) {
        // TODO Auto-generated method stub

        // change progress text label with current seekbar value
        //distancetextview.setText("The value is: "+progress);
        // change action text label to changing
        //textAction.setText("changing");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
        //textAction.setText("starting to track touch");

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
        seekBar.setSecondaryProgress(seekBar.getProgress());
        //textAction.setText("ended tracking touch");
    }
}
