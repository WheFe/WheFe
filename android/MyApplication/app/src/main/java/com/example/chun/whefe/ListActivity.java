package com.example.chun.whefe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
    }

    public void onListClicked(View v){
        //Toast.makeText(getApplicationContext(),"Image Clicked",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ListActivity.this,InfoActivity.class);
        startActivity(intent);
    }

    public void onStart(){
        super.onStart();
        Log.i("CGY","Cafe List Activity start");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i("CGY","Cafe List Activity Destroy");
    }
}
