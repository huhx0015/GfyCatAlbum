package com.gfycat.album.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class AuthenticationActivity extends AppCompatActivity {

    public static final String LOG_TAG = AuthenticationActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "Authentication!");
    }
}
