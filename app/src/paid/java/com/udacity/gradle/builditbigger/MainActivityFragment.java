package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.connolly.dillon.jokedisplayapp.DisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.AsyncResponse{

    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);

        return root;
    }

    // Sends the joke string to the android library
    @Override
    public void sendIntent(String joke) {
        Intent intent = new Intent(getActivity(), DisplayActivity.class);
        intent.putExtra(DisplayActivity.DISPLAY_ACTIVITY_TAG, joke);
        startActivity(intent);
    }

    // Starts the ProgressBar spinner
    @Override
    public void startProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    // Stops the ProgressBar spinner
    @Override
    public void stopProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
