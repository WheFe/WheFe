package com.example.chun.whefe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        TextView warningView = (TextView)findViewById(R.id.payment_warningVIew);
        warningView.setText("본 서비스는 사용자가 카페의 XXm 근처에 있으면 주문이 들어가며, 제조가 시작됩니다." +
                " 카페까지 올 때 걸리는 시간을 적지 않아 생기는 음료의 품질저하를 방지하기 위하여 시간을 적어주세요.");
    }
}
