package com.example.chun.whefe;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class OrderActivity extends AppCompatActivity {

    private CafeMenuAdapter cafeMenuAdapter;

    private ArrayList<CafeMenu> arrayList1 = new ArrayList<CafeMenu>();
    private ArrayList<CafeMenu> arrayList2 = new ArrayList<CafeMenu>();
    private HashMap<String, ArrayList<String>> arrayChild = new HashMap<String,ArrayList<String>>();
    private HashMap<String, ArrayList<String>> arrayChild2 = new HashMap<String,ArrayList<String>>();
    ExpandableListView listView;
    CoffeeViewHolder holder;

    LinearLayout categoryLayout;


    private ArrayList<ShoppingList> sh_arrayList = new ArrayList<ShoppingList>();
    private HashMap<String, ArrayList<String>> sh_arrayChild = new HashMap<String, ArrayList<String>>();
    ExpandableListView sh_listView;
    ShoppingListAdapter sh_adapter;
    ShoppingListViewHolder sh_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        /*------------------------Tool bar-----------------------*/
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);

        toolbar.setTitle("Title");
        toolbar.setTitleTextColor(Color.parseColor("#ffff33"));
        toolbar.setSubtitle("id");
        toolbar.setNavigationIcon(R.drawable.listing_option);
        setSupportActionBar(toolbar);
        /*-------------------------------------------------------*/

        setShoppingListData();

        listView = (ExpandableListView)findViewById(R.id.menuListView);
        setArrayList();
        setCategory();
        //listView.setAdapter(new AdptMain(this, arrayList, arrayChild));
        cafeMenuAdapter = new CafeMenuAdapter(this,arrayList1,arrayChild);
        listView.setAdapter(cafeMenuAdapter);

    }
    private void setCategory(){
        categoryLayout = (LinearLayout)findViewById(R.id.categoryLayout);
        Button button1 = new Button(this);
        button1.setText("커피");

        Button button2 = new Button(this);
        button2.setText("스무디");

        Button button3 = new Button(this);
        button3.setText("에이드");

        Button button4 = new Button(this);
        button4.setText("빵");

        Button button5 = new Button(this);
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
        sh_arrayList.add(new ShoppingList("카페라떼","hot","medium","샷추가 +500","-500원","2500원"));
        sh_arrayList.add(new ShoppingList("플레인스무디","","large","","-1000원","2500원"));
        sh_arrayList.add(new ShoppingList("아메리카노","iced","small","샷추가 +500","","2300원"));
        sh_arrayList.add(new ShoppingList("카푸치노","hot","medium","","-500원","1800원"));
        ArrayList<String> arrayTemp = new ArrayList<String>();
        arrayTemp.add("a");

        sh_arrayChild.put(sh_arrayList.get(0).getName(),arrayTemp);
        sh_arrayChild.put(sh_arrayList.get(1).getName(),arrayTemp);
        sh_arrayChild.put(sh_arrayList.get(2).getName(),arrayTemp);
        sh_arrayChild.put(sh_arrayList.get(3).getName(),arrayTemp);
    }
    public void onOrderSuccessButtonClicked(View v){
        Intent intent =  new Intent(OrderActivity.this,PaymentActivity.class);
        startActivity(intent);
    }

    /* 장바구니 클릭시 다이얼로그 */

    public void onShoppingListButtonClicked(View v){
        final Dialog dialog = new Dialog(OrderActivity.this);
        dialog.setTitle("장바구니");
        dialog.setContentView(R.layout.shoppinglistdialog);
        TextView title = (TextView)dialog.findViewById(R.id.sh_titleView);
        ImageView imageView = (ImageView)dialog.findViewById(R.id.sh_titleImageView);
        Button cancel = (Button)dialog.findViewById(R.id.sh_cancelButton);
        Button payment = (Button)dialog.findViewById(R.id.sh_paymentButton);
        TextView priceView = (TextView)dialog.findViewById(R.id.sh_totalPriceView);
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
                Intent intent = new Intent(OrderActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });
        sh_listView = (ExpandableListView)dialog.findViewById(R.id.shoppintListView);

        sh_adapter = new ShoppingListAdapter(this,sh_arrayList,sh_arrayChild);

        sh_listView.setClickable(true);
        sh_listView.setAdapter(sh_adapter);
        int totalPrice =0;
        for(int i = 0; i<sh_arrayList.size();i++){
         //   totalPrice += sh_arrayList.get(0).getPrice();
            StringTokenizer s = new StringTokenizer(sh_arrayList.get(i).getPrice());
            String temp = s.nextToken("원");
            totalPrice += Integer.parseInt(temp);
        }

        priceView.setText("결제금액" + totalPrice + "원");

        dialog.show();

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
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String childName = arrayChild.get(arrayGroup.get(groupPosition).getName()).get(childPosition);
            // String childName = (String)this.getChild(groupPosition,childPosition);
            View v = convertView;

            if(v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = (RelativeLayout)inflater.inflate(R.layout.menuchild, null);

                holder = new CoffeeViewHolder();
                holder.addButton = (Button)v.findViewById(R.id.addButton);
                holder.couponSpinner = (Spinner)v.findViewById(R.id.couponSpinner);
                holder.optionSpinner = (Spinner)v.findViewById(R.id.optionSpinner);
                holder.sizeSpinner = (Spinner)v.findViewById(R.id.sizeSpinner);
                holder.radioGroup = (RadioGroup)v.findViewById(R.id.radioGroup);
              //  holder.menuImageView = (ImageView) convertView.findViewById(R.id.coffeeImageView);
              //  holder.menuNameTextView = (TextView)convertView.findViewById(R.id.CoffeeNameView);
            }
            /*-------------------------------스피너 어댑터 설정----------------------------------------------------------*/
            ArrayList<String> sizeSpinnerlist = new ArrayList<String>();

            sizeSpinnerlist.add("Size");
            sizeSpinnerlist.add("Small");
            sizeSpinnerlist.add("Medium");
            sizeSpinnerlist.add("Large");

            ArrayAdapter<String> sizeadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,sizeSpinnerlist);
            holder.sizeSpinner.setAdapter(sizeadapter);

            ArrayList<String> optionSpinnerlist = new ArrayList<String>();

            optionSpinnerlist.add("Option");
            optionSpinnerlist.add("샷 추가(+500)");
            optionSpinnerlist.add("휘핑 추가(+500)");

            ArrayAdapter<String>   optionadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,optionSpinnerlist);
            holder.optionSpinner.setAdapter(optionadapter);

            ArrayList<String> couponSpinnerlist = new ArrayList<String>();

            couponSpinnerlist.add("Coupon");
            couponSpinnerlist.add("-500");
            couponSpinnerlist.add("-1000");

            ArrayAdapter<String>   couponAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item,couponSpinnerlist);
            holder.couponSpinner.setAdapter(couponAdapter);
            /*-----------------------------------------------------------------------------------*/
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
            //Button button = (Button) v.findViewById(R.id.sh_deleteButton);
            //button.setVisibility();
            TextView textGroup = (TextView) v.findViewById(R.id.sh_nameView);
            textGroup.setText(groupName);
            TextView priceView = (TextView) v.findViewById(R.id.sh_priceView);
            priceView.setText(price);

            return v;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String childName = arrayChild.get(arrayGroup.get(groupPosition).getName()).get(childPosition);

            TextView hotView;
            TextView sizeView;
            TextView optionView;
            TextView couponView;

            View v = convertView;
            String hot = arrayGroup.get(groupPosition).getHot();
            String size = arrayGroup.get(groupPosition).getSize();
            String option = arrayGroup.get(groupPosition).getOption();
            String coupon = arrayGroup.get(groupPosition).getCoupon();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch(curId){
            case R.id.menu_setting:
                Toast.makeText(this,"setting selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_logout:
                Toast.makeText(this,"logout selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_exit:
                Toast.makeText(this,"exit selected",Toast.LENGTH_SHORT).show();
                moveTaskToBack(true);
                finish();
                ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
                am.restartPackage(getPackageName());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
