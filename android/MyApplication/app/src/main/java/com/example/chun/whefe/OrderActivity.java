package com.example.chun.whefe;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    int[] images = {R.drawable.grazie1,R.drawable.grazie2};
    String[] menunames = {"아메리카노", "카페라떼"};

    List<Coffee> coffees;
    List<Coffee> smoothies;
    List<ShoppingList> shoppingLists;
    TabHost tabHost;
    ListView sh_listView;
   // MyMenuListAdapter myMenuListAdapter;
   // MyShoppingListAdapter myShoppingListAdapter;

    private CafeMenuAdapter cafeMenuAdapter;

    private ArrayList<CafeMenu> arrayList1 = new ArrayList<CafeMenu>();
    private ArrayList<CafeMenu> arrayList2 = new ArrayList<CafeMenu>();
    private HashMap<String, ArrayList<String>> arrayChild = new HashMap<String,ArrayList<String>>();
    private HashMap<String, ArrayList<String>> arrayChild2 = new HashMap<String,ArrayList<String>>();
    ExpandableListView listView;
    LinearLayout categoryLayout;

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



        listView = (ExpandableListView)findViewById(R.id.menuListView);
        setArrayList();
        setCategory();
        //listView.setAdapter(new AdptMain(this, arrayList, arrayChild));
        cafeMenuAdapter = new CafeMenuAdapter(this,arrayList1,arrayChild);
        listView.setAdapter(cafeMenuAdapter);


        /*shoppingLists = new ArrayList<ShoppingList>();

        ShoppingList s1 = new ShoppingList("아메리카노","Iced","medium","샷추가+500","No Coupon","2300");
        ShoppingList s2 = new ShoppingList("카페모카","Hot", "small","","-500","3500");
        ShoppingList s3 = new ShoppingList("아메리카노", "Hot", "Large", "연하게+0","No Coupon","1800");

        shoppingLists.add(s1);
        shoppingLists.add(s2);
        shoppingLists.add(s3);

        myShoppingListAdapter = new MyShoppingListAdapter(this,R.layout.shoppinglist,shoppingLists);

        sh_listView = (ListView)findViewById(R.id.ShoppingListView);
        sh_listView.setAdapter(myShoppingListAdapter);
*/

      //  View header = getLayoutInflater().inflate(R.layout.order_list,null,false);
      //  listView.addHeaderView(header);

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
    public void onOrderSuccessButtonClicked(View v){
        Intent intent =  new Intent(OrderActivity.this,PaymentActivity.class);
        startActivity(intent);
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
            TextView textGroup = (TextView) v.findViewById(R.id.CoffeeNameView);
            textGroup.setText(groupName);
            TextView priceView = (TextView) v.findViewById(R.id.priceVIew);
            priceView.setText(price);

            return v;
        }

        CoffeeViewHolder holder;

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

            return v;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }


    public class Coffee{
        private int image;
        private String menuname;

        public void setImage(int image){
            this.image = image;
        }
        public void setMenuname(String menuname){
            this.menuname = menuname;
        }
        public int getImage(){
            return image;
        }
        public String getMenuname(){
            return menuname;
        }
    }
    public class ShoppingList{
        private String name;
        private String hot;
        private String size;
        private String option;
        private String coupon;
        private String price;

        public ShoppingList(){};
        public ShoppingList(String name, String hot, String size, String option, String coupon, String price) {
            this.name = name;
            this.hot = hot;
            this.size = size;
            this.option = option;
            this.coupon = coupon;
            this.price = price;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getHot() {
            return hot;
        }
        public void setHot(String hot) {
            this.hot = hot;
        }
        public String getSize() {
            return size;
        }
        public void setSize(String size) {
            this.size = size;
        }
        public String getOption() {
            return option;
        }
        public void setOption(String option) {
            this.option = option;
        }
        public String getCoupon() {
            return coupon;
        }
        public void setCoupon(String coupon) {
            this.coupon = coupon;
        }
        public String getPrice() {
            return price;
        }
        public void setPrice(String price) {
            this.price = price;
        }
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

    /*public class MyShoppingListAdapter extends ArrayAdapter<ShoppingList> {

        LayoutInflater lnf;

        public MyShoppingListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ShoppingList> shoppingLists) {
            super(context, resource, shoppingLists);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return shoppingLists.size();
        }

        @Override
        public ShoppingList getItem(int position) {
            return shoppingLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                //  LayoutInflater inflater = getLayoutInflater();
                convertView = lnf.inflate(R.layout.shoppinglist,parent,false);
            }

            ShoppingList shoppingList = new ShoppingList();
            shoppingList.setName(shoppingLists.get(position).getName());
            shoppingList.setHot(shoppingLists.get(position).getHot());
            shoppingList.setSize(shoppingLists.get(position).getSize());
            shoppingList.setOption(shoppingLists.get(position).getOption());
            shoppingList.setCoupon(shoppingLists.get(position).getCoupon());
            shoppingList.setPrice(shoppingLists.get(position).getPrice());

            TextView nameView = (TextView)convertView.findViewById(R.id.sh_list_nameView);
            TextView hotView = (TextView)convertView.findViewById(R.id.sh_list_hotView);
            TextView sizeView = (TextView)convertView.findViewById(R.id.sh_list_sizeView);
            TextView optionView = (TextView)convertView.findViewById(R.id.sh_list_optionView);
            TextView couponView = (TextView)convertView.findViewById(R.id.sh_list_couponView);
            TextView priceView = (TextView)convertView.findViewById(R.id.sh_list_priceView);

            nameView.setText(shoppingList.getName());
            hotView.setText(shoppingList.getHot());
            sizeView.setText(shoppingList.getSize());
            optionView.setText(shoppingList.getOption());
            couponView.setText(shoppingList.getCoupon());
            priceView.setText(shoppingList.getPrice()+"원");

            return convertView;
        }
    }
*/
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
