package com.blogspot.androidgaidamak.acustomgooglesearchclient;

import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Dialog;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.blogspot.androidgaidamak.acustomgooglesearchclient.network.VolleySingleton;
import com.blogspot.androidgaidamak.acustomgooglesearchclient.views.ZoomableNetworkImageView;

/**
 * Created by gaidamak on 24.02.15.
 */
public class ImageDialogFragment extends DialogFragment {
    private static final String TAG = "ImageDialogFragment";
    public static final String URL = "url";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_show_image, null);
        String url = getArguments().getString(URL);
        Log.v(TAG, "URL=" + url);
        ZoomableNetworkImageView imageView =
                (ZoomableNetworkImageView) view.findViewById(R.id.imageView);
        imageView.setImageUrl(url, VolleySingleton.getInstance(getActivity()).getImageLoader());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setNegativeButton("Close", null);
        return builder.create();
    }

    public static ImageDialogFragment getInstance(String url) {
        Log.v(TAG, "getInstance(" + "url=" + url + ")");
        ImageDialogFragment fragment = new ImageDialogFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }
}
