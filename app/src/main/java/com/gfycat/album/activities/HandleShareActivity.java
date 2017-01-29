package com.gfycat.album.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.gfycat.album.R;
import com.gfycat.album.application.GfyApplication;
import javax.inject.Inject;
import retrofit2.Retrofit;

public class HandleShareActivity extends AppCompatActivity {

    Intent intent;
    String action;
    String type;

    @Inject Retrofit retrofitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_share);

        // Retrofit Dagger injection for this activity.
        ((GfyApplication) getApplication()).getApiComponent().inject(this);

        intent = getIntent();
        action = intent.getAction();
        type = intent.getType();

        if (intent.ACTION_SEND.equals(action) && type != null)
        {
            handleViewAction();
            Log.d("STEVE", "ACTION_SEND");
        }
        else if (intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            handleViewAction();
            Log.d("STEVE", "ACTION_SEND_MULTIPLE");
        }
    }

    void handleViewAction(){
        //pop up a dialog of what to do
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
//        Toast.makeText(this,"got the action", Toast.LENGTH_LONG).show();
        Log.d("STEVE", sharedText);

        // TODO: Display a DialogFragment here. Once new gfycat data has been entered, make a call to create gfycat endpoint; also store reference locally.
    }

}
