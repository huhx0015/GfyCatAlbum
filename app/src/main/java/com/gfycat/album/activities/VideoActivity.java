package com.gfycat.album.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;
import com.gfycat.album.R;
import com.gfycat.album.constants.GfyConstants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 1/29/2017.
 *
 * Play GfyCat videos with this activity.
 */

public class VideoActivity extends AppCompatActivity {

    private String videoUrl;
    private Unbinder unbinder;

    @BindView(R.id.gfy_video_view) VideoView gfyVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        unbinder = ButterKnife.bind(this);

        videoUrl = getIntent().getExtras().getString(GfyConstants.GFY_VIDEO_URL_INTENT_EXTRA, null);

        if (videoUrl != null) {
            initVideo();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initVideo() {
        Uri video = Uri.parse(videoUrl);
        gfyVideoView.setVideoURI(video);
        gfyVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                gfyVideoView.start();
            }
        });
    }
}
