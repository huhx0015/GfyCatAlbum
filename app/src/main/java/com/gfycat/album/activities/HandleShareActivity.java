package com.gfycat.album.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.gfycat.album.R;
import com.gfycat.album.application.GfyApplication;

import com.gfycat.album.constants.GfyConstants;
import com.gfycat.album.data.GfyPreferences;
import com.gfycat.album.interfaces.RetrofitInterface;
import com.gfycat.album.models.CreateGfycatPojo;
import com.gfycat.album.models.CreateGfycatRequest;
import com.gfycat.album.models.GfycatPojo;
import com.gfycat.album.models.Gif;
import com.gfycat.album.models.StatusGfycatPojo;
import com.gfycat.album.models.Tag;
import com.gfycat.album.utils.DatabaseHelper;
import com.google.common.util.concurrent.Runnables;


import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HandleShareActivity extends AppCompatActivity {

    Intent intent;
    String action;
    String type;
    String curGfyName;
    Handler uploadStatusHandler = new Handler();
    private Runnable handleStatus = new Runnable() {
        @Override
        public void run() {
            handleUploadStatus();
        }
    };

    @Inject
    Retrofit retrofitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_share);

        // Retrofit Dagger injection for this activity.
        ((GfyApplication) getApplication()).getApiComponent().inject(this);

        intent = getIntent();
        action = intent.getAction();
        type = intent.getType();

        if (intent.ACTION_SEND.equals(action) && type != null) {
            handleViewAction();
        } else if (intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            handleViewAction();
        }
    }

    void handleViewAction() {
        //pop up a dialog of what to do
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
//        Toast.makeText(this,"got the action", Toast.LENGTH_LONG).show();

        // TODO: Display a DialogFragment here. Once new gfycat data has been entered, make a call to create gfycat endpoint; also store reference locally.

        RetrofitInterface createGfycatRequest = retrofitAdapter.create(RetrofitInterface.class);
        Call<CreateGfycatPojo> call = createGfycatRequest.createGfycat(GfyPreferences.getAccessToken(GfyPreferences.initializePreferences(this)),
                                                                        new CreateGfycatRequest("http://i.imgur.com/6PtIS4p.gifv", "some title"));
        call.enqueue(new Callback<CreateGfycatPojo>() {
            @Override
            public void onResponse(Call<CreateGfycatPojo> call, Response<CreateGfycatPojo> response) {

                if (response.isSuccessful()) {
                    CreateGfycatPojo responsePojo = response.body();

                    //sample respose: {"isOk":true,"gfyname":"FrightenedMassiveDoe","secret":"4m29z50ii9gam7vi","uploadType":"fetch","fetchUrl":"http://i.imgur.com/QlxKcey.gif"}
//                    String newURL = GfyConstants.UPLOADED_URL + responsePojo.getGfyname();
                    curGfyName = responsePojo.getGfyname();
                    Log.d("STEVE ", curGfyName);
                    handleUploadStatus();
                    /*
                    launchMainIntent();*/
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
                    uploadStatusHandler.postDelayed(handleStatus, 1000);
                } else {
                    uploadStatusHandler.removeCallbacks(handleStatus);
                    Log.d("STEVE", "handler complete! for " + curGfyName);
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
        Call<GfycatPojo> call = statusInterface.getGfyCat(GfyPreferences.getAccessToken(GfyPreferences.initializePreferences(this)),curGfyName);
        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.initialize();

        call.enqueue(new Callback<GfycatPojo>() {
            @Override
            public void onResponse(Call<GfycatPojo> call, Response<GfycatPojo> response) {
                GfycatPojo gifResponse = response.body();
                ArrayList<Tag> tags = new ArrayList<>();

                Log.d("STEVE", "updating updateGfycatDB");

                if (gifResponse.getTags() != null) {
                    for (String item : gifResponse.getTags()) {
                        Tag newTag = new Tag("cats");
                        tags.add(newTag);
                        Log.d("STEVE", "how many of me in item");
                    }
                }
                else
                {
                    Log.d("STEVE", "forcing cat");
                    Tag newTag = new Tag("cats");
                    tags.add(newTag);
                }
                //Gif (ArrayList<Tag> tags, @Nullable String userGifName, @Nullable String textDescription, String gyfcatURL, String thumbnailURL, int index){

                if (gifResponse == null){
                    Log.d("STEVE", "response is null??");
                }


                Gif newGif = new Gif(tags, null, null, gifResponse.getWebmUrl(), gifResponse.getMobilePosterUrl(), dbHelper.getIndex());
                dbHelper.updateGifs(newGif);

            }

            @Override
            public void onFailure(Call<GfycatPojo> call, Throwable t) {

            }
        });
    }
}
