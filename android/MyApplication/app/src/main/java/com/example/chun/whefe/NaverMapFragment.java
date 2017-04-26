package com.example.chun.whefe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NaverMapFragment extends NMapFragment {

    private static final String NAVER_API_KEY = "fpin69EB6CPM5ATQLpgI";

    // TODO: Rename and change types of parameters


    public NaverMapFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_naver_map, container, false);
    }


}
