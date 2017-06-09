package com.example.chun.whefe.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.chun.whefe.MainActivity;
import com.example.chun.whefe.R;
import com.example.chun.whefe.dbhelper.MyCafeCouponHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.example.chun.whefe.R.layout.coupon;

/**
 * Created by Chun on 2017-05-08.
 */

public class CouponFragment extends Fragment {
    ArrayList<String> cn = new ArrayList<>();
    MyCouponAdapter myCouponAdapter;
    ListView listView;
    View view;

    private String cafe_id;
    private String cafeName;
    private String my_id;

    ArrayList<CafeCoupon> cafeCoupons;
    SQLiteDatabase db;
    MyCafeCouponHelper helper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(coupon, container, false);

        helper = new MyCafeCouponHelper(getContext());
        db = helper.getWritableDatabase();

        SharedPreferences preferences1 = getContext().getSharedPreferences("LOGIN_PREFERENCE", Context.MODE_PRIVATE);
        my_id = preferences1.getString("id","");

        SharedPreferences preferences = getContext().getSharedPreferences("INFO_PREFERENCE",Context.MODE_PRIVATE);
        cafeName = preferences.getString("name","NOTFOUND");
        cafe_id = preferences.getString("cafe_id","NOTFOUND");

        String Url = MainActivity.ip + "/whefe/android/cafecoupon?cafe_id=" + cafe_id;
        new DownloadCouponTask().execute(Url);

        listView = (ListView) view.findViewById(R.id.coup_ListView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }




    private class DownloadCouponTask extends AsyncTask<String, Void, String> {                     // 카테고리 출력 Connection

        @Override
        protected String doInBackground(String... urls) {
            try {
                return (String) downloadUrl((String) urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "다운로드 실패";
            }
        }

        protected void onPostExecute(String result) {
            System.out.println("onPostExecute!");
            Log.e("Json", result);
            Log.i("CGY","DownloadCafeCoupon");
            cafeCoupons = new ArrayList<CafeCoupon>();
            try {
                JSONArray ja = new JSONArray(result);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject order = ja.getJSONObject(i);
                    int coupon_num = (int)order.get("coupon_num");
                    String coupon_name = (String) order.get("coupon_name");
                    String cafe_id = (String) order.get("cafe_id");
                    String coupon_price = (String) order.get("coupon_price");
                    String coupon_start = (String) order.get("coupon_start");
                    String coupon_end = (String) order.get("coupon_end");
                    Boolean use_ox = (Boolean) order.get("use_ox");

                    db.execSQL("insert into cafecoupon(coupon_num, coupon_name, cafe_id, coupon_price, coupon_start, coupon_end, use_ox) " +
                            "values('"+ coupon_num + "','" + coupon_name + "','"+ cafe_id + "','"+ coupon_price + "','"+ coupon_start + "','"+ coupon_end + "','" + use_ox  + "');");

                    cafeCoupons.add(new CafeCoupon(coupon_num,coupon_name,cafe_id,coupon_price,coupon_start,coupon_end,use_ox));
                }
                Log.i("CafeCoupon",cafeCoupons.toString());

                myCouponAdapter = new MyCouponAdapter(getContext(), R.layout.coup_list, cafeCoupons);

                listView.setAdapter(myCouponAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        private String downloadUrl(String myurl) throws IOException {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();
                System.out.println("status code : " + conn.getResponseCode() + "!!!!!!!!!!!!!!");
                Log.e("status code", conn.getResponseMessage());

                BufferedReader bufreader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                }
                return page;
            } finally {
            }
        }
    }
    public class CafeCoupon{
        private int coupon_num;
        private String coupon_name;
        private String cafe_id;
        private String coupon_price;
        private String coupon_start;
        private String coupon_end;
        private Boolean use_ox;

        @Override
        public String toString() {
            return "CafeCoupon{" +
                    "coupon_num=" + coupon_num +
                    ", coupon_name='" + coupon_name + '\'' +
                    ", cafe_id='" + cafe_id + '\'' +
                    ", coupon_price='" + coupon_price + '\'' +
                    ", coupon_start='" + coupon_start + '\'' +
                    ", coupon_end='" + coupon_end + '\'' +
                    ", use_ox=" + use_ox +
                    '}';
        }

