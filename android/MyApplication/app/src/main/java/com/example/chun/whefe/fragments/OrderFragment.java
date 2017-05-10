package com.example.chun.whefe.fragments;

import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chun.whefe.MyOpenHelper;
import com.example.chun.whefe.NavigationActivity;
import com.example.chun.whefe.R;
import com.example.chun.whefe.ShoppingList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import static com.example.chun.whefe.R.id.optionSpinner;
import static com.example.chun.whefe.R.id.sizeSpinner;

/**
 * Created by Chun on 2017-05-08.
 */

public class OrderFragment extends Fragment {

    private CafeMenuAdapter cafeMenuAdapter;

    private ArrayList<CafeMenu> arrayList1 = new ArrayList<CafeMenu>();
    private ArrayList<CafeMenu> arrayList2 = new ArrayList<CafeMenu>();
    private HashMap<String, ArrayList<String>> arrayChild = new HashMap<String,ArrayList<String>>();
    private HashMap<String, ArrayList<String>> arrayChild2 = new HashMap<String,ArrayList<String>>();
    ExpandableListView listView;
    CoffeeViewHolder holder;

    TextView priceView;

    LinearLayout categoryLayout;

    SQLiteDatabase db;
    MyOpenHelper helper;

    private ArrayList<ShoppingList> sh_arrayList = new ArrayList<ShoppingList>();
    private HashMap<String, ArrayList<String>> sh_arrayChild = new HashMap<String, ArrayList<String>>();
    ExpandableListView sh_listView;
    ShoppingListAdapter sh_adapter;
    ShoppingListViewHolder sh_holder;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order,container,false);

        helper = new MyOpenHelper(getContext());
        db = helper.getWritableDatabase();


      //  setShoppingListData();

        listView = (ExpandableListView)view.findViewById(R.id.menuListView);
        setArrayList();
        setCategory();
        //listView.setAdapter(new AdptMain(this, arrayList, arrayChild));
        cafeMenuAdapter = new CafeMenuAdapter(getContext(),arrayList1,arrayChild);
        listView.setAdapter(cafeMenuAdapter);

        // 다른 그룹 닫기
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int groupCount = cafeMenuAdapter.getGroupCount();
                for(int i= 0; i<groupCount;i++){
                    if(!(i==groupPosition))
                        listView.collapseGroup(i);
                }
            }
        });

        Button orderButton = (Button)view.findViewById(R.id.orderSuccessButton);
        ImageButton sh_listButton  = (ImageButton)view.findViewById(R.id.shoppingListButton);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity activity = (NavigationActivity)getActivity();
                activity.onFragmentChanged(4);
            }
        });

        /*--------------------장바구니 클릭 시 다이얼로그------------------------*/
        sh_listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                setShoppingListData();
                dialog.setTitle("장바구니");
                dialog.setContentView(R.layout.shoppinglistdialog);
                TextView title = (TextView)dialog.findViewById(R.id.sh_titleView);
                ImageView imageView = (ImageView)dialog.findViewById(R.id.sh_titleImageView);
                Button cancel = (Button)dialog.findViewById(R.id.sh_cancelButton);
                Button payment = (Button)dialog.findViewById(R.id.sh_paymentButton);
                priceView = (TextView)dialog.findViewById(R.id.sh_totalPriceView);
                title.setText("장바구니");
                imageView.setImageResource(R.drawable.shopping_cart);

                cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                payment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        NavigationActivity activity = (NavigationActivity)getActivity();
                        activity.onFragmentChanged(4);
                    }
                });
                sh_listView = (ExpandableListView)dialog.findViewById(R.id.shoppintListView);

                sh_adapter = new ShoppingListAdapter(getContext(),sh_arrayList,sh_arrayChild);

                sh_listView.setClickable(true);
                sh_listView.setAdapter(sh_adapter);

                sh_listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {
                        int groupCount = sh_adapter.getGroupCount();
                        for(int i= 0; i<groupCount;i++){
                            if(!(i==groupPosition))
                                sh_listView.collapseGroup(i);
                        }
                    }
                });

                int totalPrice =0;
                for(int i = 0; i<sh_arrayList.size();i++){
                    //   totalPrice += sh_arrayList.get(0).getPrice();
                    StringTokenizer s = new StringTokenizer(sh_arrayList.get(i).getPrice());
                    String temp = s.nextToken("원");
                    totalPrice += Integer.parseInt(temp);
                }

                priceView.setText("결제금액 " + totalPrice + " 원");

                dialog.show();
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
        priceView.setText("결제금액 " + totalPrice + " 원");
    }
    private void setCategory(){
        categoryLayout = (LinearLayout)view.findViewById(R.id.categoryLayout);
        Button button1 = new Button(getContext());
        button1.setText("커피");

        Button button2 = new Button(getContext());
        button2.setText("스무디");

        Button button3 = new Button(getContext());
        button3.setText("에이드");

        Button button4 = new Button(getContext());
        button4.setText("빵");

        Button button5 = new Button(getContext());
        button5.setText("기타");

        categoryLayout.addView(button1);
        categoryLayout.addView(button2);
        categoryLayout.addView(button3);
        categoryLayout.addView(button4);
        categoryLayout.addView(button5);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cafeMenuAdapter.upDateItemList(arrayList1,arrayChild);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cafeMenuAdapter.upDateItemList(arrayList2,arrayChild2);
            }
        });
    }
    private void setArrayList(){
        for(int i= arrayList1.size()-1;i>=0;i--) {
            arrayChild.remove(arrayList1.get(i).getName());
            arrayList1.remove(i);
        }
        for(int i= arrayList2.size()-1;i>=0;i--){
            arrayChild.remove(arrayList2.get(i).getName());
            arrayList2.remove(i);
        }

        arrayList1.add(new CafeMenu("아메리카노","1800원"));
        arrayList1.add(new CafeMenu("카페라떼","2500원"));
        arrayList1.add(new CafeMenu("카푸치노","2300원"));
        arrayList1.add(new CafeMenu("카라멜마끼야또","2500원"));
        ArrayList<String> arrayChicken = new ArrayList<String>();
        arrayChicken.add("a");

        arrayChild.put(arrayList1.get(0).getName(),arrayChicken);
        arrayChild.put(arrayList1.get(1).getName(),arrayChicken);
        arrayChild.put(arrayList1.get(2).getName(),arrayChicken);
        arrayChild.put(arrayList1.get(3).getName(),arrayChicken);

        arrayList2.add(new CafeMenu("플레인스무디","3000원"));
        arrayList2.add(new CafeMenu("망고스무디","3000원"));
        arrayList2.add(new CafeMenu("딸기스무디","3000원"));

        arrayChild2.put(arrayList2.get(0).getName(),arrayChicken);
        arrayChild2.put(arrayList2.get(1).getName(),arrayChicken);
        arrayChild2.put(arrayList2.get(2).getName(),arrayChicken);

    }
    private void setShoppingListData(){
        /*  ShoppingList(String name, String hot, String size, String option, String coupon, String price)  */
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



    public class CafeMenuAdapter extends BaseExpandableListAdapter {
        private Context context;

        private ArrayList<CafeMenu> arrayGroup;
        private HashMap<String, ArrayList<String>> arrayChild;

        public CafeMenuAdapter(Context context, ArrayList<CafeMenu> arrayGroup, HashMap<String, ArrayList<String>> arrayChild) {
            this.context = context;
            this.arrayGroup = arrayGroup;
            this.arrayChild = arrayChild;
        }

        public void upDateItemList(ArrayList<CafeMenu> arrayGroup, HashMap<String,ArrayList<String>> arrayChild){
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
            CafeMenu cafeMenu = arrayGroup.get(groupPosition);

            String groupName = cafeMenu.getName();
            String price = cafeMenu.getPrice();
            //String groupName = arrayGroup2.get(groupPosition);
            View v = convertView;

            if(v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = (RelativeLayout)inflater.inflate(R.layout.menugroup, null);
            }
            TextView textGroup = (TextView) v.findViewById(R.id.sh_nameView);
            textGroup.setText(groupName);
            TextView priceView = (TextView) v.findViewById(R.id.priceVIew);
            priceView.setText(price);

            return v;
        }



        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
            String childName = arrayChild.get(arrayGroup.get(groupPosition).getName()).get(childPosition);
             View v = convertView;

            if(v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = (RelativeLayout)inflater.inflate(R.layout.menuchild, null);

                holder = new CoffeeViewHolder();
               // holder.addButton = (Button)v.findViewById(R.id.addButton);
                holder.couponSpinner = (Spinner)v.findViewById(R.id.couponSpinner);
                holder.optionSpinner = (Spinner)v.findViewById(optionSpinner);
                holder.sizeSpinner = (Spinner)v.findViewById(sizeSpinner);
                holder.radioGroup = (RadioGroup)v.findViewById(R.id.radioGroup);

            }
            /*-------------------------------스피너 어댑터 설정----------------------------------------------------------*/
            ArrayList<String> sizeSpinnerlist = new ArrayList<String>();

            sizeSpinnerlist.add("Size");
            sizeSpinnerlist.add("Small");
            sizeSpinnerlist.add("Medium");
            sizeSpinnerlist.add("Large");

            ArrayAdapter<String> sizeadapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,sizeSpinnerlist);
            holder.sizeSpinner.setAdapter(sizeadapter);

            ArrayList<String> optionSpinnerlist = new ArrayList<String>();

            optionSpinnerlist.add("Option");
            optionSpinnerlist.add("샷 추가(+500)");
            optionSpinnerlist.add("휘핑 추가(+500)");

            ArrayAdapter<String>   optionadapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,optionSpinnerlist);
            holder.optionSpinner.setAdapter(optionadapter);

            ArrayList<String> couponSpinnerlist = new ArrayList<String>();

            couponSpinnerlist.add("Coupon");
            couponSpinnerlist.add("-500");
            couponSpinnerlist.add("-1000");

            ArrayAdapter<String>   couponAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,couponSpinnerlist);
            holder.couponSpinner.setAdapter(couponAdapter);
            /*-----------------------------------------------------------------------------------*/
            Button addButton = (Button)v.findViewById(R.id.addButton);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Spinner sizeSpinner = (Spinner)parent.findViewById(R.id.sizeSpinner);
                    Spinner couponSpinner = (Spinner)parent.findViewById(R.id.couponSpinner);
                    Spinner optionSpinner = (Spinner)parent.findViewById(R.id.optionSpinner);
                    RadioGroup radioGroup = (RadioGroup)parent.findViewById(R.id.radioGroup);

                    String se_size = sizeSpinner.getSelectedItem().toString();
                    String se_option = optionSpinner.getSelectedItem().toString();
                    String se_coupon = couponSpinner.getSelectedItem().toString();
                    String se_name = arrayGroup.get(groupPosition).getName();
                    String se_price = arrayGroup.get(groupPosition).getPrice();

                    int radioId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton)parent.findViewById(radioId);
                    String se_hot = radioButton.getText().toString();

                    Toast.makeText(context,se_name + se_price+se_hot +se_size+ se_option + se_coupon  ,Toast.LENGTH_SHORT).show();
                    /*ShoppingList shList = new ShoppingList(se_name,se_hot,se_size,se_option,se_coupon,se_price);
                    ArrayList<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
                    shoppingLists.add(shList);*/
                    db.execSQL("insert into shoppinglist(name, hot, size, option, coupon, price) " +
                            "values('"+ se_name + "','" + se_hot + "', '" + se_size + "', '" + se_option + "', '" + se_coupon + "','" + se_price + "');");

                }
            });
            return v;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }


    public class ShoppingListAdapter extends BaseExpandableListAdapter{
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
            //String groupName = arrayGroup2.get(groupPosition);
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
        String hot ;
        String size;
        String option;
        String coupon;
        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String childName = arrayChild.get(arrayGroup.get(groupPosition).getName()).get(childPosition);

            View v = convertView;
            hot = arrayGroup.get(groupPosition).getHot();
            size = arrayGroup.get(groupPosition).getSize();
            option = arrayGroup.get(groupPosition).getOption();
            coupon = arrayGroup.get(groupPosition).getCoupon();
            Log.i("child",hot + size + option + coupon);


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
                    db.execSQL("delete from shoppinglist where name = '" + arrayGroup.get(groupPosition).getName() + "' and hot = '" + hot
                            + "' and size = '" + size + "' and option = '" + option
                            + "' and coupon = '" + coupon + "'and price = '" + arrayGroup.get(groupPosition).getPrice()  + "';"  );
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

    public static class CoffeeViewHolder{
        public RadioGroup radioGroup;
        public Spinner couponSpinner;
        public Spinner optionSpinner;
        public Spinner sizeSpinner;
        public ImageView menuImageView;
        public TextView menuNameTextView;
        public Button addButton;

    }

    class CafeMenu {
        private String name;
        private String price;

        public CafeMenu(String name, String price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
