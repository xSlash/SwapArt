package swapart.martin.swapart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import swapart.martin.swapartmockup.R;


public class GalleryActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String title;
    private String artist;
    private String year;
    private String dimensions;
    private String type;
    private ListView listview;

    private ArrayList<ArtObject> ArtObjectArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        //new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "createUser"));


        SharedPreferences prefs = getSharedPreferences("User_Object", MODE_PRIVATE);

        if (prefs.getString("loginfo", "nothing").equals("attemptToLogIn")) {

            new EndpointsAsyncTask().execute(new Pair<Context, String>(context, "logUserIn"));

            //Toast.makeText(context, "You attempted to login", Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();
            editor.putString("loginfo", "nothing");
            editor.commit();
        }

        else if (prefs.getString("loginfo", "nothing").equals("signup")) {

            new EndpointsAsyncTask().execute(new Pair<Context, String>(context, "createUser"));

            SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();
            editor.putString("loginfo", "nothing");
            editor.commit();

        }

        int size = prefs.getInt("arrayListSize", 0);

        //Toast.makeText(context, "Size: " + Integer.toString(size), Toast.LENGTH_LONG).show();
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
                    ArtObjectArrayList.add(new ArtObject(tmpTitle, tmpArtist, tmpYear, tmpDimension, tmpType, b));
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                listview = (ListView) findViewById(R.id.artObjectlistView);

                listview.setAdapter(new ArtObjectAdapter(GalleryActivity.this, ArtObjectArrayList));





                /*
                editor.putString("title_"+i, title);
                    editor.putString("artist_"+i, artist);
                    editor.putString("year_"+i, year);
                    editor.putString("dimension_"+i, dimensions);
                    editor.putString("type_"+i, type);
                 */

            }
        }

        else {
            new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("Welcome to Swapart!" )
                    .setContentText("To get started, take a picture, by pressing the camera icon, and add a picture to your gallery")
                    .setCustomImage(R.drawable.cameraicon)
                    .show();

        }




        findViewById(R.id.matchesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.this.startActivity(new Intent(GalleryActivity.this, MatchesActivity.class));
            }
        });

        /*findViewById(R.id.old_matches).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.this.startActivity(new Intent(GalleryActivity.this, MatchesActivity.class));
            }
        });*/
        findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        findViewById(R.id.findArtButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GalleryActivity.this, FindArtActivity.class));
            }
        });

        /*findViewById(R.id.old_find_art).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GalleryActivity.this, FindArtActivity.class));
            }
        });*/
        findViewById(R.id.settingsGallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

    }


    //Start camera app
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Get result from camera, after taking picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Create popup
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.gallery_popup_edit_art);

            //dialog.setTitle("Art information");

            final ImageView mImageView = (ImageView) dialog.findViewById(R.id.popUpImg);
            mImageView.setImageBitmap(imageBitmap);

            Button saveButton = (Button) dialog.findViewById(R.id.save_button);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText edt = (EditText) dialog.findViewById(R.id.title_editText);
                    title = edt.getText().toString();
                    EditText edt2 = (EditText) dialog.findViewById(R.id.artist_editText);
                    artist = edt2.getText().toString();
                    EditText edt3 = (EditText) dialog.findViewById(R.id.year_editText);
                    year = edt3.getText().toString();
                    EditText edt4 = (EditText) dialog.findViewById(R.id.dimensions_editText);
                    dimensions = edt4.getText().toString();
                    EditText edt5 = (EditText) dialog.findViewById(R.id.type_editText);
                    type = edt5.getText().toString();

                    ArtObjectArrayList.add(new ArtObject(title, artist, year, dimensions, type, imageBitmap));



                    //ArtObjectArrayList.indexOf(0)

                    listview = (ListView) findViewById(R.id.artObjectlistView);
                    //listview.setAdapter(new ArtObjectAdapter(GalleryActivity.this, new String[]{ title }, imageBitmap));
                    listview.setAdapter(new ArtObjectAdapter(GalleryActivity.this, ArtObjectArrayList));

                    //Save data to file.
                    int i = ArtObjectArrayList.size();

                    String name = "savedImage" + i + ".jpg";
                    FileOutputStream out;
                    try {
                        out = context.openFileOutput(name, Context.MODE_PRIVATE);
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();
                    editor.putString("title_"+i, title);
                    editor.putString("artist_"+i, artist);
                    editor.putString("year_"+i, year);
                    editor.putString("dimension_"+i, dimensions);
                    editor.putString("type_"+i, type);
                    editor.putInt("arrayListSize" ,ArtObjectArrayList.size());

                    editor.commit();


                    Toast.makeText(context, "ArtObject count: " + ArtObjectArrayList.size() + ".", Toast.LENGTH_LONG).show();

                    dialog.dismiss();

                    if (ArtObjectArrayList.size() == 1) {
                        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                .setTitleText("Find art!" )
                                .setContentText("To start searching for art, press the search icon at the bottom")
                                .setCustomImage(R.drawable.searcharticon)
                                .show();
                    }

                }
            });

            dialog.show();

        }
    }

    final Context context = this;

    public void startPopUp() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.gallery_popup_edit_art);
        dialog.setTitle("Art information");

        Button saveButton = (Button) dialog.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        /*try {
            final LayoutInflater inflater = (LayoutInflater) GalleryActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.gallery_popup_edit_art, (ViewGroup) findViewById(R.id.popup_element));
            //Get screensize
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            pwindo = new PopupWindow(layout, width-40, height-40, true);

            //RelativeLayout back_dim = (RelativeLayout) findViewById(R.id.galleryRelativeLayout);
            //back_dim.setAlpha(220);

            //Setting animation for popup
            pwindo.setAnimationStyle(R.style.animation);


            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            //R.layout.activity_gallery
            getWindow().getDecorView().setBackgroundColor(Color.BLACK);

            layout.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                }

            });

        }
        catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
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
