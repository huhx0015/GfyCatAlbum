package com.gfycat.album.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.gfycat.album.R;
import com.gfycat.album.application.GfyApplication;
import com.gfycat.album.dialog.SaveGfycatDialog;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Retrofit;

public class HandleShareActivity extends AppCompatActivity {

    Intent intent;
    String action;
    String type;

    private Unbinder unbinder;

    // SAVE GFYCAT VARIABLES
    private String saveGfycatLink;
    private String saveGfycatName;
    private String saveGfycatTags;
    private String saveGfycatDescription;

    @Inject Retrofit retrofitAdapter;

    @BindView(R.id.handle_share_progress_bar) ProgressBar handleShareProgressBar;
    @BindView(R.id.handle_share_save_text) TextView handleShareSaveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_handle_share);
        unbinder = ButterKnife.bind(this);

        // Retrofit Dagger injection for this activity.
        ((GfyApplication) getApplication()).getApiComponent().inject(this);

        initView();

        intent = getIntent();
        action = intent.getAction();
        type = intent.getType();

        if (intent.ACTION_SEND.equals(action) && type != null)
        {
            handleViewAction();
            handleSendText(intent);
            Log.d("STEVE", "ACTION_SEND");
        }
        else if (intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            handleViewAction();
            handleSendText(intent);
            Log.d("STEVE", "ACTION_SEND_MULTIPLE");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    // onBackPressed(): Don't allow users to press the back button.
    @Override
    public void onBackPressed() {}

    void handleViewAction(){
        //pop up a dialog of what to do
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
//        Toast.makeText(this,"got the action", Toast.LENGTH_LONG).show();
        Log.d("STEVE", sharedText);

        // TODO: Display a DialogFragment here. Once new gfycat data has been entered, make a call to create gfycat endpoint; also store reference locally.
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            String url = sharedText.substring(sharedText.indexOf("http"));
            displaySaveGfycatDialog(url);
        }
    }

    private void initView() {
        handleShareProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ee00d4"), android.graphics.PorterDuff.Mode.SRC_ATOP);
    }

    private void displaySaveGfycatDialog(String url) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SaveGfycatDialog saveGfycatDialog = SaveGfycatDialog.newInstance(url, this);
        saveGfycatDialog.show(fragmentManager, SaveGfycatDialog.class.getSimpleName());
    }

    public void saveGfycat(String link, String name, String tags, String description) {
        this.saveGfycatLink = link;
        this.saveGfycatName = name;
        this.saveGfycatTags = tags;
        this.saveGfycatDescription = description;

        // TODO: Handle saving action here.
    }
}
