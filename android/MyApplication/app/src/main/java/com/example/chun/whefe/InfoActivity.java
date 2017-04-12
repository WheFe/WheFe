package com.example.chun.whefe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        TextView infoView = (TextView)findViewById(R.id.infoView);
        TextView introduceView = (TextView)findViewById(R.id.IntroducevIew);

        infoView.setText("주소 : 한성대학교\n영업 시간 : 08 : 00 ~ 22 : 00\n혼잡도 : 13% (13/100)\n" +
                "(손님/좌석 수 *100)\n혼잡도는 카페에 있는 인원을 기준으로 계산한 것이며 다른 고객의 앉는 방식에 따라 차이가 있을 수 있습니다.");
        introduceView.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eget risus ullamcorper, viverra augue in," +
                " imperdiet nulla. Aliquam accumsan varius accumsan. Aenean cursus mi vitae porta accumsan. Sed pretium faucibus accumsan." +
                " Nullam feugiat tortor non pharetra auctor. Proin augue erat, tincidunt sed iaculis quis, porta ac justo." +
                " Nam ornare nunc at condimentum scelerisque. Maecenas finibus urna in sapien sagittis, et accumsan ex blandit." +
                " Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Praesent et leo vitae" +
                " sapien sollicitudin bibendum. ");
    }

    public void onOrderButtonClicked(View v){
        Intent intent =  new Intent(InfoActivity.this,OrderActivity.class);
        startActivity(intent);
    }

    public void onStart(){
        super.onStart();
        Log.i("CGY","Info Activity start");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i("CGY","Info Activity Destroy");
    }
}
