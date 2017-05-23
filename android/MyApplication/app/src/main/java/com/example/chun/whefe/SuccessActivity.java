package com.example.chun.whefe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class SuccessActivity extends Activity {

    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.success);

        ImageView phoneView =(ImageView) findViewById(R.id.noti_phoneView);
        ImageView iconView =(ImageView) findViewById(R.id.noti_iconView);

        phoneView.setImageResource(R.drawable.smartphone);
        iconView.setImageResource(R.drawable.whefe);

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{1000,1000},0);

    }

    public void onOKButtonClicked(View v){
        vibrator.cancel();
        finish();
    }
}
