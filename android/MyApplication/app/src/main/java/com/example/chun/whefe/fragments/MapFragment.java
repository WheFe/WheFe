package com.example.chun.whefe.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chun.whefe.NaverMapFragment;
import com.example.chun.whefe.NonSwipeViewPager;
import com.example.chun.whefe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chun on 2017-05-08.
 */

public class MapFragment extends Fragment{

    private static final int REQ_PERMISSION = 2000;
    private List<Fragment> mapFragments;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.map,container,false);


//Permission Check
        int permissionChk = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        // if Permission DENIED
        if(permissionChk == PackageManager.PERMISSION_DENIED){

            // Request Permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQ_PERMISSION);
        }
        else{
            // if Permission GRANTED
            initViewPager();
        }

        return view;

    }
    private void initViewPager() {
        mapFragments = new ArrayList<>();
        mapFragments.add(new NaverMapFragment());

        MapFragmentsPagerAdapter pagerAdapter = new MapFragmentsPagerAdapter(this.getFragmentManager());

        NonSwipeViewPager viewPager = (NonSwipeViewPager) view.findViewById(R.id.vp_maps);

        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION & grantResults.length > 0) {
            initViewPager();
        }
    }

    class MapFragmentsPagerAdapter extends FragmentPagerAdapter {
        public MapFragmentsPagerAdapter(FragmentManager fm) { super(fm);}

        @Override
        public Fragment getItem(int position) {
            return mapFragments.get(position);
        }

        @Override
        public int getCount() {
            return mapFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0: return "Naver";
                default: return "";
            }
        }
    }
}
