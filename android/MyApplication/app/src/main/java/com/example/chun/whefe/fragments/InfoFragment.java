package com.example.chun.whefe.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chun.whefe.MainActivity;
import com.example.chun.whefe.NavigationActivity;
import com.example.chun.whefe.R;

import java.io.InputStream;
import java.net.URL;


/**
 * Created by Chun on 2017-05-08.
 */

public class InfoFragment extends Fragment{

    //

    Bitmap bitmap;

    String imageFilename;
    //
    LinearLayout imageList;
    View view;

    ImageView imageView1;
    ImageView imageView2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info,container,false);


       // URL url = new URL(MainActivity.ip + "/whefe/image/Notebook_SAMSUNG.jpg");
      //  task = new back();
      //  task.execute(imgUrl);



        TextView nameView = (TextView)view.findViewById(R.id.cafeNameView);
        TextView addressView = (TextView)view.findViewById(R.id.cafeAddressView);
        TextView phoneView = (TextView)view.findViewById(R.id.cafePhoneView);
        TextView timeView = (TextView)view.findViewById(R.id.cafeTimeView);
        TextView personView = (TextView)view.findViewById(R.id.cafePersonView);

        TextView introduceView = (TextView)view.findViewById(R.id.IntroducevIew);

        Button orderButton = (Button)view.findViewById(R.id.OrderButton);
        Button couponButton = (Button)view.findViewById(R.id.CouponButton);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity activity = (NavigationActivity)getActivity();
                activity.onFragmentChanged(2);
            }
        });
        couponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity activity = (NavigationActivity)getActivity();
                activity.onFragmentChanged(3);
            }
        });

        SharedPreferences preferences = getContext().getSharedPreferences("INFO_PREFERENCE",Context.MODE_PRIVATE);
        String cafeName = preferences.getString("name","NOTFOUND");
        String cafeAddress = preferences.getString("address","NOTFOUND");
        String cafePhone = preferences.getString("phone","NOTFOUND");
        String cafeOpen = preferences.getString("open","NOTFOUND");
        String cafeClose = preferences.getString("close","NOTFOUND");
        String cafePerson = preferences.getString("person","NOTFOUND");
        String cafeMax = preferences.getString("max","NOTFOUND");
        String cafeIntro = preferences.getString("intro","");
        imageFilename = preferences.getString("image","");

        // Tool bar 타이틀 설정
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(cafeName);

        nameView.setText(cafeName);
        addressView.setText("주소 : " + cafeAddress);
        phoneView.setText("전화번호 : " + cafePhone);
        timeView.setText("영업시간 : " + cafeOpen + " ~ " + cafeClose);

        double person = Double.parseDouble(cafePerson);
        double max = Double.parseDouble(cafeMax);

        double maxPerPerson = person/max*100;

        if(maxPerPerson > 65){
            personView.setBackgroundColor(Color.RED);
        }else if(maxPerPerson > 40){
            //personView.setTextColor(Color.YELLOW);
            personView.setBackgroundColor(Color.YELLOW);
        }else if(maxPerPerson >0){
            personView.setBackgroundColor(Color.GREEN);
        }
        personView.setText("혼잡도 : " + (int)person + "/" + (int)max + " ( " + maxPerPerson + "% )");

        introduceView.setText(cafeIntro);

        setImageList();


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());

                dialog.setContentView(R.layout.image_zoom_dialog);
                ImageView imageView = (ImageView)dialog.findViewById(R.id.dialog_imageView);
                ImageButton cancelButton = (ImageButton)dialog.findViewById(R.id.dialog_closeButton);

               // imageView.setImageResource(R.drawable.grazie1);
                imageView.setImageBitmap(bitmap);
                imageView.setAdjustViewBounds(true);
                cancelButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());

                dialog.setContentView(R.layout.image_zoom_dialog);
                ImageView imageView = (ImageView)dialog.findViewById(R.id.dialog_imageView);
                ImageButton cancelButton = (ImageButton)dialog.findViewById(R.id.dialog_closeButton);

                imageView.setImageResource(R.drawable.grazie2);

                cancelButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });

        return view;
    }

    public void setImageList() {
        imageList = (LinearLayout)view.findViewById(R.id.info_imageList);

        new LoadImage().execute(MainActivity.ip + "/whefe/resources/images/menuimage/americano.jpg");

        imageView1 = new ImageView(getContext());
     //   imageView1.setImageResource(R.drawable.grazie1);
       /* URL url = new URL(MainActivity.ip + "/whefe/image");
        Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());*/
      //  imageView1.setImageBitmap(bitmap);
        imageView1.setAdjustViewBounds(true);
        imageView1.setVisibility(View.VISIBLE);



        imageView2 = new ImageView(getContext());
        imageView2.setImageResource(R.drawable.grazie2);
        imageView2.setAdjustViewBounds(true);
        imageView2.setVisibility(View.VISIBLE);

        imageList.addView(imageView1);
        imageList.addView(imageView2);


    }
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if(image != null) {
                bitmap = image;
                imageView1.setImageBitmap(image);

            } else {

                Toast.makeText(getContext(), "이미지가 존재하지 않거나 네트워크 오류 발생", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
