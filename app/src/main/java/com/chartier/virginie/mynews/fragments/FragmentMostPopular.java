package com.chartier.virginie.mynews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chartier.virginie.mynews.R;

public class FragmentMostPopular extends Fragment {

    // Create key for our Bundle
    private static final String KEY_POSITION = "position";


    public FragmentMostPopular() { }


    // This method that will create a new instance of FragmentMostPopular, and add data to its bundle.
    public static FragmentMostPopular newInstance(int position) {

        // Create new fragment
        FragmentMostPopular frag = new FragmentMostPopular();

        // Create bundle and add it some data
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        frag.setArguments(args);

        return (frag);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_most_popular, container, false);
    }
}
