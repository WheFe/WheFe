package com.example.chun.whefe.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.chun.whefe.MainActivity;
import com.example.chun.whefe.R;
import com.example.chun.whefe.dbhelper.MyCafeInfoHelper;
import com.example.chun.whefe.dbhelper.MyCustomerCouponHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import static com.example.chun.whefe.R.layout.mycoupon;

/**
 * Created by Chun on 2017-05-27.
 */

public class ShowCouponFragment extends Fragment {

    private static final String tag = "ShowCoupon";

    private String cafe_id;
    private String cafeName;
    private String my_id;
    View view;

    ExpandableListView expandableListView;

    Vector<ParentData> data;
    SQLiteDatabase db;
    MyCustomerCouponHelper helper;
    MyCafeInfoHelper infoHelper;
    SQLiteDatabase infoDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(mycoupon, container, false);

        infoHelper = new MyCafeInfoHelper(getContext());
        infoDB = infoHelper.getWritableDatabase();

        helper = new MyCustomerCouponHelper(getContext());
        db = helper.getWritableDatabase();

        expandableListView = (ExpandableListView)view.findViewById(R.id.myCouponListView);

        SharedPreferences preferences = getContext().getSharedPreferences("INFO_PREFERENCE", Context.MODE_PRIVATE);
        cafeName = preferences.getString("name","NOTFOUND");
        cafe_id = preferences.getString("cafe_id","NOTFOUND");

        SharedPreferences preferences1 = getContext().getSharedPreferences("LOGIN_PREFERENCE", Context.MODE_PRIVATE);
        my_id = preferences1.getString("id","");


        setCouponListData();

        /*ParentAdapter adapter = new ParentAdapter(getContext(),data);
        expandableListView.setAdapter(adapter);*/

       String myCouponUrl = MainActivity.ip + "/whefe/android/customercoupon?customer_id=" + my_id;
        new DownloadCouponTask().execute(myCouponUrl);

