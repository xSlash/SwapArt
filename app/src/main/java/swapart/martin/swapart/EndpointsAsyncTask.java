package swapart.martin.swapart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import swapart.martin.backend.myApi.MyApi;

/**
 * Created by Martin on 21-03-2015.
 */
class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;



    @Override
    protected String doInBackground(Pair<Context, String>... params) {

        //this.context = Context;

        if (myApiService == null) {  // Only do this once

            /* For localhost
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.2.2:8080/_ah/api/") // 10.0.2.2 is localhost's IP address in Android emulator
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
            @Override
            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
            */
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://swapart-phoenix.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }


        context = params[0].first;
        String name = params[0].second;

        if(name.equals("createUser")) {

            SharedPreferences prefs = context.getSharedPreferences("User_Object", context.MODE_PRIVATE);

            try {

                String prefs_username = prefs.getString("Username", "no username chosen");
                String prefs_password = prefs.getString("Password", "no password chosen");
                String prefs_name = prefs.getString("Name", "no name chosen");
                String prefs_city = prefs.getString("City", "no city chosen");
                String prefs_phone = prefs.getString("Phone", "no phone chosen");

                //return myApiService.nameToX(name).execute().getData();
                return myApiService.storeObject(prefs_username, prefs_password, prefs_name, prefs_city, prefs_phone).execute().getData();

            } catch (IOException e) {
                return e.getMessage();
            }
        }

        else if (name.equals("storeUserGallery")) {

            try {


                SharedPreferences prefs = context.getSharedPreferences("User_Object", context.MODE_PRIVATE);
                String tmpUser = prefs.getString("userlogin_username", "nothing");
                String tmpPass = prefs.getString("userlogin_password", "nothing2");

                return myApiService.checkLogin(tmpUser, tmpPass).execute().getData();

                //return myApiService.storeObject("testUser2", "testPW2", "testName2", "testCity2", "testPhone2").execute().getData();

                //return myApiService.checkLogin("User1","Password1").execute().getData();


            } catch (IOException e) {
                return e.getMessage();
            }
        }



        else if (name.equals("logUserIn")) {

            try {


                SharedPreferences prefs = context.getSharedPreferences("User_Object", context.MODE_PRIVATE);
                String tmpUser = prefs.getString("userlogin_username", "nothing");
                String tmpPass = prefs.getString("userlogin_password", "nothing2");

                return myApiService.checkLogin(tmpUser, tmpPass).execute().getData();

                //return myApiService.storeObject("testUser2", "testPW2", "testName2", "testCity2", "testPhone2").execute().getData();

                //return myApiService.checkLogin("User1","Password1").execute().getData();


            } catch (IOException e) {
                return e.getMessage();
            }
        }

        else {
            try {
                //return myApiService.nameToX(name).execute().getData();
                return myApiService.storeObject("testUser2", "testPW2", "testName2", "testCity2", "testPhone2").execute().getData();
                //return myApiService.storeObject("testUser2", "testPW2").execute().getData();

            } catch (IOException e) {
                return e.getMessage();
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        /*if (result.equals("Password not correct")) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
        else if (result.substring(0, 11).equals("User created")){

        }*/

        if(result.contains(":")) {
            //Login successfull
            String[] resultStringArray = result.split(":");

            SharedPreferences.Editor editor = context.getSharedPreferences("User_Object", context.MODE_PRIVATE).edit();
            editor.putString("Username", resultStringArray[0]);
            //editor.putString("Password", <We dont retrieve password>);

            editor.putString("Name", resultStringArray[1]);
            editor.putString("Phone", resultStringArray[2]);
            editor.putString("City", resultStringArray[3]);

            editor.putString("loginfo", "signedin");


            editor.commit();

            //Username - Name - Phone - City
            Toast.makeText(context, resultStringArray[0] + " - " + resultStringArray[1] + " - " + resultStringArray[2] + " - " + resultStringArray[3], Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }

    }
}


