package com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code favourite} table.
 */
public class FavouriteCursor extends AbstractCursor implements FavouriteModel {
    public FavouriteCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(FavouriteColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Link to thumbnail image (generated by google).
     * Cannot be {@code null}.
     */
    @NonNull
    public String getThumbnailLink() {
        String res = getStringOrNull(FavouriteColumns.THUMBNAIL_LINK);
        if (res == null)
            throw new NullPointerException("The value of 'thumbnail_link' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Link to real image (the big one).
     * Cannot be {@code null}.
     */
    @NonNull
    public String getDisplayLink() {
        String res = getStringOrNull(FavouriteColumns.DISPLAY_LINK);
        if (res == null)
            throw new NullPointerException("The value of 'display_link' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTitle() {
        String res = getStringOrNull(FavouriteColumns.TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'title' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
