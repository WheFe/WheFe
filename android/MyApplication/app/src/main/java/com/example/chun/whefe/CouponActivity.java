package com.example.chun.whefe;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CouponActivity extends AppCompatActivity {

    List<Coupon> coupons;
    MyCouponAdapter myCouponAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon);

    /*------------------------Tool bar-----------------------*/
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);

        toolbar.setTitle("Title");
        toolbar.setTitleTextColor(Color.parseColor("#ffff33"));
        toolbar.setSubtitle("id");
        toolbar.setNavigationIcon(R.drawable.ic_menu_send);
        setSupportActionBar(toolbar);
        /*-------------------------------------------------------*/

        coupons = new ArrayList<Coupon>();

        Coupon c1 = new Coupon();
        c1.setStr1("전체 500원 할인");

        Coupon c2 = new Coupon();
        c2.setStr1("전체 1000원 할인");

        Coupon c3 = new Coupon();
        c3.setStr1("전체 700원 할인");

        coupons.add(c1);
        coupons.add(c2);
        coupons.add(c3);


        myCouponAdapter = new MyCouponAdapter(this,R.layout.coup_list,coupons);

        listView = (ListView)findViewById(R.id.coup_ListView);
        listView.setAdapter(myCouponAdapter);
    }

    public static class MyViewHolder{
        public TextView textView;
        public Button button;
    }

    public class Coupon{
        public String str1;

        public String getStr1() {
            return str1;
        }
        public void setStr1(String str1) {
            this.str1 = str1;
        }
    }

    public class MyCouponAdapter extends ArrayAdapter<Coupon> {

        //List<Coffee> coffees;
        //Context context;
        MyViewHolder holder;
        String orderInfo;
        LayoutInflater lnf;

        public MyCouponAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Coupon> coupons) {
            super(context, resource, coupons);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return coupons.size();
        }

        @Override
        public Coupon getItem(int position) {
            return coupons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {



            if(convertView==null){
                //  LayoutInflater inflater = getLayoutInflater();
                convertView = lnf.inflate(R.layout.coup_list,parent,false);

                holder = new MyViewHolder();
                holder.textView = (TextView)convertView.findViewById(R.id.coup_TextView);
                holder.button = (Button)convertView.findViewById(R.id.coup_ReceiveButton);

                holder.textView.setText(getItem(position).getStr1());

            }else{
                holder = (MyViewHolder) convertView.getTag();
            }

            Coupon coupon = new Coupon();
            coupon.setStr1(coupons.get(position).getStr1());


            return convertView;
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
