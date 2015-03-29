package swapart.martin.swapart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import swapart.martin.swapartmockup.R;


public class GalleryActivity extends Activity {

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
                startPopUp();
            }
        });
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
