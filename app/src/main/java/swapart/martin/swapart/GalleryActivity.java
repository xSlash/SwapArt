package swapart.martin.swapart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

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
        findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.this.startActivity(new Intent(GalleryActivity.this, AddArtMenuActivity.class));
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.this.startActivity(new Intent(GalleryActivity.this, EditArtActivity.class));
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.this.startActivity(new Intent(GalleryActivity.this, SearchArtActivity.class));
            }
        });
        findViewById(R.id.test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                //startPopUp();
                //Toast.makeText(GalleryActivity.this,"Title2: " + title, Toast.LENGTH_LONG);
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GalleryActivity.this, FindArt.class));
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
            dialog.setContentView(R.layout.gallery_popup_edit_art);
            dialog.setTitle("Art information");

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

                    Toast.makeText(context, "ArtObject count: " + ArtObjectArrayList.size(), Toast.LENGTH_LONG).show();

                    //ArtObjectArrayList.indexOf(0)

                    listview = (ListView) findViewById(R.id.artObjectlistView);
                    //listview.setAdapter(new ArtObjectAdapter(GalleryActivity.this, new String[]{ title }, imageBitmap));
                    listview.setAdapter(new ArtObjectAdapter(GalleryActivity.this, ArtObjectArrayList));

                    /*Toast.makeText(GalleryActivity.this,"Title: " + title, Toast.LENGTH_LONG);

                    Button bt = (Button) findViewById(R.id.test_button);
                    bt.setText(title);*/

                    dialog.dismiss();

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
