package com.example.chun.whefe.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.chun.whefe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import static com.example.chun.whefe.R.layout.mycoupon;

/**
 * Created by Chun on 2017-05-27.
 */

public class ShowCouponFragment extends Fragment {

    private String cafe_id;
    private String cafeName;
    private String my_id;
    View view;

    ExpandableListView expandableListView;

    Vector<ParentData> data = new Vector<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(mycoupon, container, false);

        expandableListView = (ExpandableListView)view.findViewById(R.id.myCouponListView);

        SharedPreferences preferences = getContext().getSharedPreferences("INFO_PREFERENCE", Context.MODE_PRIVATE);
        cafeName = preferences.getString("name","NOTFOUND");
        cafe_id = preferences.getString("cafe_id","NOTFOUND");

        SharedPreferences preferences1 = getContext().getSharedPreferences("LOGIN_PREFERENCE", Context.MODE_PRIVATE);
        my_id = preferences1.getString("id","");


        setCouponListData();

        ParentAdapter adapter = new ParentAdapter(getContext(),data);
        expandableListView.setAdapter(adapter);

      /*  String myCouponUrl = MainActivity.ip + "/whefe/android/coupon?customer_id=" + my_id;
        new DownloadCategoryTask().execute(myCouponUrl);*/

        return view;
    }

    public void setCouponListData(){
        ParentData parentData1 = new ParentData("그라지에 미래관");
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
        data.add(parentData3);
    }

    public void setCouponListServer(){

    }

    private class DownloadCategoryTask extends AsyncTask<String, Void, String> {                     // 카테고리 출력 Connection

        String[] cafeid = new String[7];

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
            Log.i("CGY","DownloadCategory");

            try {
                JSONArray ja = new JSONArray(result);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject order = ja.getJSONObject(i);
                    String couponName = (String) order.get("coupon_name");
                    String cafe_id = (String)order.get("cafe_id");
                    String couponNum = (String)order.get("coupon_num");

                    /*int check = -1;
                    for(int j = 0; j<cafeid.length;j++){
                        if(cafeid[j].equals(cafe_id)){
                            check = j;
                            break;
                        }
                    }
                    if(check==-1){
                        TextView cafeNameTextView = new TextView(getContext());
                        cafeNameTextView.setText(cafe_id);
                        cafeNameTextView.setTextColor(Color.BLACK);
                        cafeNameTextView.setTextSize(20);
                        linearLayout.addView(cafeNameTextView);
                    }*/
                    setCouponListServer();
                }
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

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.mycoupon_child, parent, false);
            }

            TextView couponNameView = (TextView)convertView.findViewById(R.id.mc_couponNameView);
            couponNameView.setText(data.get(groupPosition).child.get(childPosition).getCouponName());

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
        public Vector<Child> child;

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
