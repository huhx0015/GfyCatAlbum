package com.gfycat.album.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gfycat.album.R;
import com.gfycat.album.data.GfyPreferences;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkIfUserIsLoggedIn();
    }

    private void checkIfUserIsLoggedIn() {
        SharedPreferences gfyPreferences = GfyPreferences.initializePreferences(this);
        boolean isUserLoggedIn = GfyPreferences.getUserLoggedIn(gfyPreferences);

        if (isUserLoggedIn) {

            // TODO: Check if token is valid first.
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        finish();
    }
}
