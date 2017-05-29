package com.example.chun.whefe.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chun.whefe.CafeInfo;
import com.example.chun.whefe.MainActivity;
import com.example.chun.whefe.NavigationActivity;
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

/**
 * Created by Chun on 2017-05-22.
 */

public class CafeListFragment extends Fragment {

    View view;
   // String ip = "http://223.194.157.35:8080";

    ListView listView;
    CafeListAdapter cafeListAdapter;
    ArrayList<CafeInfo> cafeList = new ArrayList<CafeInfo>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cafe_list,container,false);

        String categoryUrl = MainActivity.ip + "/whefe/android/cafeinfo";
        new DownloadCategoryTask().execute(categoryUrl);

        listView = (ListView)view.findViewById(R.id.cl_listView);


        Button backButton = (Button)view.findViewById(R.id.cl_backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private class DownloadCategoryTask extends AsyncTask<String, Void, String> {                     // 카테고리 출력 Connection

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
            for(int i= cafeList.size()-1;i>=0;i--) {
                cafeList.remove(i);
            }
            try {


                JSONArray ja = new JSONArray(result);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject order = ja.getJSONObject(i);
                    String cafe_id = (String)order.get("cafe_id");
                    String cafeName = (String) order.get("cafe_name");
                    String cafeAddress = (String)order.get("cafe_address");
                    String cafePhone = (String)order.get("cafe_tel");
                    String cafeOpen = (String)order.get("cafe_open");
                    String cafeClose = (String)order.get("cafe_end");
                    String cafeMax = (String)order.get("cafe_max");
                    String cafePerson = (String)order.get("cafe_curr");
                    String cafe_intro = (String)order.get("cafe_intro");
                    String cafe_image = (String)order.get("imageFilename");

                    CafeInfo cafeInfo = new CafeInfo(cafe_id,cafeName,cafeAddress,cafePhone,cafeOpen,cafeClose,cafePerson,cafeMax,cafe_intro,cafe_image);
                    cafeList.add(cafeInfo);


                }
                cafeListAdapter = new CafeListAdapter(getContext(),cafeList,R.layout.list_group);
                listView.setAdapter(cafeListAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("INFO_PREFERENCE", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("cafe_id",cafeList.get(position).getCafe_id());
                        editor.putString("name", cafeList.get(position).getCafeName());
                        editor.putString("address", cafeList.get(position).getCafeAddress());
                        editor.putString("phone", cafeList.get(position).getCafePhone());
                        editor.putString("open", cafeList.get(position).getCafeOpen());
                        editor.putString("close",cafeList.get(position).getCafeClose());
                        editor.putString("person",cafeList.get(position).getCafePerson());
                        editor.putString("max", cafeList.get(position).getCafeMaximum());
                        editor.putString("intro", cafeList.get(position).getCafe_intro());
                        editor.putString("image",cafeList.get(position).getCafe_image());
                        editor.commit();

                        NavigationActivity activity = (NavigationActivity)getActivity();
                        activity.onFragmentChanged(1);

                    }
                });
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

   private class CafeListAdapter extends BaseAdapter{
       private Context context;
       private ArrayList<CafeInfo> cafeList = new ArrayList<CafeInfo>();
       private int layout;
       private LayoutInflater inflater;

       public CafeListAdapter(Context context, ArrayList<CafeInfo> cafeList, int layout) {
           this.context = context;
           this.cafeList = cafeList;
           this.layout = layout;

           inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
       }

       @Override
       public int getCount() {
           return cafeList.size();
       }

       @Override
       public Object getItem(int position) {
           return cafeList.get(position);
       }

       @Override
       public long getItemId(int position) {
           return position;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {

           if(convertView == null){
               convertView= inflater.inflate(layout,parent,false);

           }

           TextView nameView = (TextView)convertView.findViewById(R.id.cl_cafeName);
           TextView addressView = (TextView)convertView.findViewById(R.id.cl_adressView);
           TextView phoneView = (TextView)convertView.findViewById(R.id.cl_phoneView);
           TextView timeView = (TextView)convertView.findViewById(R.id.cl_timeView);
           TextView maxView = (TextView)convertView.findViewById(R.id.cl_maxView);
            TextView personPerMax = (TextView)convertView.findViewById(R.id.personPerMaxView);

           nameView.setText(cafeList.get(position).getCafeName());
           addressView.setText(cafeList.get(position).getCafeAddress());
           phoneView.setText(cafeList.get(position).getCafePhone());
           String time = cafeList.get(position).getCafeOpen() + " ~ " + cafeList.get(position).getCafeClose();
           timeView.setText(time);

           double person = Double.parseDouble(cafeList.get(position).getCafePerson());
           double max = Double.parseDouble(cafeList.get(position).getCafeMaximum());

           double maxPerPerson = (double)(person/max*100);

           maxView.setText((int)person + "/" + (int)max);
           personPerMax.setText("( " + maxPerPerson + "% )");

           if(maxPerPerson > 65){
               maxView.setTextColor(Color.RED);
               personPerMax.setTextColor(Color.RED);
           }else if(maxPerPerson > 40){
               maxView.setTextColor(Color.YELLOW);
               personPerMax.setTextColor(Color.YELLOW);
           }else if(maxPerPerson >0){
               maxView.setTextColor(Color.GREEN);
               personPerMax.setTextColor(Color.GREEN);
           }

           return convertView;
       }
   }

}
