package com.gfycat.album.models;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gfycat.album.R;
import com.tokenautocomplete.TokenCompleteTextView;

/**
 * Created by Steve on 1/28/2017.
 */

public class GifsCompletionView extends TokenCompleteTextView<String> {

    public GifsCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(final String string) {

        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        FrameLayout view = (FrameLayout) l.inflate(R.layout.chip, (ViewGroup) getParent(), false);
        TextView chipText = (TextView) view.findViewById(R.id.chipText);
        chipText.setText(string);

        return view;
    }

    @Override
    protected String defaultObject(String completionText) {
        return completionText;
    }

    @Override
    public void addObject(String object) {
        super.addObject(object);
    }


}

