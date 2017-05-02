package com.example.chun.whefe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText idEdit;
    EditText passEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

      /*  *//*------------------------Tool bar-----------------------*//*
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);

        toolbar.setTitleTextColor(Color.parseColor("#ffff33"));
        toolbar.setSubtitle("Whefe");
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        *//*-------------------------------------------------------*/
    }

    public void onLoginButtonClicked(View v){


       Intent intent =  new Intent(MainActivity.this,MapActivity.class);


        idEdit = (EditText) findViewById(R.id.LIDEdit);
        passEdit = (EditText) findViewById(R.id.LPassEdit);


        finish();
        intent.putExtra("ID_TEXT",idEdit.getText().toString());
        intent.putExtra("Pass_TEXT",passEdit.getText().toString());

        startActivity(intent);

    }

    public void onTestButtonClicked(View v){
        Intent intent =  new Intent(MainActivity.this,OrderActivity.class);
        startActivity(intent);
    }

    public void onSignupClicked(View v){
        Toast.makeText(getApplicationContext(),"Signup 눌림",Toast.LENGTH_SHORT).show();

        Intent intent =  new Intent(MainActivity.this,SignupActivity.class);
        startActivity(intent);

    }


    public void onStart(){
        super.onStart();
        Log.i("CGY","Login Activity start");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i("CGY","Login Activity Destroy");
    }
}
