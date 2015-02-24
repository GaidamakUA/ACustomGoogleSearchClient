package com.blogspot.androidgaidamak.acustomgooglesearchclient.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.blogspot.androidgaidamak.acustomgooglesearchclient.BuildConfig;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite.FavouriteColumns;

public class FavouritesSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = FavouritesSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "favourites.db";
    private static final int DATABASE_VERSION = 1;
    private static FavouritesSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final FavouritesSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_FAVOURITE = "CREATE TABLE IF NOT EXISTS "
            + FavouriteColumns.TABLE_NAME + " ( "
            + FavouriteColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavouriteColumns.THUMBNAIL_LINK + " TEXT NOT NULL, "
            + FavouriteColumns.DISPLAY_LINK + " TEXT NOT NULL, "
            + FavouriteColumns.TITLE + " TEXT NOT NULL "
            + ", CONSTRAINT unique_name UNIQUE (thumbnail_link, display_link) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static FavouritesSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static FavouritesSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static FavouritesSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new FavouritesSQLiteOpenHelper(context);
    }

    private FavouritesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new FavouritesSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static FavouritesSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new FavouritesSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private FavouritesSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new FavouritesSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_FAVOURITE);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