        return view;
    }

    public void setCouponListData(){
       /* ParentData parentData1 = new ParentData("그라지에 미래관");
        parentData1.child.add(new Child("300원 할인"));
        parentData1.child.add(new Child("400원 할인"));
        parentData1.child.add(new Child("200원 할인"));

        ParentData parentData2 = new ParentData("그라지에 연구관");
        parentData2.child.add(new Child("500원 할인"));
        parentData2.child.add(new Child("100원 할인"));
        parentData2.child.add(new Child("200원 할인"));

        ParentData parentData3 = new ParentData("팥고당");
        parentData3.child.add(new Child("100원 할인"));
        parentData3.child.add(new Child("200원 할인"));
        parentData3.child.add(new Child("300원 할인"));
        parentData3.child.add(new Child("400원 할인"));

        data.add(parentData1);
        data.add(parentData2);
        data.add(parentData3);*/
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
            ParentData parentData;
         //   cafeCoupons = new ArrayList<CafeCoupon>();
            ArrayList<CustomerCoupon> cafeCoupons = new ArrayList<CustomerCoupon>();
            try {
                JSONArray ja = new JSONArray(result);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject order = ja.getJSONObject(i);
                    String coupon_name = (String) order.get("coupon_name");
                    String cafe_id = (String) order.get("cafe_id");
                    String cafe_name = (String) order.get("cafe_name");
                    String coupon_price = (String) order.get("coupon_price");
                    String coupon_start = (String) order.get("coupon_start");
                    String coupon_end = (String) order.get("coupon_end");

                    db.execSQL("insert into customercoupon(cafe_id, cafe_name, coupon_name, coupon_price, usable, coupon_start, coupon_end) " +
                            "values('"+ cafe_id + "','" + cafe_name + "','"+ coupon_name + "','"+ coupon_price + "','"+ false + "','"+ coupon_start + "','" + coupon_end  + "');");

                    cafeCoupons.add(new CustomerCoupon(cafe_id,cafe_name,coupon_name,coupon_price,false,coupon_start,coupon_end));
                }


                Cursor rs = infoDB.rawQuery("select * from cafeinfo;", null);
                int cafe_count = 0;
                Log.i(tag,"cafe_count" + cafe_count);
                data = new Vector<>();
                ArrayList<String> cafes = new ArrayList<String>();
                while(rs.moveToNext()){
                    String cafe_id = rs.getString(0);
                    String cafe_name = rs.getString(1);

                    cafes.add(cafe_name);
                    cafe_count++;
                }

                for(int i = 0; i < cafe_count;i++) {    // 카페의 개수
                    data.add(new ParentData(cafes.get(i))); // data = parentData의 벡터

                    for(int j = 0; j < cafeCoupons.size();j++) {
                        if (data.get(i).getCafeName().equals(cafeCoupons.get(j).getCafe_name())) {
                            data.get(i).child.add(cafeCoupons.get(j));
                        }
                    }
                }
                ParentAdapter adapter = new ParentAdapter(getContext(),data);
                expandableListView.setAdapter(adapter);
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
    public class CustomerCoupon{
        private String cafe_id;
        private String cafe_name;
        private String coupon_name;
        private String coupon_price;
        private Boolean usable;
        private String coupon_start;
        private String coupon_end;

        public CustomerCoupon(String cafe_id, String cafe_name, String coupon_name, String coupon_price, Boolean usable, String coupon_start, String coupon_end) {
            this.cafe_id = cafe_id;
            this.cafe_name = cafe_name;
            this.coupon_name = coupon_name;
            this.coupon_price = coupon_price;
            this.usable = usable;
            this.coupon_start = coupon_start;
            this.coupon_end = coupon_end;
        }
        public String getCafe_id() {
            return cafe_id;
        }
        public String getCafe_name() {
            return cafe_name;
        }
        public String getCoupon_name() {
            return coupon_name;
        }
        public String getCoupon_price() {
            return coupon_price;
        }
        public Boolean getUsable() {
            return usable;
        }
        public String getCoupon_start() {
            return coupon_start;
        }
        public String getCoupon_end() {
            return coupon_end;
        }
    }

    public class ParentAdapter extends BaseExpandableListAdapter {

        private Context context;
        private Vector<ParentData> data;
        private LayoutInflater inflater = null;

        public ParentAdapter(Context context, Vector<ParentData> data){
            this.data = data;
            this.context = context;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.mycoupon_group, parent, false);
            }

            TextView cafeNameView = (TextView)convertView.findViewById(R.id.mc_cafenameView);
            cafeNameView.setText(data.get(groupPosition).getCafeName());

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView nameView = null;
            TextView priceView = null;
            TextView periodView = null;
            Button button = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.coup_list, parent, false);
                nameView = (TextView) convertView.findViewById(R.id.coup_nameView);
                priceView = (TextView) convertView.findViewById(R.id.coup_priceView);
                periodView = (TextView) convertView.findViewById(R.id.coup_periodView);
                button = (Button) convertView.findViewById(R.id.coup_ReceiveButton);

                button.setVisibility(View.INVISIBLE);
                CustomerCoupon customerCoupon = data.get(groupPosition).child.get(childPosition);

                nameView.setText(customerCoupon.getCoupon_name());
                priceView.setText(customerCoupon.getCoupon_price()+ " 원");
                periodView.setText(customerCoupon.getCoupon_start() + " ~ " + customerCoupon.getCoupon_end());
            }






            return convertView;
        }
        @Override
        public int getGroupCount() {
            return data.size();
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return data.get(groupPosition).child.size();
        }
        @Override
        public Object getGroup(int groupPosition) {
            return data.get(groupPosition);
        }
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return data.get(groupPosition).child.get(childPosition);
        }
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        @Override
        public boolean hasStableIds() {
            return true;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public class ParentData{
        private String cafeName;
        public Vector<CustomerCoupon> child;

        public ParentData(String cafeName) {
            this.cafeName = cafeName;
            child = new Vector<>();
        }

        public String getCafeName() {
            return cafeName;
        }
    }
    public class Child{
        private String couponName;

        public Child(String couponName){
            this.couponName = couponName;
        }
        String getCouponName(){
            return couponName;
        }
    }
}
