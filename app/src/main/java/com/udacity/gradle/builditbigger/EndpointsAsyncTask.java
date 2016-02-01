package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.example.builditbigger.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by Dillon Connolly on 1/26/2016.
 */
public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private AsyncResponse mResponseCallback;


    // Custom interface to handle events outside of the AsyncTask based on the progress
    public interface AsyncResponse {
        void sendIntent(String joke);
        void startProgress();
        void stopProgress();
    }

    public EndpointsAsyncTask(AsyncResponse listener){
       this.mResponseCallback = listener;
    }

    @Override
    protected void onPostExecute(String s) {
        mResponseCallback.stopProgress();
        mResponseCallback.sendIntent(s);
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        mResponseCallback.startProgress();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Change ip to google endpoint server ip. This is computer ip address on same network
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e){
            return e.getMessage();
        }
    }
}
