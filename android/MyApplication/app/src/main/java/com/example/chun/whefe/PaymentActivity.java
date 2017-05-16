package com.example.chun.whefe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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
    }
}
