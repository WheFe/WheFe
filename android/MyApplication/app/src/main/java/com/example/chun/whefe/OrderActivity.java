package com.example.chun.whefe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    int[] images = {R.drawable.grazie1,R.drawable.grazie2};
    String[] menunames = {"아메리카노", "카페라떼"};

    List<Coffee> coffees;
    List<Coffee> smoothies;
    List<ShoppingList> shoppingLists;
    TabHost tabHost;
    ListView listView;
    ListView sh_listView;
    MyMenuListAdapter myMenuListAdapter;
    MyShoppingListAdapter myShoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);


        /*------------------------ Tab Host Setting ---------------------------*/

        tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("buttonTab");
        //spec.setContent(R.id.buttonTab);
        spec.setIndicator("Coffee");
        spec.setContent(R.id.layout_1);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);


        /*------------------------ List View Setting --------------------------*/
        coffees = new ArrayList<Coffee>();

        Coffee c1 = new Coffee();
        c1.setImage(R.drawable.grazie1);
        c1.setMenuname("아메리카노");

        Coffee c2 = new Coffee();
        c2.setImage(R.drawable.grazie2);
        c2.setMenuname("카페라떼");

        Coffee c3 = new Coffee();
        c3.setImage(R.drawable.paint1);
        c3.setMenuname("카푸치노");

        Coffee c4 = new Coffee();
        c4.setImage(R.drawable.paint2);
        c4.setMenuname("카페모카");

        Coffee c5 = new Coffee();
        c5.setImage(R.drawable.grazie1);
        c5.setMenuname("플레인스무디");

        Coffee c6 = new Coffee();
        c6.setImage(R.drawable.grazie2);
        c6.setMenuname("망고스무디");

        coffees.add(c1);
        coffees.add(c2);
        coffees.add(c3);
        coffees.add(c4);
        coffees.add(c5);
        coffees.add(c6);
        Log.i("CGY","coffee size : "+coffees.size());

        myMenuListAdapter = new MyMenuListAdapter(this,R.layout.cafemenu,coffees);

        listView = (ListView)findViewById(R.id.TabListView1);
        listView.setAdapter(myMenuListAdapter);

        shoppingLists = new ArrayList<ShoppingList>();

        ShoppingList s1 = new ShoppingList("아메리카노","Iced","medium","샷추가+500","No Coupon","2300");
        ShoppingList s2 = new ShoppingList("카페모카","Hot", "small","","-500","3500");
        ShoppingList s3 = new ShoppingList("아메리카노", "Hot", "Large", "연하게+0","No Coupon","1800");

        shoppingLists.add(s1);
        shoppingLists.add(s2);
        shoppingLists.add(s3);

        myShoppingListAdapter = new MyShoppingListAdapter(this,R.layout.shoppinglist,shoppingLists);

        sh_listView = (ListView)findViewById(R.id.ShoppingListView);
        sh_listView.setAdapter(myShoppingListAdapter);

        Utility.getListViewSize(listView);
        Utility.getShoppingListViewSize(sh_listView);


      //  View header = getLayoutInflater().inflate(R.layout.order_list,null,false);
      //  listView.addHeaderView(header);

        /*--------------------- Tab 추가 ----------------------------------*/
        TabHost.TabSpec spec2 = tabHost.newTabSpec("tag2");
        spec2.setContent(R.id.layout_2);
        spec2.setIndicator("Smoothie");
        tabHost.addTab(spec2);
        tabHost.setCurrentTab(0);
      //  tabHost.getTabWidget().getTabCount();
      //  Toast.makeText(this,tabHost.getTabWidget().getTabCount(),Toast.LENGTH_LONG).show();

        smoothies = new ArrayList<Coffee>();

        c5 = new Coffee();
        c5.setImage(R.drawable.grazie1);
        c5.setMenuname("플레인스무디");

        c6 = new Coffee();
        c6.setImage(R.drawable.grazie2);
        c6.setMenuname("망고스무디");

        smoothies.add(c5);
        smoothies.add(c6);

        MyMenuListAdapter myMenuListAdapter2 = new MyMenuListAdapter(getApplicationContext(),R.layout.cafemenu,smoothies);
        // listView.notify();
        // listView = (ListView)findViewById(R.id.TabListView2);
        ListView listView2 = (ListView)findViewById(R.id.TabListView2);
        // listview2.notifyAll();
        listView2.setAdapter(myMenuListAdapter2);


        Log.i("CGY","tabcount : " +tabHost.getTabWidget().getTabCount());
        /*---------------------List 2 ---------------------------------*/



    }


    public class MyListViewSetting implements TabHost.TabContentFactory{
        @Override
        public View createTabContent(String tag) {
            smoothies = new ArrayList<Coffee>();

            Coffee c5 = new Coffee();
            c5.setImage(R.drawable.grazie1);
            c5.setMenuname("플레인스무디");

            Coffee c6 = new Coffee();
            c6.setImage(R.drawable.grazie2);
            c6.setMenuname("망고스무디");

            smoothies.add(c5);
            smoothies.add(c6);

            myMenuListAdapter = new MyMenuListAdapter(getApplicationContext(),R.layout.cafemenu,smoothies);

            MyMenuListAdapter myMenuListAdapter2 = new MyMenuListAdapter(getApplicationContext(),R.layout.cafemenu,smoothies);
          // listView.notify();
            // listView = (ListView)findViewById(R.id.TabListView2);
            ListView listView2 = (ListView)findViewById(R.id.TabListView2);
           // listview2.notifyAll();
            listView2.setAdapter(myMenuListAdapter2);

            return findViewById(R.id.layout_2);

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

    public class MyMenuListAdapter extends ArrayAdapter<Coffee> {

        //List<Coffee> coffees;
        //Context context;

        LayoutInflater lnf;

        public MyMenuListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Coffee> coffees) {
            super(context, resource, coffees);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // this.context = context;
           // this.coffees = coffees;
        }

        @Override
        public int getCount() {
            return coffees.size();
        }

        @Override
        public Coffee getItem(int position) {
            return coffees.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
              //  LayoutInflater inflater = getLayoutInflater();
                convertView = lnf.inflate(R.layout.cafemenu,parent,false);
            }

            Coffee coffee = new Coffee();
            coffee.setImage(coffees.get(position).getImage());
            coffee.setMenuname(coffees.get(position).getMenuname());

            ImageView menuImage = (ImageView)convertView.findViewById(R.id.MenuImage);
            TextView menuName = (TextView)convertView.findViewById(R.id.MenuNameView);

            menuImage.setImageResource(coffee.getImage());
            menuName.setText(coffee.getMenuname());

            /*----------------- 스피너 --------------------------*/
            Spinner sizeSpinner = (Spinner) convertView.findViewById(R.id.SizeSpinner);
            ArrayList<String> sizeSpinnerlist = new ArrayList<String>();

            sizeSpinnerlist.add("Size");
            sizeSpinnerlist.add("Small");
            sizeSpinnerlist.add("Medium");
            sizeSpinnerlist.add("Large");

            ArrayAdapter<String>   sizeadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,sizeSpinnerlist);
            sizeSpinner.setAdapter(sizeadapter);

            Spinner optionSpinner = (Spinner) convertView.findViewById(R.id.OptionSpinner);
            ArrayList<String> optionSpinnerlist = new ArrayList<String>();

            optionSpinnerlist.add("Option");
            optionSpinnerlist.add("샷 추가(+500)");
            optionSpinnerlist.add("휘핑 추가(+500)");

            ArrayAdapter<String>   optionadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,optionSpinnerlist);
            optionSpinner.setAdapter(optionadapter);

            Spinner couponSpinner = (Spinner) convertView.findViewById(R.id.CouponSpinner);
            ArrayList<String> couponSpinnerlist = new ArrayList<String>();

            couponSpinnerlist.add("Coupon");
            couponSpinnerlist.add("-500");
            couponSpinnerlist.add("-1000");

            ArrayAdapter<String>   couponAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,couponSpinnerlist);
            couponSpinner.setAdapter(couponAdapter);

            return convertView;
        }
    }

    public class MyShoppingListAdapter extends ArrayAdapter<ShoppingList> {

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


}
