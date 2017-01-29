package com.gfycat.album.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.gfycat.album.R;
import com.gfycat.album.application.GfyApplication;
import com.gfycat.album.data.GfyPreferences;
import com.gfycat.album.interfaces.RetrofitInterface;
import com.gfycat.album.models.GrantRequest;
import com.gfycat.album.models.GrantResponsePojo;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.splash_progress_bar) ProgressBar splashProgressBar;
    @BindView(R.id.activity_splash_layout) RelativeLayout activitySplashLayout;

    @Inject Retrofit retrofitAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);

        // Retrofit Dagger injection for this activity.
        ((GfyApplication) getApplication()).getApiComponent().inject(this);

        initView();
        checkIfUserIsLoggedIn();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        splashProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ee00d4"), android.graphics.PorterDuff.Mode.SRC_ATOP);
    }

    private void checkIfUserIsLoggedIn() {
        SharedPreferences gfyPreferences = GfyPreferences.initializePreferences(this);
        boolean isUserLoggedIn = GfyPreferences.getUserLoggedIn(gfyPreferences);

        if (isUserLoggedIn) {
            refreshToken();
        } else {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        finish();
    }

    private void refreshToken() {
        splashProgressBar.setVisibility(View.VISIBLE);
        RetrofitInterface refreshTokenRequest = retrofitAdapter.create(RetrofitInterface.class);
        Call<GrantResponsePojo> call = refreshTokenRequest.getToken(new GrantRequest(getString(R.string.gfycat_client_id), getString(R.string.gfycat_client_secret)));
        call.enqueue(new Callback<GrantResponsePojo>() {
            @Override
            public void onResponse(Call<GrantResponsePojo> call, Response<GrantResponsePojo> response) {

                if (response.isSuccessful()) {
                    GrantResponsePojo responsePojo = response.body();

                    SharedPreferences gfyPrefs = GfyPreferences.initializePreferences(SplashActivity.this);
                    GfyPreferences.setUserLoggedIn(true, gfyPrefs);
                    GfyPreferences.setAccessToken(responsePojo.getAccessToken(), gfyPrefs);
                    GfyPreferences.setRefreshToken(responsePojo.getRefreshToken(), gfyPrefs);

                    launchMainIntent();
                } else {
                    splashProgressBar.setVisibility(View.GONE);
                    displayRefreshResponseSnackbar(getString(R.string.refresh_token_error_message));
                }
            }

            @Override
            public void onFailure(Call<GrantResponsePojo> call, Throwable t) {
                splashProgressBar.setVisibility(View.GONE);
                displayRefreshResponseSnackbar(getString(R.string.refresh_token_error_message));
            }
        });
    }

    private void displayRefreshResponseSnackbar(String message) {
        Snackbar snackbar = Snackbar
                .make(activitySplashLayout, message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshToken();
                    }
                });
        snackbar.show();
    }

    private void launchMainIntent() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
