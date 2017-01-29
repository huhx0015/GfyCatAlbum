package com.gfycat.album.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.gfycat.album.R;
import com.gfycat.album.activities.HandleShareActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 1/29/2017.
 */

public class SaveGfycatDialog extends DialogFragment {

    private Context context;
    private Unbinder unbinder;

    @BindView(R.id.gfycat_name_field) EditText nameField;
    @BindView(R.id.link_field) EditText linkField;
    @BindView(R.id.hashtag_field) EditText hashtagField;
    @BindView(R.id.description_field) EditText descriptionField;

    @OnClick(R.id.dialog_save_button)
    public void saveButton() {
        String name = nameField.getText().toString();
        String link = linkField.getText().toString();
        String hashtags = hashtagField.getText().toString();
        String description = descriptionField.getText().toString();
        ((HandleShareActivity) context).saveGfycat(link, name, hashtags, description);
        getDialog().dismiss();
    }

    @OnClick(R.id.dialog_cancel_button)
    public void cancelButton() {
        getDialog().dismiss();
        ((HandleShareActivity) context).finish();
    }

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public SaveGfycatDialog(Context context) {
        this.context = context;
    }

    public static SaveGfycatDialog newInstance(Context context) {
        return new SaveGfycatDialog(context);
    }

    /** FRAGMENT LIFECYCLE METHODS _____________________________________________________________ **/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View save_gfycat_dialog_view = inflater.inflate(R.layout.dialog_save, container, false);
        unbinder = ButterKnife.bind(this, save_gfycat_dialog_view);
        getDialog().setCancelable(false);
        return save_gfycat_dialog_view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
