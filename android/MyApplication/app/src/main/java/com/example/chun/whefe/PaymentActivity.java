package com.example.chun.whefe;

import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    List<ShoppingList> shoppingLists;
    ShoppingList shoppingList;
   // MyShoppingListAdapter myShoppingListAdapter;
    ListView sh_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        /*------------------------Tool bar-----------------------*/
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);

        toolbar.setTitle("Title");
        toolbar.setTitleTextColor(Color.parseColor("#ffff33"));
        toolbar.setSubtitle("id");
        toolbar.setNavigationIcon(R.drawable.listing_option);
        setSupportActionBar(toolbar);
        /*-------------------------------------------------------*/

        TextView warningView = (TextView)findViewById(R.id.payment_warningVIew);
        warningView.setText("본 서비스는 사용자가 카페의 XXm 근처에 있으면 주문이 들어가며, 제조가 시작됩니다." +
                " 음료 수령시간이 늦어져 생기는 품질저하는 책임질 수 없으니, 주의하여 주시기 바랍니다.");

        shoppingLists = new ArrayList<ShoppingList>();

        ShoppingList s1 = new ShoppingList("아메리카노","Iced","medium","샷추가+500","No Coupon","2300");
        ShoppingList s2 = new ShoppingList("카페모카","Hot", "small","","-500","3500");
        ShoppingList s3 = new ShoppingList("아메리카노", "Hot", "Large", "연하게+0","No Coupon","1800");

        shoppingLists.add(s1);
        shoppingLists.add(s2);
        shoppingLists.add(s3);

       /* myShoppingListAdapter = new MyShoppingListAdapter(this,R.layout.sh_list_group,shoppingLists);

        sh_listView = (ListView)findViewById(R.id.payment_orderListView);
        sh_listView.setAdapter(myShoppingListAdapter);*/

        TextView textView = (TextView)findViewById(R.id.pay_priceView);
        textView.setText("가격 : 7600 원");
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
