package com.gfycat.album.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gfycat.album.R;
import com.gfycat.album.activities.MainActivity;
import com.gfycat.album.models.Gif;
import com.gfycat.album.models.Tag;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Michael Yoon Huh on 1/28/2017.
 */

public class GifRecyclerViewAdapter extends RealmRecyclerViewAdapter<Gif, GifRecyclerViewAdapter.GifViewHolder> {

    private Context context;
    private OrderedRealmCollection<Gif> gifData;

    public GifRecyclerViewAdapter(OrderedRealmCollection<Gif> data, Context context) {
        super(context, data, true);
        this.context = context;
    }

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_gif, parent, false);

        GifViewHolder viewHolder = new GifViewHolder(itemView, new GifViewHolder.OnViewHolderLongClickListener() {
            @Override
            public void onLongClick(View caller, int position) {
                Gif gifObject = getData().get(position);
                ((MainActivity) context).displayActionDialog(gifObject.getIndex(), gifObject.getGyfcatURL());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GifViewHolder holder, int position) {
        Gif gifObject = getData().get(position);

        // GIF TITLE:
        holder.titleText.setText(gifObject.getUserGifName());

        // GIF IMAGE:
        Picasso.with(context)
                .load(gifObject.getThumbnailURL())
                .into(holder.gifImageView);

        // GIF TAGS:
        String tagsString = "";
        int tagCount = 0;
        for (Tag tag : gifObject.getTagsList()) {
            tagsString+= tag.getTag();
            if (tagCount != gifObject.getTagsList().size() - 1) {
                tagsString+= ", ";
            }
            tagCount++;
        }
        holder.tagsText.setText(tagsString);

        // GIF DESCRIPTION:
        holder.descriptionText.setText(gifObject.getTextDescription());
    }

    public static class GifViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        @BindView(R.id.gif) ImageView gifImageView;
        @BindView(R.id.title) TextView titleText;
        @BindView(R.id.tags) TextView tagsText;
        @BindView(R.id.user_description) TextView descriptionText;

        public OnViewHolderLongClickListener viewHolderLongClickListener;

        public GifViewHolder(View view, OnViewHolderLongClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);

            if (listener != null) {
                viewHolderLongClickListener = listener;
                view.getRootView().setOnLongClickListener(this);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int itemPos = getAdapterPosition();
            viewHolderLongClickListener.onLongClick(v, itemPos);
            return true;
        }

        public interface OnViewHolderLongClickListener {
            void onLongClick(View caller, int position);
        }
    }
}