        public CafeCoupon(int coupon_num, String coupon_name, String cafe_id, String coupon_price, String coupon_start, String coupon_end, Boolean use_ox) {
            this.coupon_num = coupon_num;
            this.coupon_name = coupon_name;
            this.cafe_id = cafe_id;
            this.coupon_price = coupon_price;
            this.coupon_start = coupon_start;
            this.coupon_end = coupon_end;
            this.use_ox = use_ox;
        }
        public int getCoupon_num() {
            return coupon_num;
        }
        public String getCoupon_name() {
            return coupon_name;
        }
        public String getCafe_id() {
            return cafe_id;
        }
        public String getCoupon_price() {
            return coupon_price;
        }
        public String getCoupon_start() {
            return coupon_start;
        }
        public String getCoupon_end() {
            return coupon_end;
        }
        public Boolean getUse_ox() {
            return use_ox;
        }
    }
    public class MyCouponAdapter extends ArrayAdapter<CafeCoupon> {
        String orderInfo;
        LayoutInflater lnf;

        public MyCouponAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<CafeCoupon> cafeCoupons) {
            super(context, resource, cafeCoupons);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return cafeCoupons.size();
        }

        @Override
        public CafeCoupon getItem(int position) {
            return cafeCoupons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            TextView nameView = null;
            TextView priceView = null;
            TextView periodView = null;
            Button button = null;

            if (convertView == null) {
                convertView = lnf.inflate(R.layout.coup_list, parent, false);

                nameView = (TextView) convertView.findViewById(R.id.coup_nameView);
                priceView = (TextView) convertView.findViewById(R.id.coup_priceView);
                periodView = (TextView) convertView.findViewById(R.id.coup_periodView);
                button = (Button) convertView.findViewById(R.id.coup_ReceiveButton);
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Log.i("coupon","쿠폰 받기 버튼 눌림");
                        // customer_id, cafe_id, coupon_name, coupon_num, usable
                        JSONArray jsonArray = new JSONArray();
                        try{

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("customer_id", my_id);
                            jsonObject.put("cafe_id", cafe_id);
                            jsonObject.put("cafe_name", cafeName);
                            jsonObject.put("coupon_name", getItem(position).getCoupon_name());
                            jsonObject.put("coupon_price", getItem(position).getCoupon_price());
                            jsonObject.put("coupon_num", getItem(position).getCoupon_num());
                            jsonObject.put("coupon_start", getItem(position).getCoupon_start());
                            jsonObject.put("coupon_end", getItem(position).getCoupon_end());

                            jsonArray.put(jsonObject);

                            String obj = jsonArray.toString();
                            String obj2 = URLEncoder.encode(obj,"UTF-8");
                            String paymentURL = MainActivity.ip + "/whefe/android/downloadcoupon?coupon=" + obj2;
                            new SendTask().execute(paymentURL);
                            Toast.makeText(getContext(), "쿠폰을 수령하였습니다.", Toast.LENGTH_SHORT).show();
                        }catch(JSONException e){
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
            nameView.setText(getItem(position).getCoupon_name());
            priceView.setText(getItem(position).getCoupon_price()+ " 원");
            periodView.setText(getItem(position).getCoupon_start() + " ~ " + getItem(position).getCoupon_end());

            return convertView;
        }
    }
    private class SendTask extends AsyncTask<String, Void, String> {                     // 카테고리 출력 Connection

        @Override
        protected String doInBackground(String... urls) {
            try {
                return (String) downloadUrl((String) urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "다운로드 실패";
            }
        }

        protected void onPostExecute(String result) {
            System.out.println("onPostExecute!");
        }

        private String downloadUrl(String myurl) throws IOException {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();
                System.out.println("status code : " + conn.getResponseCode() + "!!!!!!!!!!!!!!");
                Log.e("status code", conn.getResponseMessage());

                BufferedReader bufreader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                }
                return page;
            } finally {
            }
        }
    }
}



