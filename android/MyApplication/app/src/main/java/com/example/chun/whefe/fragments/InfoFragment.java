package com.example.chun.whefe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chun.whefe.NavigationActivity;
import com.example.chun.whefe.R;


/**
 * Created by Chun on 2017-05-08.
 */

public class InfoFragment extends Fragment{

    LinearLayout imageList;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info,container,false);

        TextView infoView = (TextView)view.findViewById(R.id.infoView);
        TextView introduceView = (TextView)view.findViewById(R.id.IntroducevIew);

        Button orderButton = (Button)view.findViewById(R.id.OrderButton);
        Button couponButton = (Button)view.findViewById(R.id.CouponButton);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity activity = (NavigationActivity)getActivity();
                activity.onFragmentChanged(2);
            }
        });
        couponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity activity = (NavigationActivity)getActivity();
                activity.onFragmentChanged(3);
            }
        });

        infoView.setText("주소 : 한성대학교\n영업 시간 : 08 : 00 ~ 22 : 00\n혼잡도 : 13% (13/100)\n" +
                "(손님/좌석 수 *100)\n혼잡도는 카페에 있는 인원을 기준으로 계산한 것이며 다른 고객의 앉는 방식에 따라 차이가 있을 수 있습니다.");
        introduceView.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eget risus ullamcorper, viverra augue in," +
                " imperdiet nulla. Aliquam accumsan varius accumsan. Aenean cursus mi vitae porta accumsan. Sed pretium faucibus accumsan." +
                " Nullam feugiat tortor non pharetra auctor. Proin augue erat, tincidunt sed iaculis quis, porta ac justo." +
                " Nam ornare nunc at condimentum scelerisque. Maecenas finibus urna in sapien sagittis, et accumsan ex blandit." +
                " Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Praesent et leo vitae" +
                " sapien sollicitudin bibendum. ");

        setImageList();

        return view;
    }

    public void setImageList(){
        imageList = (LinearLayout)view.findViewById(R.id.info_imageList);

        ImageView imageView1 = new ImageView(getContext());
        imageView1.setImageResource(R.drawable.grazie1);
        imageView1.setAdjustViewBounds(true);
        imageView1.setVisibility(View.VISIBLE);

        ImageView imageView2 = new ImageView(getContext());
        imageView2.setImageResource(R.drawable.grazie2);
        imageView2.setAdjustViewBounds(true);
        imageView2.setVisibility(View.VISIBLE);

        imageList.addView(imageView1);
        imageList.addView(imageView2);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void onStart(){
        super.onStart();
        Log.i("CGY","Info Activity start");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i("CGY","Info Activity Destroy");
    }

}
