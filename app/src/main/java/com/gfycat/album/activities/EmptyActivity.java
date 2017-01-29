package com.gfycat.album.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gfycat.album.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 1/29/2017.
 */

public class EmptyActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.search_gif_link) TextView searchGifLink;

    @OnClick(R.id.search_gif_link)
    public void searchGifs() {
        // TODO: Handle gif search action here.
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
