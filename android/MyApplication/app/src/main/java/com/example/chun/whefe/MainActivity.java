package com.example.chun.whefe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText LIDEdit;
    EditText LPassEdit;
    String sid, spw;
    URLConnection conn;
    OutputStreamWriter outputStreamWriter;
    BufferedReader bufferedReader;
    StringBuilder strbldr;
    String instr;

    CheckBox autoLogin;

    CheckLoginTask loginTask;

    public static final String ip = "http://223.194.153.74:8080";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        try {
            autoLoginCheck();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LIDEdit = (EditText) findViewById(R.id.LIDEdit);
        LPassEdit = (EditText) findViewById(R.id.LPassEdit);

        autoLogin = (CheckBox)findViewById(R.id.autoLogin);
        Button button = (Button) findViewById(R.id.LoginButton);

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);

                finish();

                startActivity(intent);

                return false;
            }
        });
    }

    public void autoLoginCheck() throws ExecutionException, InterruptedException {
        final int defaultValue = 0;
        SharedPreferences preferences = getSharedPreferences("LOGIN_PREFERENCE",Context.MODE_PRIVATE);
        int autoLogin = preferences.getInt("autoLogin",defaultValue);

        if(autoLogin==1){
            Log.i("autologin","자동로그인 켜짐");
            String id = preferences.getString("id","NOTFOUND");
            String pass = preferences.getString("password","NOTFOUND");

            String checkingResult = "";
            loginTask = new CheckLoginTask(this);
            checkingResult = loginTask.execute(id, pass).get();

            if (checkingResult.equals("fail")) {
                Toast.makeText(this, "자동로그인 실패", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("autoLogin",0);
                editor.putString("id", null);
                editor.putString("pass",null);

            } else if(checkingResult.equals("success")){
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                finish();
                startActivity(intent);

            } else{
                Log.e("login","nothing happened!!!!!!!!!!!!!!!!!");
            }
        }else if(autoLogin==0){
            Toast.makeText(this, "자동로그인 꺼짐", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) throws ExecutionException, InterruptedException {   //로그인 시도
        String checkingResult = "";
        loginTask = new CheckLoginTask(this);
        sid = LIDEdit.getText().toString();
        spw = LPassEdit.getText().toString();
        checkingResult = loginTask.execute(sid, spw).get();

        if (checkingResult.equals("fail")) {
            Toast.makeText(this, "아이디, 비밀번호 오류", Toast.LENGTH_SHORT).show();
            LPassEdit.setText("");
            Log.e("login", "아이디, 비밀번호 확인하세요");
        } else if(checkingResult.equals("success")){
            Log.e("login", "로그인 성공");

            LPassEdit.setText("");

            SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREFERENCE", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("id", sid);
            editor.putString("password", spw);
            if(autoLogin.isChecked()){
                editor.putInt("autoLogin",1);   // 자동로그인
            }
            else{
                editor.putInt("autoLogin",0);   // 자동로그인 X
            }
            editor.commit();

            finish();
            Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
            startActivity(intent);
        } else{
            Log.e("login","nothing happened!!!!!!!!!!!!!!!!!");
        }
    }

    class CheckLoginTask extends AsyncTask<String, Void, String> {
        String urlstr = "http://113.198.84.66/mainLogIn.php";
        URL url;
        Context context;
        String checkInfo;

        public CheckLoginTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onProgressUpdate(Void... data) {
            super.onProgressUpdate(data);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String sidset = params[0];
                String spwset = params[1];
                checkInfo = URLEncoder.encode("customer_id", "UTF-8") + "=" + URLEncoder.encode(sidset, "UTF-8");
                checkInfo += "&" + URLEncoder.encode("customer_pw", "UTF-8") + "=" + URLEncoder.encode(spwset, "UTF-8");


                url = new URL(urlstr);
                conn = url.openConnection();
                conn.setDoOutput(true);
                outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                outputStreamWriter.write(checkInfo);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                strbldr = new StringBuilder();
                instr = null;

                while ((instr = bufferedReader.readLine()) != null) {
                    strbldr.append(instr);
                    break;
                }
                bufferedReader.close();
                return strbldr.toString();
            } catch (Exception e) {
                return new String("error : " + e.getLocalizedMessage());
            }
        }
    }

   /* public void onLoginButtonClicked(View v) {
        boolean success = false;

        -------------------------로그인 체크--------------------------






        ------------------------------------------------------------
        if(success == false){   //로그인 실패
            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();



        }else if(success == true){
            Intent intent =  new Intent(MainActivity.this,NavigationActivity.class);

            LIDEdit = (EditText) findViewById(R.id.LIDEdit);
            LPassEdit = (EditText) findViewById(R.id.LPassEdit);

            finish();
            intent.putExtra("ID_TEXT",LIDEdit.getText().toString());
            intent.putExtra("Pass_TEXT",LPassEdit.getText().toString());

            startActivity(intent);

        }
    }*/


    public void onSignupClicked(View v) {
        Toast.makeText(getApplicationContext(), "Signup 눌림", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);

    }


    public void onStart() {
        super.onStart();
        Log.i("CGY", "Login Activity start");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i("CGY", "Login Activity Destroy");
    }
}
