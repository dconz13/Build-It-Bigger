package com.connolly.dillon.jokedisplayapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Dillon Connolly on 1/26/2016.
 */
public class DisplayActivity extends AppCompatActivity {

    public static final String DISPLAY_ACTIVITY_TAG = "DISPLAY_ACTIVITY_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
    }

}
