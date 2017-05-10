package com.example.chun.whefe.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chun.whefe.MyOpenHelper;
import com.example.chun.whefe.R;
import com.example.chun.whefe.ShoppingList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Chun on 2017-05-08.
 */

public class PaymentFragment extends Fragment {

    ShoppingList shoppingList;
    // MyShoppingListAdapter myShoppingListAdapter;

    MyOpenHelper helper;
    SQLiteDatabase db;

    private ArrayList<ShoppingList> sh_arrayList = new ArrayList<ShoppingList>();
    private HashMap<String, ArrayList<String>> sh_arrayChild = new HashMap<String, ArrayList<String>>();
    ExpandableListView sh_listView;
    ShoppingListAdapter sh_adapter;
    ShoppingListViewHolder sh_holder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment,container,false);

        helper = new MyOpenHelper(getContext());
        db = helper.getWritableDatabase();

        TextView warningView = (TextView)view.findViewById(R.id.payment_warningVIew);
        warningView.setText("본 서비스는 사용자가 카페의 XXm 근처에 있으면 주문이 들어가며, 제조가 시작됩니다." +
                " 음료 수령시간이 늦어져 생기는 품질저하는 책임질 수 없으니, 주의하여 주시기 바랍니다.");


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
            Log.i("DB",rs.getString(0) + rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getString(4) + rs.getString(5));
            String db_name = rs.getString(0);
            String db_hot = rs.getString(1);
            String db_size = rs.getString(2);
            String db_option = rs.getString(3);
            String db_coupon = rs.getString(4);
            String db_price = rs.getString(5);

            sh_arrayList.add(new ShoppingList(db_name,db_hot,db_size,db_option,db_coupon,db_price));

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
            ShoppingList shoppingList = arrayGroup.get(groupPosition);

            String groupName = shoppingList.getName();
            String price = shoppingList.getPrice();

            View v = convertView;

            if(v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = (RelativeLayout)inflater.inflate(R.layout.sh_list_group, null);


            }

            TextView textGroup = (TextView) v.findViewById(R.id.sh_nameView);
            textGroup.setText(groupName);
            TextView priceView = (TextView) v.findViewById(R.id.sh_priceView);
            priceView.setText(price);

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
             coupon = arrayGroup.get(groupPosition).getCoupon();

            if(v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = (LinearLayout)inflater.inflate(R.layout.sh_list_child, null);

                sh_holder = new ShoppingListViewHolder();
                sh_holder.hotView = (TextView)v.findViewById(R.id.sh_hotVIew);
                sh_holder.sizeView = (TextView)v.findViewById(R.id.sh_sizeView);
                sh_holder.optionView = (TextView)v.findViewById(R.id.sh_optionView);
                sh_holder.couponView = (TextView)v.findViewById(R.id.sh_couponView);
            }

            sh_holder.hotView.setText(hot);
            sh_holder.sizeView.setText(size);
            sh_holder.optionView.setText(option);
            sh_holder.couponView.setText(coupon);
            Button deleteButton = (Button)v.findViewById(R.id.sh_deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.execSQL("delete from shoppinglist where name = '" + arrayGroup.get(groupPosition).getName() + "' , hot = '" + hot
                            + "' , size = '" + size + "' , option = '" + option
                            + "' , coupon = '" + coupon + "', price = '" + arrayGroup.get(groupPosition).getPrice()  + "';"  );
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
        public TextView couponView;
    }
}
