package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.connolly.dillon.jokedisplayapp.DisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.AsyncResponse {

    private InterstitialAd mInterstitialAd;
    private ProgressBar mProgressBar;
    private String mJokeHolder = "";
    private boolean closed = false;
    private boolean asyncDone = false;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            mJokeHolder = savedInstanceState.getString("mJokeHolder");
            asyncDone = savedInstanceState.getBoolean("asyncDone");
        }
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("mJokeHolder",mJokeHolder);
        outState.putBoolean("asyncDone", asyncDone);
        super.onSaveInstanceState(outState);
    }

    // Loads the Interstitial Ad and handles the ad listener callbacks
    public void loadInterstitialAd(Context context){
        closed = false;
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                showInterstitialAd();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                closed = true;
            }

            @Override
            public void onAdClosed() {
                closed = true;
                if(asyncDone){
                    sendIntent(mJokeHolder);
                }
                super.onAdClosed();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    // Reveals the interstitial Ad
    public void showInterstitialAd(){
        if(mInterstitialAd.isLoaded()){
            stopProgress();
            mInterstitialAd.show();
        }
    }

    // Sends the joke string to the android library
    @Override
    public void sendIntent(String joke) {
        if(closed) {
            Intent intent = new Intent(getActivity(), DisplayActivity.class);
            if(joke == null)
                joke = "";
            intent.putExtra(DisplayActivity.DISPLAY_ACTIVITY_TAG, joke);
            startActivity(intent);
            asyncDone = false;
        } else {
            asyncDone = true;
            mJokeHolder = joke;
        }
    }

    // Starts the ProgressBar spinner
    @Override
    public void startProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        loadInterstitialAd(getContext());
    }

    // Stops the ProgressBar spinner
    @Override
    public void stopProgress() {
        if(mInterstitialAd.isLoaded()){
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
