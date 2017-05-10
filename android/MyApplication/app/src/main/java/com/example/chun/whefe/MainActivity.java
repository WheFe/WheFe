package com.example.chun.whefe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

        Button button = (Button)findViewById(R.id.LoginButton);
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent =  new Intent(MainActivity.this,NavigationActivity.class);

                idEdit = (EditText) findViewById(R.id.LIDEdit);
                passEdit = (EditText) findViewById(R.id.LPassEdit);

                finish();
                intent.putExtra("ID_TEXT",idEdit.getText().toString());
                intent.putExtra("Pass_TEXT",passEdit.getText().toString());

                startActivity(intent);

                return false;
            }
        });

    }

    public void onLoginButtonClicked(View v){
        boolean success = false;

        /*-------------------------로그인 체크--------------------------*/






        /*------------------------------------------------------------*/
        if(success == false){   //로그인 실패
            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();



        }else if(success == true){
            Intent intent =  new Intent(MainActivity.this,NavigationActivity.class);

            idEdit = (EditText) findViewById(R.id.LIDEdit);
            passEdit = (EditText) findViewById(R.id.LPassEdit);

            finish();
            intent.putExtra("ID_TEXT",idEdit.getText().toString());
            intent.putExtra("Pass_TEXT",passEdit.getText().toString());

            startActivity(intent);

        }
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
