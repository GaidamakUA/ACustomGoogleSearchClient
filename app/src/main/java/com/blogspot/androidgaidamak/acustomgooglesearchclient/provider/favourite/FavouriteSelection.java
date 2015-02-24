package com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.base.AbstractSelection;

/**
 * Selection for the {@code favourite} table.
 */
public class FavouriteSelection extends AbstractSelection<FavouriteSelection> {
    @Override
    protected Uri baseUri() {
        return FavouriteColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code FavouriteCursor} object, which is positioned before the first entry, or null.
     */
    public FavouriteCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new FavouriteCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null)}.
     */
    public FavouriteCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null)}.
     */
    public FavouriteCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public FavouriteSelection id(long... value) {
        addEquals("favourite." + FavouriteColumns._ID, toObjectArray(value));
        return this;
    }

    public FavouriteSelection thumbnailLink(String... value) {
        addEquals(FavouriteColumns.THUMBNAIL_LINK, value);
        return this;
    }

    public FavouriteSelection thumbnailLinkNot(String... value) {
        addNotEquals(FavouriteColumns.THUMBNAIL_LINK, value);
        return this;
    }

    public FavouriteSelection thumbnailLinkLike(String... value) {
        addLike(FavouriteColumns.THUMBNAIL_LINK, value);
        return this;
    }

    public FavouriteSelection thumbnailLinkContains(String... value) {
        addContains(FavouriteColumns.THUMBNAIL_LINK, value);
        return this;
    }

    public FavouriteSelection thumbnailLinkStartsWith(String... value) {
        addStartsWith(FavouriteColumns.THUMBNAIL_LINK, value);
        return this;
    }

    public FavouriteSelection thumbnailLinkEndsWith(String... value) {
        addEndsWith(FavouriteColumns.THUMBNAIL_LINK, value);
        return this;
    }

    public FavouriteSelection displayLink(String... value) {
        addEquals(FavouriteColumns.DISPLAY_LINK, value);
        return this;
    }

    public FavouriteSelection displayLinkNot(String... value) {
        addNotEquals(FavouriteColumns.DISPLAY_LINK, value);
        return this;
    }

    public FavouriteSelection displayLinkLike(String... value) {
        addLike(FavouriteColumns.DISPLAY_LINK, value);
        return this;
    }

    public FavouriteSelection displayLinkContains(String... value) {
        addContains(FavouriteColumns.DISPLAY_LINK, value);
        return this;
    }

    public FavouriteSelection displayLinkStartsWith(String... value) {
        addStartsWith(FavouriteColumns.DISPLAY_LINK, value);
        return this;
    }

    public FavouriteSelection displayLinkEndsWith(String... value) {
        addEndsWith(FavouriteColumns.DISPLAY_LINK, value);
        return this;
    }

    public FavouriteSelection title(String... value) {
        addEquals(FavouriteColumns.TITLE, value);
        return this;
    }

    public FavouriteSelection titleNot(String... value) {
        addNotEquals(FavouriteColumns.TITLE, value);
        return this;
    }

    public FavouriteSelection titleLike(String... value) {
        addLike(FavouriteColumns.TITLE, value);
        return this;
    }

    public FavouriteSelection titleContains(String... value) {
        addContains(FavouriteColumns.TITLE, value);
        return this;
    }

    public FavouriteSelection titleStartsWith(String... value) {
        addStartsWith(FavouriteColumns.TITLE, value);
        return this;
    }

    public FavouriteSelection titleEndsWith(String... value) {
        addEndsWith(FavouriteColumns.TITLE, value);
        return this;
    }
}
