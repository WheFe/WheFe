package com.example.chun.whefe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    int[] images = {R.drawable.grazie1,R.drawable.grazie2};
    String[] menunames = {"아메리카노", "카페라떼"};

    List<Coffee> coffees;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        coffees = new ArrayList<Coffee>();

        Coffee c1 = new Coffee();
        c1.setImage(R.drawable.grazie1);
        c1.setMenuname("아메리카노");

        Coffee c2 = new Coffee();
        c2.setImage(R.drawable.grazie2);
        c2.setMenuname("카페라떼");

        coffees.add(c1);
        coffees.add(c2);



        MyAdapter myAdapter = new MyAdapter(this,R.layout.cafemenu,coffees);
        ListView listView = (ListView)findViewById(R.id.TabListView);
        listView.setAdapter(myAdapter);

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

    public class MyAdapter extends ArrayAdapter<Coffee> {
        /*Context context;
        int[] images;
        String[] menunames;*/

        List<Coffee> coffees;

        /*public MyAdapter(Context context,int[] images, String[] menunames){
            this.context = context;
            this.images = images;
            this.menunames = menunames;
        }*/

        Context context;

        public MyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Coffee> coffees) {
            super(context, resource, coffees);
            this.context = context;
            this.coffees = coffees;
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
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.cafemenu,parent,false);
            }


            ImageView menuImage = (ImageView)convertView.findViewById(R.id.MenuImage);
            TextView menuName = (TextView)convertView.findViewById(R.id.MenuNameView);

            menuImage.setImageResource(images[position]);
            menuName.setText(menunames[position]);



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

}
