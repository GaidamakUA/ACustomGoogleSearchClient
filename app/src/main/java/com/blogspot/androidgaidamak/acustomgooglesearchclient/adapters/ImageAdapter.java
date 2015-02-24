package com.blogspot.androidgaidamak.acustomgooglesearchclient.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.NetworkImageView;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.ImageDialogFragment;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.Protocol;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.R;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.network.VolleySingleton;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite.FavouriteColumns;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite.FavouriteContentValues;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite.FavouriteCursor;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite.FavouriteSelection;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gaidamak on 22.02.15.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    Activity context;
    ArrayList<Protocol.SearchResponse.SearchItem> images = new ArrayList<>();

    public ImageAdapter(Activity context) {
        this.context = context;
    }

    public void addResults(Protocol.SearchResponse.SearchItem[] searchItems){
        images.addAll(Arrays.asList(searchItems));
        notifyDataSetChanged();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bindImage(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        Activity context;

        RelativeLayout mainBody;
        NetworkImageView previewImageView;
        TextView titleTextView;
        ToggleButton favouritesToggleButton;

        public ImageViewHolder(View itemView, Activity context) {
            super(itemView);
            mainBody = (RelativeLayout) itemView.findViewById(R.id.mainBody);
            previewImageView = (NetworkImageView) itemView.findViewById(R.id.previewImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            favouritesToggleButton = (ToggleButton) itemView.findViewById(R.id.favouritesToggleButton);

            this.context = context;
        }

        public void bindImage(final Protocol.SearchResponse.SearchItem item) {
            previewImageView.setImageUrl(item.image.thumbnailLink,
                    VolleySingleton.getInstance(context).getImageLoader());
            titleTextView.setText(item.title);

            FavouriteSelection selection = new FavouriteSelection();
            selection.displayLink(item.link);
            FavouriteCursor cursor = selection.query(context.getContentResolver());
            favouritesToggleButton.setChecked(cursor.getCount() > 0);

            // In normal conditions OnCheckedChanged is better.
            // But adapter can change state while recycling views.
            favouritesToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToggleButton toggleButton = (ToggleButton) v;
                    if (toggleButton.isChecked()) {
                        FavouriteContentValues contentValues = new FavouriteContentValues();
                        contentValues.putTitle(item.title);
                        contentValues.putDisplayLink(item.link);
                        contentValues.putThumbnailLink(item.image.thumbnailLink);
                        contentValues.insert(context.getContentResolver());
                    } else {
                        FavouriteSelection selection = new FavouriteSelection();
                        selection.displayLink(item.link);
                        selection.delete(context.getContentResolver());
                    }
                }
            });
            mainBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageDialogFragment.getInstance(item.link)
                            .show(context.getFragmentManager(), "TAG");
                }
            });
        }
    }
}
