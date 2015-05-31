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
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileInputStream;

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



        int ownimgnumber = prefs.getInt("numberformatchactivity", 1);

        String name = "savedImage" + ownimgnumber + ".jpg";
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





        findViewById(R.id.matches_goto_matchlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchesActivity.this, MatchesListActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        findViewById(R.id.matches_searchart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchesActivity.this, FindArtActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        findViewById(R.id.matches_chatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "12345678";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });



        findViewById(R.id.homeMatches).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MatchesActivity.this, GalleryActivity.class));
                finish();
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
            }
        });

        findViewById(R.id.settingsMatches).setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
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
