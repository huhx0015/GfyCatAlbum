package com.gfycat.album.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.gfycat.album.R;

import static android.R.attr.action;
import static android.R.attr.type;


public class HandleShareActivity extends AppCompatActivity {

    Intent intent;
    String action;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_share);

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
    }

}
