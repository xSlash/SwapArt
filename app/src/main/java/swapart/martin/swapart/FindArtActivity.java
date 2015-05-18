package swapart.martin.swapart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import swapart.martin.swapartmockup.R;


public class FindArtActivity extends Activity implements SeekBar.OnSeekBarChangeListener{

    private ArrayList<String> al;
    private ArrayList<String> likedImagesString = new ArrayList<>();
    private ArrayList<ImageView> likedImageView = new ArrayList<>();
    private ArrayList<Bitmap> likedBitmap = new ArrayList<>();
    private int randnumber;

    private String currentIMG;
    private int IMGnumber;
    private int IMGnumberOld;

    private ArrayAdapter<String> arrayAdapter;
    private int i = 9;
    private final Context context = this;

    @InjectView(R.id.frame) SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_find_art was the original
        setContentView(R.layout.activity_find_art2);

        ButterKnife.inject(this);

        al = new ArrayList<>();

        /*al.add("picture 1");
        al.add("picture 2");
        al.add("picture 3");
        al.add("picture 4");
        al.add("picture 5");
        al.add("picture 6");
        al.add("picture 7");
        al.add("picture 8");*/

        al.add("");
        al.add("");
        al.add("");
        al.add("");
        al.add("");
        al.add("");
        al.add("");
        al.add("");

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.swipeart_element, R.id.art_element_image, al ) {
            @Override
            public View getView(int position, View v, ViewGroup parent) {
                v = super.getView(position, v, parent);
                ImageView billede = (ImageView) v.findViewById(R.id.billede);

                Random r = new Random();
                int randomnumber = r.nextInt(5);
                randnumber = randomnumber;

                billede.setImageResource(R.drawable.art1 + randomnumber );
                if (position == 0) {
                    currentIMG = "R.drawable.art" + Integer.toString(randomnumber + 1);

                    IMGnumberOld = IMGnumber;
                    IMGnumber = randomnumber+1;

                    SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();
                    editor.putInt("likedNumber", IMGnumberOld);

                    editor.commit();

                    //Store current front Image
                    /*Bitmap imageBitmap = ((BitmapDrawable)billede.getDrawable()).getBitmap();

                    String name = "likedImage.jpg";
                    FileOutputStream out;
                    try {
                        out = context.openFileOutput(name, Context.MODE_PRIVATE);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
                return v;
            }
        };


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
                //Images the user likes

                String temp = (String) dataObject;
                /*likedImagesString.add(temp);
                ImageView img = (ImageView) findViewById(R.id.billede);
                likedImageView.add(img);
                Drawable draw = img.getDrawable();


                Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
                likedBitmap.add(bitmap);

                //arrayAdapter.getView(0, R.layout.swipeart_element, getParent());



                FileOutputStream out = null;
                try {
                    out = new FileOutputStream("imageyoulike.png");
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/ //End of try

                SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();
                editor.putInt("likedImageNumber", randnumber);
                editor.putString("likedImageText", temp);
                editor.commit();

                makeToast(FindArtActivity.this, "Right!" + " + " + Integer.toString(randnumber));

                startActivity(new Intent(FindArtActivity.this, MatchesActivity.class));
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                //al.add("picture ".concat(String.valueOf(i)));

                al.add("");

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

        final Context context = this;



        findViewById(R.id.infoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //HERE
                if (IMGnumber == 1) {
                    new SweetAlertDialog(context)
                            .setTitleText("Target" )
                            .setContentText("By: Michelangelo \nYear: 520 \nSize: 0,8 m x 1,2 m \nType: Brush paint")
                            .show();
                }

                else if (IMGnumber == 2) {
                    new SweetAlertDialog(context)
                            .setTitleText("The Forrest" )
                            .setContentText("By: Donatello \nYear: 1436 \nSize: 0,6 m x 0,9 m \nType: Finger paint")
                            .show();
                }

                else if (IMGnumber == 3) {
                    new SweetAlertDialog(context)
                            .setTitleText("Nature of China" )
                            .setContentText("By: Raphael Sanzio \nYear: 1778 \n Size: 2,1 m x 2,5 m \nType: Oil")
                            .show();
                }

                else if (IMGnumber == 4) {
                    new SweetAlertDialog(context)
                            .setTitleText("Night walk" )
                            .setContentText("By: Leonardo da Vinci \nYear: 1346 \nSize: 1 m x 1,6 m \nType: Airbrush ")
                            .show();
                }

                else if (IMGnumber == 5) {
                    new SweetAlertDialog(context)
                            .setTitleText("The Muse" )
                            .setContentText("By: Pablo Picasso \nYear: 1996 \nSize: 1,5 m x 1,8 m \nType: Spray paint")
                            .show();
                }

                else {
                    new SweetAlertDialog(context)
                            .setTitleText("Something went wrong! " )
                                    //.setContentText("It's pretty, isn't it?")
                                    //.setContentText(Integer.toString(k))
                            .setContentText("We shouldn't end here")
                            .show();
                }

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

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_settings);

        //dialog.setTitle("Menu");

        Button updateButton = (Button) dialog.findViewById(R.id.checkForUpdates);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://dl.dropboxusercontent.com/u/12052609/SwapArt.apk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button profileButton = (Button) dialog.findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog2 = new Dialog(context);
                dialog2.setContentView(R.layout.popup_profile);
                dialog2.setTitle("Edit profile");

                SharedPreferences prefs = getSharedPreferences("User_Object", MODE_PRIVATE);

                EditText name = (EditText) dialog2.findViewById(R.id.nameText);
                name.setText(prefs.getString("Name", "no name chosen"));
                EditText city = (EditText) dialog2.findViewById(R.id.cityText);
                city.setText(prefs.getString("City", "no city chosen"));
                EditText phone = (EditText) dialog2.findViewById(R.id.phoneText);
                phone.setText(prefs.getString("Phone", "no number set"));


                Button saveChanges = (Button) dialog2.findViewById(R.id.saveProfileButton);
                saveChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();

                        EditText name = (EditText) dialog2.findViewById(R.id.nameText);
                        EditText city = (EditText) dialog2.findViewById(R.id.cityText);
                        EditText phone = (EditText) dialog2.findViewById(R.id.phoneText);

                        editor.putString("Name", name.getText().toString());
                        editor.putString("City", city.getText().toString());
                        editor.putString("Phone", phone.getText().toString());

                        editor.commit();

                        dialog2.dismiss();

                        new EndpointsAsyncTask().execute(new Pair<Context, String>(context, "update"));
                    }
                });

                dialog2.show();

            }

            });

        Button matchCriteriaButton = (Button) dialog.findViewById(R.id.matchcriteriaButton);
        matchCriteriaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog2 = new Dialog(context);
                dialog2.setContentView(R.layout.popup_match_criteria);
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

                Button saveChanges = (Button) dialog2.findViewById(R.id.saveChangesButton);
                saveChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog2.dismiss();
                    }
                });


                dialog2.show();
            }
        });

        Button shareButton = (Button) dialog.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share SwapArt via"));

                /*final Dialog dialog2 = new Dialog(context);
                dialog2.setContentView(R.layout.popup_share_swapart);
                dialog2.setTitle("Share Swapart");

                Button closeShareDialog = (Button) dialog2.findViewById(R.id.closeShareButton);
                closeShareDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog2.dismiss();
                    }
                });

                dialog2.show();*/

            }

        });

        Button contactButton = (Button) dialog.findViewById(R.id.contactButton);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog2 = new Dialog(context);
                dialog2.setContentView(R.layout.popup_contact_us);
                dialog2.setTitle("Contact us");

                Button closeShareDialog = (Button) dialog2.findViewById(R.id.closeContact);
                closeShareDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog2.dismiss();
                    }
                });

                dialog2.show();

            }

        });

        Button closesettings = (Button) dialog.findViewById(R.id.closeSettings);
        closesettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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
