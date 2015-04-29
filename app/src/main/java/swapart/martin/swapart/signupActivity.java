package swapart.martin.swapart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import swapart.martin.swapartmockup.R;


public class signupActivity extends Activity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.checkBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupActivity.this.startActivity(new Intent(signupActivity.this, TermsConditionsActivity.class));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.usernameText);
                EditText password = (EditText) findViewById(R.id.passwordText);

                EditText name = (EditText) findViewById(R.id.nameText);
                EditText city = (EditText) findViewById(R.id.cityText);
                EditText phone = (EditText) findViewById(R.id.phoneText);

                SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();
                editor.putString("Username", username.getText().toString());
                editor.putString("Password", password.getText().toString());

                editor.putString("Name", name.getText().toString());
                editor.putString("City", city.getText().toString());
                editor.putString("Phone", phone.getText().toString());

                editor.commit();

                //retrieve saved User preferences (name, city etc)
                /*SharedPreferences prefs = getSharedPreferences("User_Object", MODE_PRIVATE);
                String prefs_username = prefs.getString("Username", "no username chosen");
                String prefs_password = prefs.getString("Password", "no password chosen");
                String prefs_name = prefs.getString("Name", "no name chosen");
                String prefs_city = prefs.getString("City", "no city chosen");
                String prefs_phone = prefs.getString("phone", "no name chosen");*/

                //EndpointsAsyncTask eat = ;
                //new EndpointsAsyncTask().execute(new Pair<Context, String>(context, "createUser"));



                signupActivity.this.startActivity(new Intent(signupActivity.this, GalleryActivity.class));
            }
        });
    }

    //First task will be here. Test GitHub changes

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
