package com.example.chun.whefe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chun.whefe.R;

/**
 * Created by Chun on 2017-05-22.
 */

public class HistoryFragment extends Fragment {
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map,container,false);





        return view;
    }
}
