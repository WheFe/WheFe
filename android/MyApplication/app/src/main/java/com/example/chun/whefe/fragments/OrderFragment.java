package com.example.chun.whefe.fragments;

import android.app.Dialog;
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

import com.example.chun.whefe.MainActivity;
import com.example.chun.whefe.NavigationActivity;
import com.example.chun.whefe.R;
import com.example.chun.whefe.ShoppingList;
import com.example.chun.whefe.dbhelper.MyCategoryHelper;
import com.example.chun.whefe.dbhelper.MyMenuHelper;
import com.example.chun.whefe.dbhelper.MyOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    Bitmap bitmap;

    private String cafe_id;

    Button[] categoryButton = new Button[7];


    //private ArrayList<CafeMenu>[] menuArrays = new ArrayList<CafeMenu>[7];

    private ArrayList<Menus> menuArrays = new ArrayList<Menus>();

    public class Menus{
        private ArrayList<CafeMenu> menuList = new ArrayList<CafeMenu>();

        public ArrayList<CafeMenu> getMenuList() {
            return menuList;
        }
        public void setMenuList(ArrayList<CafeMenu> menuList) {
            this.menuList = menuList;
        }
    }


    private ArrayList<CafeMenu> arrayList1 = new ArrayList<CafeMenu>();
    private HashMap<String, ArrayList<String>> arrayChild = new HashMap<String,ArrayList<String>>();



    ExpandableListView listView;
    CoffeeViewHolder holder;

    TextView priceView;

    LinearLayout categoryLayout;

    SQLiteDatabase db;
    SQLiteDatabase categoryDB;
    SQLiteDatabase menuDB;
    MyOpenHelper helper;
    MyCategoryHelper categoryHelper;
    MyMenuHelper menuHelper;


    private ArrayList<ShoppingList> sh_arrayList = new ArrayList<ShoppingList>();
    private HashMap<String, ArrayList<String>> sh_arrayChild = new HashMap<String, ArrayList<String>>();
    ExpandableListView sh_listView;
    ShoppingListAdapter sh_adapter;
    ShoppingListViewHolder sh_holder;

    ImageView menu_imageView;

    View view;

    String cafeName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.order,container,false);

        helper = new MyOpenHelper(getContext());
        db = helper.getWritableDatabase();
        categoryHelper = new MyCategoryHelper(getContext());
        categoryDB = categoryHelper.getWritableDatabase();
        menuHelper = new MyMenuHelper(getContext());
        menuDB = menuHelper.getWritableDatabase();

        SharedPreferences preferences = getContext().getSharedPreferences("INFO_PREFERENCE",Context.MODE_PRIVATE);
        cafeName = preferences.getString("name","NOTFOUND");
        cafe_id = preferences.getString("cafe_id","NOTFOUND");

        listView = (ExpandableListView)view.findViewById(R.id.menuListView);


        setCategory();

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
        int totalPrice
                =0;
        for(int i = 0; i<sh_arrayList.size();i++) {
            //   totalPrice += sh_arrayList.get(0).getPrice();
            StringTokenizer s = new StringTokenizer(sh_arrayList.get(i).getPrice());
            String temp = s.nextToken("원");
            totalPrice += Integer.parseInt(temp);
        }
        priceView.setText("결제금액 " + totalPrice + " 원");
    }
    private void setButtonClick(){

        Cursor rs = categoryDB.rawQuery("select count(*) from categorylist;", null);

        rs.moveToNext();
        for(int i = 0; i<rs.getInt(0);i++) {
            final int position = i;
            Log.i("setBUtton","position " + position + categoryButton.length);
            categoryButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Cursor rs = menuDB.rawQuery("select * from menulist;", null);

                    ArrayList<CafeMenu> tempArray = new ArrayList<CafeMenu>();
                    ArrayList<String> arrayChicken = new ArrayList<String>();
                    arrayChicken.add("a");

                    int count = 0;
                    while(rs.moveToNext()) {

                        Log.i("DB", rs.getInt(0) + rs.getString(1) + rs.getString(2));

                        String menu_name = rs.getString(0);
                        String menu_price = rs.getString(1);
                        String menu_image = rs.getString(2);
                        String menu_category = rs.getString(3);


                        if (menu_category.equals(categoryButton[position].getText())) {    // 첫번째 카테고리.
                            CafeMenu cafeMenu = new CafeMenu(menu_name, menu_price, menu_image);
                            tempArray.add(cafeMenu);    // == arrayList

                            arrayChild.put(tempArray.get(count).getName(), arrayChicken);

                            menuArrays.get(position).setMenuList(tempArray);
                            count++;
                        }
                    }


                    cafeMenuAdapter.upDateItemList(menuArrays.get(position).getMenuList(), arrayChild);
                }
            });
        }
    }
    private void setCategory(){
        categoryLayout = (LinearLayout)view.findViewById(R.id.categoryLayout);
        Log.i("CGY","setCategoryList");
        String categoryUrl = MainActivity.ip + "/whefe/android/category?cafe_id=" + cafe_id;
        new DownloadCategoryTask().execute(categoryUrl);
        Log.i("categoryTask", "tast Execute");
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
            System.out.println("onPostExecute!");
            Log.e("Json", result);
            Log.i("CGY","DownloadCategory");
            categoryDB.execSQL("delete from categorylist  ;"  );

            try {
                JSONArray ja = new JSONArray(result);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject order = ja.getJSONObject(i);
                    String category_name = (String) order.get("category_name");
                    String cafe_id = (String)order.get("cafe_id");

                    categoryDB.execSQL("insert into categorylist(category_name, cafe_id) " +
                            "values('"+ category_name + "','" + cafe_id  + "');");
                    Log.i("categoryDB", "before : " + category_name);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Cursor rs = categoryDB.rawQuery("select * from categorylist;", null);

            int count = 0;

            while(rs.moveToNext()){

                Log.i("categoryDB","count : " + rs.getCount() + " : " + rs.getString(0) + rs.getString(1));

                String category_name = rs.getString(0);
                String cafe_id_temp = rs.getString(1);

                if(cafe_id.equals(cafe_id_temp)){
                    categoryButton[count] = new Button(getContext());
                    categoryButton[count].setText(category_name);
                    Menus menus = new Menus();
                    menuArrays.add(menus);
                    categoryLayout.addView(categoryButton[count]);
                    Log.e("button[i]",categoryButton[count].getText().toString());

                    count++;
                }
            }
            setMenuList();
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

    private void setMenuList(){
        Log.i("CGY","setMenuList");


        String categoryUrl = MainActivity.ip + "/whefe/android/menu?cafe_id=" + cafe_id;
        new DownloadMenuTask().execute(categoryUrl);
    }

    private class DownloadMenuTask extends AsyncTask<String, Void, String> {                    // 메뉴 출력 Connection
        String menu_name;
        String menu_price;
        String category_name;
        String menu_image;


        @Override
        protected String doInBackground(String... urls) {
            try {
                return (String) downloadUrl((String) urls[0]);
            } catch (IOException e) {

                e.printStackTrace();
                return "다운로드 실패";
            }
        }

        // 메뉴 관리
        protected void onPostExecute(String result) {
            Log.i("CGY","DownloadMenu");
            System.out.println("onPostExecute!");
            Log.e("Json", result);
            try {
                JSONArray ja = new JSONArray(result);

                // delete all data
                menuDB.execSQL("delete from menulist  ;"  );


                for (int i = 0; i < ja.length(); i++) {
                    JSONObject obj = ja.getJSONObject(i);
                    String cafe_id_temp = obj.get("cafe_id").toString();
                    if(cafe_id.equals(cafe_id_temp)){
                        menu_name = obj.get("menu_name").toString();
                        menu_price = obj.get("menu_price").toString();
                        category_name = obj.get("category_name").toString();
                        menu_image = obj.get("menu_image").toString();
                        //arrayList3 = new ArrayList[button.length];
                    //    menu_image = "";
                        int k = 0;

                        //insert data
                        menuDB.execSQL("insert into menulist(menu_name, menu_price,menu_image, menu_category ) " +
                                "values('"+ menu_name + "','" + menu_price +"', '" + menu_image+ "', '" + category_name + "');");
                        Log.i("menuDB", "before : " + menu_name + menu_price + category_name);
                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int i= arrayList1.size()-1;i>=0;i--) {
                arrayChild.remove(arrayList1.get(i).getName());
                arrayList1.remove(i);
            }
            ArrayList<String> arrayChicken = new ArrayList<String>();
            arrayChicken.add("a");

            Cursor rs = menuDB.rawQuery("select * from menulist;", null);


            int count = 0;
            while(rs.moveToNext()) {

                Log.i("postMenuDB", rs.getString(0) + rs.getString(1) + rs.getString(2));

                String menu_name = rs.getString(0);
                String menu_price = rs.getString(1);
                String menu_image = rs.getString(2);
                String menu_category = rs.getString(3);


                if (menu_category.equals(categoryButton[0].getText())) {    // 첫번째 카테고리.

                    //arrayList1 = new ArrayList<CafeMenu>();

                    /**
                     *
                     * */

                    CafeMenu cafeMenu = new CafeMenu(menu_name, menu_price, menu_image);
                    arrayList1.add(cafeMenu);    // == arrayList

                    arrayChild.put(arrayList1.get(count).getName(), arrayChicken);

                    Log.i("menuDB", "After : " + menu_name + menu_price + menu_category);

                    menuArrays.get(0).setMenuList(arrayList1);
                    count++;
                }
            }
            cafeMenuAdapter = new CafeMenuAdapter(getContext(),menuArrays.get(0).getMenuList(),arrayChild);
//        cafeMenuAdapter = new CafeMenuAdapter(getContext(),arrayList1,arrayChild);
            listView.setAdapter(cafeMenuAdapter);


            setButtonClick();
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
            } Log.i("순서체크","3 setImageBitmap");
        }
    }

    private void setShoppingListData(){
        /*  ShoppingList(String name, String hot, String size, String option, String coupon, String price)  */
        for(int i= sh_arrayList.size()-1;i>=0;i--) {
            sh_arrayChild.remove(sh_arrayList.get(i).getName());
            sh_arrayList.remove(i);
        }
        Cursor rs = db.rawQuery("select * from shoppinglist;", null);
        while(rs.moveToNext()){
            Log.i("DB",rs.getInt(0) + rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getString(4) + rs.getString(5) + rs.getString(6));

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
            final CafeMenu cafeMenu = arrayGroup.get(groupPosition);

            String groupName = cafeMenu.getName();
            String price = cafeMenu.getPrice();
            //String groupName = arrayGroup2.get(groupPosition);
            String imageFilename = cafeMenu.getImageFilename();
            View v = convertView;

            bitmap =null;
            Log.i("순서체크","bitmap = null");
            if(v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = (RelativeLayout)inflater.inflate(R.layout.menugroup, null);
            }
            TextView textGroup = (TextView) v.findViewById(R.id.sh_nameView);
            textGroup.setText(groupName);
            TextView priceView = (TextView) v.findViewById(R.id.priceVIew);
            priceView.setText(price);
            menu_imageView = (ImageView) v.findViewById(R.id.sh_imageView);
            new LoadImage(menu_imageView,getContext()).execute(MainActivity.ip + "/whefe/resources/images/menuimage/" + imageFilename);
         //   imageView.setImageResource(R.drawable.whefe);
            //menu_imageView.setImageBitmap(bitmap);

            menu_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getContext());

                    String imageFilename = cafeMenu.getImageFilename();

                    dialog.setContentView(R.layout.image_zoom_dialog);
                    ImageView imageView = (ImageView)dialog.findViewById(R.id.dialog_imageView);
                    ImageButton cancelButton = (ImageButton)dialog.findViewById(R.id.dialog_closeButton);
                    new LoadImage(imageView,getContext()).execute(MainActivity.ip + "/whefe/resources/images/menuimage/" + imageFilename);
                //    imageView.setImageResource(R.drawable.whefe);
               //     imageView.setImageBitmap(bitmap);

                    cancelButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });

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


            /*-----------------------------------------------------------------------------------*/
            Button addButton = (Button)v.findViewById(R.id.addButton);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Spinner sizeSpinner = (Spinner)parent.findViewById(R.id.sizeSpinner);

                    Spinner optionSpinner = (Spinner)parent.findViewById(R.id.optionSpinner);
                    RadioGroup radioGroup = (RadioGroup)parent.findViewById(R.id.radioGroup);

                    String se_size = sizeSpinner.getSelectedItem().toString();
                    String se_option = optionSpinner.getSelectedItem().toString();

                    String se_name = arrayGroup.get(groupPosition).getName();
                    String se_price = arrayGroup.get(groupPosition).getPrice();

                //    int se_image = arrayGroup.get(groupPosition).getImageResource();
                    String se_image = arrayGroup.get(groupPosition).getImageFilename();

                    int radioId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton)parent.findViewById(radioId);
                    String se_hot = radioButton.getText().toString();

                    Toast.makeText(context,se_name + se_price+se_hot +se_size+ se_option   ,Toast.LENGTH_SHORT).show();

                    db.execSQL("insert into shoppinglist(name, hot, size, option,  price, image, cafe_name) " +
                            "values('"+ se_name + "','" + se_hot + "', '" + se_size + "', '" + se_option + "', '" +  se_price + "', '" +  se_image + "', '" +  cafeName + "');");

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
            final ShoppingList shoppingList = arrayGroup.get(groupPosition);

            String groupName = shoppingList.getName();
            String price = shoppingList.getPrice();
            //String groupName = arrayGroup2.get(groupPosition);
            View v = convertView;

            if(v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = (RelativeLayout)inflater.inflate(R.layout.sh_list_group, null);
            }

            String imageFilename = shoppingList.getImageFilename();
            ImageView imageView = (ImageView)v.findViewById(R.id.sh_imageView);
            new LoadImage(imageView,getContext()).execute(MainActivity.ip + "/whefe/resources/images/menuimage/" + imageFilename);
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
        String hot ;
        String size;
        String option;
        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String childName = arrayChild.get(arrayGroup.get(groupPosition).getName()).get(childPosition);

            View v = convertView;
            hot = arrayGroup.get(groupPosition).getHot();
            size = arrayGroup.get(groupPosition).getSize();
            option = arrayGroup.get(groupPosition).getOption();

            Log.i("child",hot + size + option );

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

    public static class CoffeeViewHolder{
        public RadioGroup radioGroup;
        public Spinner optionSpinner;
        public Spinner sizeSpinner;
        public ImageView menuImageView;
        public TextView menuNameTextView;
        public Button addButton;
    }

    class CafeMenu {
        private String name;
        private String price;
        private String imageFilename;

        public CafeMenu(String name, String price, String imageFilename) {
            this.name = name;
            this.price = price;
            this.imageFilename = imageFilename;
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

        public String getImageFilename() {
            return imageFilename;
        }

        public void setImageFilename(String imageFilename) {
            this.imageFilename = imageFilename;
        }
    }
}
