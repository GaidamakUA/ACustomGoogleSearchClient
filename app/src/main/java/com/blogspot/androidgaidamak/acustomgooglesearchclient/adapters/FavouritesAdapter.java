package com.blogspot.androidgaidamak.acustomgooglesearchclient.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.NetworkImageView;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.ImageDialogFragment;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.R;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.network.VolleySingleton;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite.FavouriteCursor;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite.FavouriteSelection;

public class FavouritesAdapter extends CursorRecyclerAdapter<FavouritesAdapter.ImageViewHolder, FavouriteCursor> {
    Activity context;

    public FavouritesAdapter(FavouriteCursor cursor, Activity context) {
        super(cursor);
        this.context = context;
    }

    @Override
    public void onBindViewHolderCursor(ImageViewHolder holder, FavouriteCursor cursor) {
        holder.bindImage(cursor);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(v, context);
    }

    @Override
    public Filter getFilter() {
        return null;
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

        public void bindImage(final FavouriteCursor cursor) {
            previewImageView.setImageUrl(cursor.getThumbnailLink(),
                    VolleySingleton.getInstance(context).getImageLoader());
            titleTextView.setText(cursor.getTitle());
            // TODO implement favourites
            favouritesToggleButton.setChecked(true);
            final String imageUrl = cursor.getDisplayLink();

            favouritesToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToggleButton toggleButton = (ToggleButton) v;
                    FavouriteSelection selection = new FavouriteSelection();
                    selection.displayLink(imageUrl);
                    selection.delete(context.getContentResolver());
                }
            });
            mainBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("ImageViewHolder", "cursor.getDisplayLink()->" + cursor.getDisplayLink());
                    ImageDialogFragment.getInstance(imageUrl)
                            .show(context.getFragmentManager(), "TAG");
                }
            });
        }
    }
}
