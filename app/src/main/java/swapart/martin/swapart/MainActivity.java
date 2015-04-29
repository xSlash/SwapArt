package swapart.martin.swapart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import swapart.martin.swapartmockup.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "createUser"));

        //doStuff();

        findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, signupActivity.class));
            }
        });

        findViewById(R.id.skipButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, GalleryActivity.class));
            }
        });
        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
            }
        });

        /*findViewById(R.id.testbt).setOnClickListener((v) -> {
            new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Martin"));
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


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

    public void doStuff()
    {

        /*MyApi myApiService = null;
        Context context;

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://swapart-phoenix.appspot.com/_ah/api/");


        myApiService = builder.build();

        //context = params[0].first;
        String name = "Martin";

        try {

            Toast.makeText(MainActivity.this, myApiService.sayHi(name).execute().getData(), Toast.LENGTH_LONG).show();
            //return myApiService.nameToX(name).execute().getData();
        } catch (IOException e) {
            //return e.getMessage();
        }*/
    }
}
