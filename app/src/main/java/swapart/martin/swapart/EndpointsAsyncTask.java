package swapart.martin.swapart;

import android.content.Context;
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

            try {
                //return myApiService.nameToX(name).execute().getData();
                return myApiService.storeObject("someuser1", "somepassword2", "somename2", "somecity2", "somephone2").execute().getData();
                //return myApiService.storeObject("someuser1", "somepassword2").execute().getData();

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
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}


