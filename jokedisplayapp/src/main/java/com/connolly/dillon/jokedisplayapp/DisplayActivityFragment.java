package com.connolly.dillon.jokedisplayapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Dillon Connolly on 1/26/2016.
 */
public class DisplayActivityFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.display_activity_fragment, container, false);

        Intent intent = getActivity().getIntent();
        String joke = intent.getStringExtra(DisplayActivity.DISPLAY_ACTIVITY_TAG);
        TextView jokeTextView = (TextView) rootView.findViewById(R.id.joke_textView);

        if(joke != null && joke.length() > 0){
            jokeTextView.setText(joke);
        } else {
            jokeTextView.setText(getString(R.string.joke_error));
        }
        return rootView;
    }
}
