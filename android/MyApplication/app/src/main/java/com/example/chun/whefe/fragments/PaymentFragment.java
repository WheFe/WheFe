package com.example.chun.whefe.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chun.whefe.MainActivity;
import com.example.chun.whefe.R;
import com.example.chun.whefe.ShoppingList;
import com.example.chun.whefe.dbhelper.MyHistoryHelper;
import com.example.chun.whefe.dbhelper.MyOpenHelper;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Chun on 2017-05-08.
 */

public class PaymentFragment extends Fragment {

    private String cafe_id;
    private String cafeName;
    private String my_id;

    Bitmap bitmap;

    ShoppingList shoppingList;
    // MyShoppingListAdapter myShoppingListAdapter;

    ArrayList<OrderList> orderLists;

    MyOpenHelper helper;
    SQLiteDatabase db;
    MyHistoryHelper historyHelper;
    SQLiteDatabase historyDB;

    private ArrayList<ShoppingList> sh_arrayList = new ArrayList<ShoppingList>();
    private HashMap<String, ArrayList<String>> sh_arrayChild = new HashMap<String, ArrayList<String>>();
    ExpandableListView sh_listView;
    ShoppingListAdapter sh_adapter;
    ShoppingListViewHolder sh_holder;

    Spinner couponSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment,container,false);

        SharedPreferences preferences = getContext().getSharedPreferences("INFO_PREFERENCE", Context.MODE_PRIVATE);
        cafeName = preferences.getString("name","NOTFOUND");
        cafe_id = preferences.getString("cafe_id","NOTFOUND");

        SharedPreferences preferences1 = getContext().getSharedPreferences("LOGIN_PREFERENCE", Context.MODE_PRIVATE);
        my_id = preferences1.getString("id","");

        helper = new MyOpenHelper(getContext());
        db = helper.getWritableDatabase();
        historyHelper = new MyHistoryHelper(getContext());
        historyDB = historyHelper.getWritableDatabase();

        TextView warningView = (TextView)view.findViewById(R.id.payment_warningVIew);
        warningView.setText(" 음료 수령시간이 늦어져 생기는 품질저하는 책임질 수 없으니, 주의하여 주시기 바랍니다.");

        setShoppingListData();
        sh_listView = (ExpandableListView)view.findViewById(R.id.payment_orderListView);

        sh_adapter = new ShoppingListAdapter(getContext(),sh_arrayList,sh_arrayChild);

        sh_listView.setClickable(true);
        sh_listView.setAdapter(sh_adapter);

        int totalPrice =0;
        for(int i = 0; i<sh_arrayList.size();i++) {
            //   totalPrice += sh_arrayList.get(0).getPrice();
            StringTokenizer s = new StringTokenizer(sh_arrayList.get(i).getPrice());
            String temp = s.nextToken("원");
            totalPrice += Integer.parseInt(temp);
        }
        TextView textView = (TextView)view.findViewById(R.id.pay_priceView);
        textView.setText("결제금액 " + totalPrice + " 원");

        ArrayList<String> couponSpinnerList = new ArrayList<String>();

        couponSpinnerList.add("선택해주세요.");
        couponSpinnerList.add("-500원");
        couponSpinnerList.add("-700원");
        couponSpinnerList.add("-1000원");

        ArrayAdapter<String> couponAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,couponSpinnerList);

        couponSpinner = (Spinner)view.findViewById(R.id.pay_coupon_spinner);
        couponSpinner.setAdapter(couponAdapter);

        couponSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculatePrice();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button cancelButton = (Button)view.findViewById(R.id.paymentCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        Button paymentButton = (Button)view.findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // customer_id, cafe_id, menu_name , hot_ice_none, menu_size, option_name
                orderLists = new ArrayList<OrderList>();

                Cursor rs = db.rawQuery("select * from shoppinglist;", null);
                if(rs.getCount()==0){
                    Toast.makeText(getContext(), "장바구니에 물품 없음", Toast.LENGTH_SHORT).show();
                }else {
                    while (rs.moveToNext()) {
                        String db_name = rs.getString(1);
                        String db_hot = rs.getString(2);
                        String db_size = rs.getString(3);
                        String db_option = rs.getString(4);

                        orderLists.add(new OrderList(my_id, cafe_id, db_name, db_hot, db_size, db_option));
                    }
                }
            }
        });

        return view;
    }


    public void calculatePrice(){
        int totalPrice =0;
        for(int i = 0; i<sh_arrayList.size();i++) {
            //   totalPrice += sh_arrayList.get(0).getPrice();
            StringTokenizer s = new StringTokenizer(sh_arrayList.get(i).getPrice());
            String temp = s.nextToken("원");
            totalPrice += Integer.parseInt(temp);
        }

        String coupon = couponSpinner.getSelectedItem().toString();
        try {
            StringTokenizer s = new StringTokenizer(coupon);
            String temp = s.nextToken("원");

            Log.i("coupon","temp : " + temp);

            totalPrice += Integer.parseInt(temp);
        }catch (Exception e){
            Log.i("coupon","token error");
        }
        TextView textView = (TextView)getView().findViewById(R.id.pay_priceView);
        textView.setText("결제금액 " + totalPrice + " 원");
    }
    private void setShoppingListData(){
        for(int i= sh_arrayList.size()-1;i>=0;i--) {
            sh_arrayChild.remove(sh_arrayList.get(i).getName());
            sh_arrayList.remove(i);
        }
        Cursor rs = db.rawQuery("select * from shoppinglist;", null);
        while(rs.moveToNext()){
            Log.i("DB",rs.getInt(0) + rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getString(4) + rs.getString(5) + rs.getInt(6));

            int db_id = rs.getInt(0);
            String db_name = rs.getString(1);
            String db_hot = rs.getString(2);
            String db_size = rs.getString(3);
            String db_option = rs.getString(4);
            String db_price = rs.getString(5);
            String db_image = rs.getString(6);

            sh_arrayList.add(new ShoppingList(db_id,db_name,db_hot,db_size,db_option,db_price,db_image));
        }

        ArrayList<String> arrayTemp = new ArrayList<String>();
        arrayTemp.add("a");

        for(int i=0;i<sh_arrayList.size();i++){
            sh_arrayChild.put(sh_arrayList.get(i).getName(),arrayTemp);
        }
    }
    public class ShoppingListAdapter extends BaseExpandableListAdapter {
        private Context context;
        private ArrayList<ShoppingList> arrayGroup;
        private HashMap<String, ArrayList<String>> arrayChild;

        public ShoppingListAdapter(Context context, ArrayList<ShoppingList> arrayGroup, HashMap<String, ArrayList<String>> arrayChild) {
            this.context = context;
            this.arrayGroup = arrayGroup;
            this.arrayChild = arrayChild;
        }

        public void upDateItemList(ArrayList<ShoppingList> arrayGroup, HashMap<String,ArrayList<String>> arrayChild){
            this.arrayGroup = arrayGroup;
            this.arrayChild = arrayChild;
            notifyDataSetChanged();
        }

        @Override
        public int getGroupCount() {
            return arrayGroup.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return arrayChild.get(arrayGroup.get(groupPosition).getName()).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return arrayGroup.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition);
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
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final ShoppingList shoppingList = arrayGroup.get(groupPosition);

            String groupName = shoppingList.getName();
            String price = shoppingList.getPrice();

            View v = convertView;

            if(v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = (RelativeLayout)inflater.inflate(R.layout.sh_list_group, null);


            }

            String imageFilename = shoppingList.getImageFilename();
            ImageView imageView = (ImageView)v.findViewById(R.id.sh_imageView);
            new LoadImage(imageView,getContext()).execute(MainActivity.ip + "/whefe/resources/images/menuimage/" + imageFilename);
          //  imageView.setImageResource(shoppingList.getImageResource());
            TextView textGroup = (TextView) v.findViewById(R.id.sh_nameView);
            textGroup.setText(groupName);
            TextView priceView = (TextView) v.findViewById(R.id.sh_priceView);
            priceView.setText(price);

            /*imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getContext());

                    dialog.setContentView(R.layout.image_zoom_dialog);
                    ImageView imageView = (ImageView)dialog.findViewById(R.id.dialog_imageView);
                    ImageButton cancelButton = (ImageButton)dialog.findViewById(R.id.dialog_closeButton);

                    imageView.setImageResource(shoppingList.getImageResource());

                    cancelButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });*/

            return v;
        }

        String hot;
        String size;
        String option;
        String coupon;

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, final View convertView, ViewGroup parent) {
            final String childName = arrayChild.get(arrayGroup.get(groupPosition).getName()).get(childPosition);

            View v = convertView;
             hot = arrayGroup.get(groupPosition).getHot();
             size = arrayGroup.get(groupPosition).getSize();
             option = arrayGroup.get(groupPosition).getOption();

            if(v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = (LinearLayout)inflater.inflate(R.layout.sh_list_child, null);

                sh_holder = new ShoppingListViewHolder();
                sh_holder.hotView = (TextView)v.findViewById(R.id.sh_hotVIew);
                sh_holder.sizeView = (TextView)v.findViewById(R.id.sh_sizeView);
                sh_holder.optionView = (TextView)v.findViewById(R.id.sh_optionView);

            }

            sh_holder.hotView.setText(hot);
            sh_holder.sizeView.setText(size);
            sh_holder.optionView.setText(option);
            Button deleteButton = (Button)v.findViewById(R.id.sh_deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.execSQL("delete from shoppinglist where _id = '" + arrayGroup.get(groupPosition).getId() + "';"  );
                        arrayChild.remove(arrayGroup.get(groupPosition).getName());
                        arrayGroup.remove(groupPosition);
                        calculatePrice();
                        notifyDataSetChanged();
                }
            });

            return v;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
    public static class ShoppingListViewHolder{
        public ImageView imageView;
        public TextView nameView;
        public TextView priceView;
        public TextView hotView;
        public TextView sizeView;
        public TextView optionView;

    }
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        ImageView imageView;
        Context context;

        public LoadImage(ImageView imageView, Context context){
            this.imageView = imageView;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if(image != null) {
                bitmap = image;
                //imageView1.setImageBitmap(image);
                imageView.setImageBitmap(bitmap);
            } else {
                //bitmap = R.drawable.whefe;
                Resources res = getResources();
                BitmapDrawable bd = null;
                bd = (BitmapDrawable) ContextCompat.getDrawable(getContext(),R.drawable.whefe);
                bitmap = bd.getBitmap();


                //Toast.makeText(getContext(), "이미지가 존재하지 않거나 네트워크 오류 발생", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class OrderList{
        private String customer_id;
        private String cafe_id;
        private String menu_name;
        private String hot_ice_none;
        private String menu_size;
        private String option_name;

        public OrderList(String customer_id, String cafe_id, String menu_name, String hot_ice_none, String menu_size, String option_name) {
            this.customer_id = customer_id;
            this.cafe_id = cafe_id;
            this.menu_name = menu_name;
            this.hot_ice_none = hot_ice_none;
            this.menu_size = menu_size;
            this.option_name = option_name;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public String getCafe_id() {
            return cafe_id;
        }

        public String getMenu_name() {
            return menu_name;
        }

        public String getHot_ice_none() {
            return hot_ice_none;
        }

        public String getMenu_size() {
            return menu_size;
        }

        public String getOption_name() {
            return option_name;
        }

    }
}
