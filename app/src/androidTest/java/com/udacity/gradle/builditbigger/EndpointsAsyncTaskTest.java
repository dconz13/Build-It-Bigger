package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.v4.util.Pair;
import android.test.AndroidTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dillon Connolly on 1/27/2016.
 */
public class EndpointsAsyncTaskTest extends AndroidTestCase implements EndpointsAsyncTask.AsyncResponse {

    private static boolean called;
    private final CountDownLatch mTimer = new CountDownLatch(1);;
    private String mJoke;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        called = false;
    }

    public void testNonEmptyResult() throws InterruptedException {
        new EndpointsAsyncTask(this).execute();
        mTimer.await(30, TimeUnit.SECONDS);
        assertTrue(called);
        assertNotNull(mJoke);
        assertNotSame(mJoke, "");
    }

    @Override
    public void sendIntent(String joke) {
        mTimer.countDown();
        called = true;
        mJoke = joke;
    }

    @Override
    public void startProgress() {

    }

    @Override
    public void stopProgress() {

    }
}
