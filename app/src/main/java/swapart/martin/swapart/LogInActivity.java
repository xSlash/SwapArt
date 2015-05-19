package swapart.martin.swapart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import swapart.martin.swapartmockup.R;


public class LogInActivity extends Activity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        //context = this;

        findViewById(R.id.OKlogInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText username = (EditText) findViewById(R.id.usernameLogInText);
                EditText password = (EditText) findViewById(R.id.passwordLogInText);

                SharedPreferences.Editor editor = getSharedPreferences("User_Object", MODE_PRIVATE).edit();
                editor.putString("userlogin_username", username.getText().toString());
                editor.putString("userlogin_password", password.getText().toString());
                editor.putString("loginfo", "attemptToLogIn");

                editor.commit();

                //new EndpointsAsyncTask().execute(new Pair<Context, String>(context, "logUserIn"));

                LogInActivity.this.startActivity(new Intent(LogInActivity.this, GalleryActivity.class));
            }
        });

        /*findViewById(R.id.OKlogInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
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
