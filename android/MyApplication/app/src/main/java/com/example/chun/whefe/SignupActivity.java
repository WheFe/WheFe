package com.example.chun.whefe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        setContentView(R.layout.signup);
    }

    public void onStart(){
        super.onStart();
        Log.i("CGY","Sign up start");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i("CGY","Sign up Activity Destroy");
    }
}
