package com.gfycat.album.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.gfycat.album.R;
import com.gfycat.album.application.GfyApplication;
import com.gfycat.album.data.GfyPreferences;
import com.gfycat.album.interfaces.RetrofitInterface;
import com.gfycat.album.models.CreateGfycatPojo;
import com.gfycat.album.models.CreateGfycatRequest;
import com.gfycat.album.models.GfyItem;
import com.gfycat.album.models.GfycatPojo;
import com.gfycat.album.models.Gif;
import com.gfycat.album.models.StatusGfycatPojo;
import com.gfycat.album.models.Tag;
import com.gfycat.album.utils.DatabaseHelper;
import java.util.ArrayList;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.gfycat.album.dialog.SaveGfycatDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Retrofit;

public class HandleShareActivity extends AppCompatActivity {

    Intent intent;
    String action;
    String type;
    String curGfyName;
    String gfyId;
    Handler uploadStatusHandler = new Handler();
    private Runnable handleStatus = new Runnable() {
        @Override
        public void run() {
            handleUploadStatus();
            uploadStatusHandler.removeCallbacks(handleStatus);
        }
    };

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

        if (intent.ACTION_SEND.equals(action) && type != null) {
            handleSendText(intent);
        } else if (intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            handleSendText(intent);
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

        // TODO: Display a DialogFragment here. Once new gfycat data has been entered, make a call to create gfycat endpoint; also store reference locally.

        RetrofitInterface createGfycatRequest = retrofitAdapter.create(RetrofitInterface.class);
        Call<CreateGfycatPojo> call = createGfycatRequest.createGfycat(GfyPreferences.getAccessToken(GfyPreferences.initializePreferences(this)),
                                                                        new CreateGfycatRequest(saveGfycatLink, saveGfycatName));
        call.enqueue(new Callback<CreateGfycatPojo>() {
            @Override
            public void onResponse(Call<CreateGfycatPojo> call, Response<CreateGfycatPojo> response) {

                if (response.isSuccessful()) {
                    CreateGfycatPojo responsePojo = response.body();
                    curGfyName = responsePojo.getGfyname();
                    handleUploadStatus();
                }
            }

            @Override
            public void onFailure(Call<CreateGfycatPojo> call, Throwable t) {
                Log.d("onFailure: ", "oh no");
            }
        });
    }

    void handleUploadStatus() {

        //call upload endpoint
        RetrofitInterface statusInterface = retrofitAdapter.create(RetrofitInterface.class);
        Call<StatusGfycatPojo> call = statusInterface.checkUploadStatus(GfyPreferences.getAccessToken(GfyPreferences.initializePreferences(this)),curGfyName);
        call.enqueue(new Callback<StatusGfycatPojo>() {
            @Override
            public void onResponse(Call<StatusGfycatPojo> call, Response<StatusGfycatPojo> response) {
                StatusGfycatPojo statusBody = response.body();
                String status = statusBody.getTask();


                if (!status.equals("complete")) {
                    Log.d("STEVE", "calling handler for " + curGfyName);
                    uploadStatusHandler.postDelayed(handleStatus, 5000);
                } else {
                    uploadStatusHandler.removeCallbacks(handleStatus);
                    Log.d("STEVE", "handler complete! for " + curGfyName);
                    gfyId = statusBody.getGfyId();
                    updateGfycatDB();
                }
            }

            @Override
            public void onFailure(Call<StatusGfycatPojo> call, Throwable t) {

            }
        });
    }


    void updateGfycatDB() {
        //FrightenedMassiveDoe
        RetrofitInterface statusInterface = retrofitAdapter.create(RetrofitInterface.class);
        Call<GfycatPojo> call = statusInterface.getGfyCat(GfyPreferences.getAccessToken(GfyPreferences.initializePreferences(this)), gfyId);
        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.initialize();

        call.enqueue(new Callback<GfycatPojo>() {
            @Override
            public void onResponse(Call<GfycatPojo> call, Response<GfycatPojo> response) {
                if (response.isSuccessful()) {
                    GfyItem gifResponse = response.body().getGfyItem();
                    ArrayList<Tag> tags = new ArrayList<>();

                    if (gifResponse.getTags() != null) {
                        for (String item : gifResponse.getTags()) {
                            Tag newTag = new Tag(item);
                            tags.add(newTag);
                        }
                    } else if (saveGfycatTags != null) {
                        String[] tokens = saveGfycatTags.split("[ .,?!]+");
                        for (String token : tokens) {
                            Tag newTag = new Tag(token);
                            tags.add(newTag);
                        }
                    } else {
                        Log.d("STEVE", "forcing cat");
                        Tag newTag = new Tag("cats");
                        tags.add(newTag);
                    }

                    Gif newGif = new Gif(tags, saveGfycatName, saveGfycatDescription, gifResponse.getGifUrl(), gifResponse.getMobilePosterUrl(), dbHelper.getIndex());
                    dbHelper.updateGifs(newGif);
                    finish();
                } else {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<GfycatPojo> call, Throwable t) {
                finish();
            }
        });
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

        handleViewAction();
    }
}
