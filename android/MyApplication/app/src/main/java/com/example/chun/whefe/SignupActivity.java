package com.example.chun.whefe;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class SignupActivity extends AppCompatActivity {

    EditText LIDEdit, LPassEdit, NameEdit, PhoneEdit, PassEdit2;    //PassEdit2 : 비밀번호 확인
    Button GoButton, IdCheckButton;
    URLConnection conn;
    OutputStreamWriter outputStreamWriter;
    BufferedReader bufferedReader;
    String mode, sid, spw, sname, sphone, ecdmsg, instr;
    DbTask dbTask;
    CheckIdTask checkIdTask;
    StringBuilder strbldr;

    String pass, passCheck; //비번, 비번확인


    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        setContentView(R.layout.signup);

        LIDEdit = (EditText) findViewById(R.id.LIDEdit);
        LPassEdit = (EditText) findViewById(R.id.LPassEdit);
        NameEdit = (EditText) findViewById(R.id.NameEdit);
        PhoneEdit = (EditText) findViewById(R.id.PhoneEdit);
        GoButton = (Button) findViewById(R.id.GoButton);
        IdCheckButton = (Button) findViewById(R.id.IdCheckButton);
       // GoButton.setOnClickListener(this);
        //IdCheckButton.setOnClickListener();

        PassEdit2 = (EditText) findViewById(R.id.PassEdit2); //비번확인


    }

    private AlertDialog createDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("비밀번호 확인");
        builder.setMessage("비밀번호 일치여부를 확인하세요");

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //LPassEdit.requestFocus();
                Log.i("pass", "올바른 비밀번호를 입력하세요");
            }
        });
        AlertDialog dialog = builder.create();

        return dialog;
    }

    public void onClick(View v) {
        pass = LPassEdit.getText().toString();
        passCheck = PassEdit2.getText().toString();

        if (pass == passCheck) {     //비밀번호와 비밀번호확인칸이 동일하면 DB에 회원정보 입력수행
            mode = "i";
            sid = LIDEdit.getText().toString();
            spw = LPassEdit.getText().toString();
            sname = NameEdit.getText().toString();
            sphone = PhoneEdit.getText().toString();

            dbTask = new DbTask(this);
            dbTask.execute(mode, sid, spw, sname, sphone);
        } else {
            dialog = createDialogBox();
            dialog.setTitle("회원가입 오류");
            dialog.show();
        }
    }

    public void onSameIdClick(View v) throws ExecutionException, InterruptedException {  //아이디 중복체크 버튼 클릭
        String checkingResult;
        checkIdTask = new CheckIdTask(this);
        sid = LIDEdit.getText().toString();
        checkingResult = checkIdTask.execute(sid).get();
        Log.e("id", checkingResult);

        if(checkingResult != ""){
            Log.e("id","이미 있는 아이디");
        } else{
            Log.e("id","사용할 수 있는 아이디");
        }
    }

    class CheckIdTask extends AsyncTask<String, Void, String> {
        String urlstr = "http://223.194.155.51/sameIdCheck.php";
        URL url;
        Context context;
        String checkId;

        public CheckIdTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onProgressUpdate(Void... data) {
            super.onProgressUpdate(data);
        }

        @Override
        protected String doInBackground(String... params){

            try{
                String sidset = params[0];
                checkId = URLEncoder.encode("customer_id", "UTF-8") + "=" + URLEncoder.encode(sidset, "UTF-8");


                url = new URL(urlstr);
                conn = url.openConnection();
                conn.setDoOutput(true);
                outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                outputStreamWriter.write(checkId);
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
            }catch(Exception e){
                return new String("error : " + e.getLocalizedMessage());
            }
        }
    }

    class DbTask extends AsyncTask<String, Void, String> {
        String urlstr = "http://223.194.155.51/signUp.php";
        URL url;
        Context context;

        public DbTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onProgressUpdate(Void... data) {
            super.onProgressUpdate(data);
        }


        @Override
        protected String doInBackground(String... params) {
            mode = (String) params[0];
            sid = (String) params[1];
            spw = (String) params[2];
            sname = (String) params[3];
            sphone = (String) params[4];

            try {
                ecdmsg = URLEncoder.encode("mode", "UTF-8") + "=" + URLEncoder.encode(mode, "UTF-8");
                ecdmsg += "&" + URLEncoder.encode("customer_id", "UTF-8") + "=" + URLEncoder.encode(sid, "UTF-8");
                ecdmsg += "&" + URLEncoder.encode("customer_pw", "UTF-8") + "=" + URLEncoder.encode(spw, "UTF-8");
                ecdmsg += "&" + URLEncoder.encode("customer_name", "UTF-8") + "=" + URLEncoder.encode(spw, "UTF-8");
                ecdmsg += "&" + URLEncoder.encode("customer_phone", "UTF-8") + "=" + URLEncoder.encode(spw, "UTF-8");

                url = new URL(urlstr);
                conn = url.openConnection();
                conn.setDoOutput(true);
                outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
                outputStreamWriter.write(ecdmsg);
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

    public void onStart() {
        super.onStart();
        Log.i("CGY", "Sign up start");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i("CGY", "Sign up Activity Destroy");
    }
}
