package com.example.chun.whefe.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chun.whefe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chun on 2017-05-08.
 */

public class CouponFragment extends Fragment{
    List<Coupon> coupons;
    MyCouponAdapter myCouponAdapter;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coupon,container,false);

        coupons = new ArrayList<Coupon>();

        Coupon c1 = new Coupon();
        c1.setStr1("전체 500원 할인");

        Coupon c2 = new Coupon();
        c2.setStr1("전체 1000원 할인");

        Coupon c3 = new Coupon();
        c3.setStr1("전체 700원 할인");

        coupons.add(c1);
        coupons.add(c2);
        coupons.add(c3);


        myCouponAdapter = new MyCouponAdapter(getContext(),R.layout.coup_list,coupons);

        listView = (ListView)view.findViewById(R.id.coup_ListView);
        listView.setAdapter(myCouponAdapter);

        return view;
    }

    public static class MyViewHolder{
        public TextView textView;
        public Button button;
    }

    public class Coupon{
        public String str1;

        public String getStr1() {
            return str1;
        }
        public void setStr1(String str1) {
            this.str1 = str1;
        }
    }

    public class MyCouponAdapter extends ArrayAdapter<Coupon> {

        //List<Coffee> coffees;
        //Context context;
        MyViewHolder holder;
        String orderInfo;
        LayoutInflater lnf;

        public MyCouponAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Coupon> coupons) {
            super(context, resource, coupons);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return coupons.size();
        }

        @Override
        public Coupon getItem(int position) {
            return coupons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {



            if(convertView==null){
                //  LayoutInflater inflater = getLayoutInflater();
                convertView = lnf.inflate(R.layout.coup_list,parent,false);

                holder = new MyViewHolder();
                holder.textView = (TextView)convertView.findViewById(R.id.coup_TextView);
                holder.button = (Button)convertView.findViewById(R.id.coup_ReceiveButton);

                holder.textView.setText(getItem(position).getStr1());

            }else{
                holder = (MyViewHolder) convertView.getTag();
            }

            Coupon coupon = new Coupon();
            coupon.setStr1(coupons.get(position).getStr1());


            return convertView;
        }

    }

}
