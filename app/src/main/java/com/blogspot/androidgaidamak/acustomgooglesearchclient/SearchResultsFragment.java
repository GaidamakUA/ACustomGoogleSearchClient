package com.blogspot.androidgaidamak.acustomgooglesearchclient;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.adapters.ImageAdapter;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.network.GsonRequest;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.network.VolleySingleton;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchResultsFragment extends Fragment {
    private static final String TAG = "SearchResultsFragment";

    public static final String COMMON_QUERY = "?searchType=image&" +
            "key=AIzaSyCs4XKzAs_0mDXlK5zeZBeGJjMduCnb79k&" +
            "cx=004176250037548948735:e4xbt6t-fyy&" +
            "q=%1$s";
    public static final String COMMON_LOAD_MORE_QUERY = "?searchType=image&" +
            "key=AIzaSyCs4XKzAs_0mDXlK5zeZBeGJjMduCnb79k&" +
            "cx=004176250037548948735:e4xbt6t-fyy&" +
            "q=%1$s&" +
            "start=%2$d";

    private RecyclerView mRecyclerView;

    private int startIndex = -1;
    private String searchTerms;
    private ImageAdapter mImageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_search_results, container, false);

        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.mainListRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int pastVisiblesItems = manager.findFirstVisibleItemPosition();

                if (startIndex != -1) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        String formedQuery = String.format(COMMON_LOAD_MORE_QUERY, searchTerms, startIndex);
                        Log.v(TAG, "formedQuery=" + formedQuery);
                        GsonRequest<Protocol.SearchResponse> request = new GsonRequest<>(Request.Method.GET,
                                MainActivity.BASE_URL + formedQuery, Protocol.SearchResponse.class, null, null,
                                new MyResponseListener(mImageAdapter, SearchResultsFragment.this),
                                mErrorListener);
                        VolleySingleton.getInstance(getActivity()).getRequestQueue().add(request);
                    }
                }
            }
        });

        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume(" + ")");
        mImageAdapter = new ImageAdapter(getActivity());
        mRecyclerView.setAdapter(mImageAdapter);
    }

    public void performQuery(String query) throws UnsupportedEncodingException {

        String formedQuery = String.format(COMMON_QUERY, URLEncoder.encode(query, "UTF-8"));
        GsonRequest<Protocol.SearchResponse> request = new GsonRequest<>(Request.Method.GET,
                MainActivity.BASE_URL + formedQuery, Protocol.SearchResponse.class, null, null,
                new MyResponseListener(mImageAdapter, SearchResultsFragment.this), mErrorListener);
        Log.v(TAG, "getActivity()->" + getActivity());
        VolleySingleton.getInstance(getActivity()).getRequestQueue().add(request);
    }

    static class MyResponseListener implements Response.Listener<Protocol.SearchResponse> {
        private ImageAdapter mImageAdapter;
        private SearchResultsFragment fragment;

        MyResponseListener(ImageAdapter mImageAdapter, SearchResultsFragment fragment) {
            this.mImageAdapter = mImageAdapter;
            this.fragment = fragment;
        }
        @Override
        public void onResponse(Protocol.SearchResponse response) {
            Log.v(TAG, "SearchResultsFragment=" + fragment);
            mImageAdapter.addResults(response.items);
            if (response.queries.nextPage == null) {
                fragment.startIndex = -1;
            } else {
                fragment.startIndex = response.queries.nextPage[0].startIndex;
                fragment.searchTerms = response.queries.nextPage[0].searchTerms;
                try {
                    fragment.searchTerms = URLEncoder.encode(fragment.searchTerms, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    ;
    Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                Log.v(TAG, new String(error.networkResponse.data, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    };
}
