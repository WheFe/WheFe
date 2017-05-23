package com.example.chun.whefe.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chun.whefe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.util.Log.e;
import static com.example.chun.whefe.R.layout.coupon;

/**
 * Created by Chun on 2017-05-08.
 */

public class CouponFragment extends Fragment {
    List<Coupon> coupons;
    ArrayList<String> cn = new ArrayList<>();
    Coupon couponArray[] = new Coupon[100];
    MyCouponAdapter myCouponAdapter;
    ListView listView;
    Coupon c;
    View view;
    String coupon_name;
    DBTask dbTask;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(coupon, container, false);
        dbTask = new DBTask();

        coupons = new ArrayList<Coupon>();

        /*try {
            //coupon_name = dbTask.execute("http://223.194.155.51/cafecouponlist.php").get();
            dbTask.execute("http://223.194.155.51/cafecouponlist.php");
            String[] couponArray = coupon_name.split(",");
            for(int i=0;i<couponArray.length;i++) {
                Log.e("coupon",couponArray[i]);
                c = new Coupon();
                c.setStr1(couponArray[i]);

                coupons.add(c);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        try {
            String json = dbTask.execute("http://223.194.155.51/jsonTest.php").get();
            JSONArray ja = new JSONArray(json);
            for(int i=0;i<ja.length();i++) {
                JSONObject jsonObject = ja.getJSONObject(i);
                String coupon_num = jsonObject.get("coupon_num").toString();
                String coupon_name = jsonObject.get("coupon_name").toString();
                String cafe_id = jsonObject.get("cafe_id").toString();

                couponArray[i] = new Coupon();
                couponArray[i].setStr1(coupon_name);
                couponArray[i].setCoupon_num(Integer.parseInt(coupon_num));
                couponArray[i].setCafe_id(cafe_id);
                coupons.add(couponArray[i]);
                /*Coupon cou = new Coupon();
                cou.setStr1(coupon_name);
                cou.setCoupon_num(Integer.parseInt(coupon_num));
                cou.setCafe_id(cafe_id);

                coupons.add(cou);*/
            }

        } catch (InterruptedException e) {
            Toast.makeText(getContext(),"InterruptedException",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ExecutionException e) {
            Toast.makeText(getContext(),"ExecutionException",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (JSONException e) {
            Toast.makeText(getContext(),"JSONException",Toast.LENGTH_SHORT).show();
            couponArray[0] = new Coupon();
            couponArray[0].setStr1("500원 할인");
            couponArray[0].setCoupon_num(0);
            couponArray[0].setCafe_id("0");
            coupons.add(couponArray[0]);

            couponArray[1] = new Coupon();
            couponArray[1].setStr1("700원 할인");
            couponArray[1].setCoupon_num(0);
            couponArray[1].setCafe_id("0");
            coupons.add(couponArray[1]);

            couponArray[2] = new Coupon();
            couponArray[2].setStr1("1000원 할인");
            couponArray[2].setCoupon_num(0);
            couponArray[2].setCafe_id("0");
            coupons.add(couponArray[2]);

            e.printStackTrace();
        }

        /*Log.e("JSON 쿠폰",couponArray[0].getStr1());
        Log.e("JSON 쿠폰",couponArray[1].getStr1());
        Log.e("JSON 쿠폰",couponArray[2].getStr1());*/

        myCouponAdapter = new MyCouponAdapter(getContext(), R.layout.coup_list, coupons);
        listView = (ListView) view.findViewById(R.id.coup_ListView);
        listView.setAdapter(myCouponAdapter);

        //Log.e("coupon",couponArray[0].getCafe_id());
        //dbTask.cancel(true);
       /* couponArray[0] = new Coupon();
        couponArray[1] = new Coupon();
        couponArray[2] = new Coupon();*/


       /* myCouponAdapter = new MyCouponAdapter(getContext(), R.layout.coup_list, coupons);

        listView = (ListView) view.findViewById(R.id.coup_ListView);
        listView.setAdapter(myCouponAdapter);
*/
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static class MyViewHolder {
        public TextView textView;
        public Button button;
    }

    public class Coupon {
        private String str1;
        private int coupon_num;
        private String cafe_id;

        public String getStr1() {
            return str1;
        }
        public int getCoupon_num(){
            return coupon_num;
        }

        public String getCafe_id(){
            return cafe_id;
        }

        public void setStr1(String str1) {
            this.str1 = str1;
        }
        public void setCoupon_num(int coupon_num){
            this.coupon_num = coupon_num;
        }
        public void setCafe_id(String cafe_id){
            this.cafe_id = cafe_id;
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


            if (convertView == null) {
                //  LayoutInflater inflater = getLayoutInflater();
                convertView = lnf.inflate(R.layout.coup_list, parent, false);

                holder = new MyViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.coup_TextView);
                holder.button = (Button) convertView.findViewById(R.id.coup_ReceiveButton);

                holder.button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.i("coupon","쿠폰 받기 버튼 눌림");
                    }
                });


                holder.textView.setText(getItem(position).getStr1());

            } else {
                holder = (MyViewHolder) convertView.getTag();
            }

            Coupon coupon = new Coupon();
            coupon.setStr1(coupons.get(position).getStr1());


            return convertView;
        }

    }

    private class DBTask extends AsyncTask<String, Void, String> {                    // 메뉴 출력 Connection

        @Override
        protected String doInBackground(String... urls) {
            try{
                return (String)downloadUrl((String)urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "다운로드 실패";
            }
        }

        @Override
        /*protected void onPostExecute(String result) {
            System.out.println("onPostExecute!");
            Log.e("Json", result);

            try {
                jsonObject = new JSONObject(result);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(jsonObject.toString());
            try {
                System.out.println(jsonObject.get("category_name"));
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }*/
        protected void onPostExecute(String result) {
            System.out.println("onPostExecute!");
            e("result", result+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            /*try {
                JSONArray jsonArray = new JSONArray(result);

                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String coupon_num = jsonObject.get("coupon_num").toString();
                    String coupon_name = jsonObject.get("coupon_name").toString();
                    String cafe_id = jsonObject.get("cafe_id").toString();
                    couponArray[0].setStr1("CA");
                    *//*couponArray[i] = new Coupon();
                    couponArray[i].setStr1(coupon_name);
                    couponArray[i].setCoupon_num(Integer.parseInt(coupon_num));
                    couponArray[i].setCafe_id(cafe_id);
                    coupons.add(couponArray[i]);*//*
*//*
                    Coupon cou = new Coupon();
                    cou.setStr1(coupon_name);
                    cou.setCoupon_num(Integer.parseInt(coupon_num));
                    cou.setCafe_id(cafe_id);

                    coupons.add(cou);*//*

                }
                myCouponAdapter = new MyCouponAdapter(getContext(), R.layout.coup_list, coupons);

                listView = (ListView) view.findViewById(R.id.coup_ListView);
                listView.setAdapter(myCouponAdapter);
                dbTask.cancel(true);
                Log.e("deTask","deTask");
            } catch (JSONException e) {
                e.printStackTrace();
            }*/


           /* Coupon coupon = new Coupon();
            coupon.setStr1(temp);
            coupons.add(coupon);*/


        }

        private String downloadUrl(String myurl) throws IOException {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();

                BufferedReader bufreader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                String line =null;
                String page = "";
                while((line = bufreader.readLine()) != null) {
                    page +=line;
                }
                return page;
            } finally {

            }
        }
    }
}



