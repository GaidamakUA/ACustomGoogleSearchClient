package com.blogspot.androidgaidamak.acustomgooglesearchclient;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.androidgaidamak.acustomgooglesearchclient.adapters.FavouritesAdapter;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite.FavouriteColumns;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.provider.favourite.FavouriteCursor;

public class FavouritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "FavouritesFragment";
    private RecyclerView mRecyclerView;
    private FavouritesAdapter mFavouritesAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().getLoaderManager().initLoader(0, null, this);
        mFavouritesAdapter = new FavouritesAdapter(null, activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_search_results, container, false);

        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.mainListRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFavouritesAdapter);

        return mainView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.v(TAG, "onCreateLoader(" + "id=" + id + ", args=" + args + ")");
        CursorLoader cursorLoader = new CursorLoader(
                getActivity().getBaseContext(),
                FavouriteColumns.CONTENT_URI,
                FavouriteColumns.ALL_COLUMNS,
                null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v(TAG, "onLoadFinished(" + "loader=" + loader + ", data=" + data.getCount() + ")");
        mFavouritesAdapter.changeCursor(new FavouriteCursor(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.v(TAG, "onLoaderReset(" + "loader=" + loader + ")");
        mFavouritesAdapter.changeCursor(null);
    }
}
