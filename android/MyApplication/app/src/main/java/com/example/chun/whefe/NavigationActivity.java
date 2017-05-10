package com.example.chun.whefe;


import android.app.ActivityManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chun.whefe.fragments.CouponFragment;
import com.example.chun.whefe.fragments.FragmentReplaceable;
import com.example.chun.whefe.fragments.InfoFragment;
import com.example.chun.whefe.fragments.MapFragment;
import com.example.chun.whefe.fragments.OrderFragment;
import com.example.chun.whefe.fragments.PaymentFragment;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentReplaceable {

    MapFragment mapFragment;
    InfoFragment infoFragment;
    OrderFragment orderFragment;
    CouponFragment couponFragment;
    PaymentFragment paymentFragment;

    FragmentTransaction transaction ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     //   TextView nav_id_view = (TextView)findViewById(R.id.nav_id_view);
       // nav_id_view.setText("rlduf138");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       //ft = getFragmentManager().beginTransaction();


      //  mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(nav_fragment);
        mapFragment = new MapFragment();
        infoFragment = new InfoFragment();
        orderFragment = new OrderFragment();
        couponFragment = new CouponFragment();
        paymentFragment = new PaymentFragment();

        setDefaultFragment();
    }

    public void setDefaultFragment(){

        transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container,mapFragment);

        transaction.commit();
    }


    @Override
    public void onFragmentChanged(int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(index==0){       //지도
             transaction.replace(R.id.container,mapFragment);
         }else if(index==1){    // 카페 정보
            transaction.replace(R.id.container,infoFragment);
          }
        else if(index==2){  //주문화면
            transaction.replace(R.id.container,orderFragment);
        }else if(index==3){ // 쿠폰
            transaction.replace(R.id.container,couponFragment);
        }else if(index==4){ //결제 안내
            transaction.replace(R.id.container,paymentFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_settings) {

            return true;
        }else if(id==R.id.menu_logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(NavigationActivity.this);

            builder.setMessage("로그아웃 하시겠습니까?");
            builder.setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setTitle("로그아웃");
            dialog.show();

            return true;
        }else if(id==R.id.menu_exit){
            AlertDialog.Builder builder = new AlertDialog.Builder(NavigationActivity.this);

            builder.setMessage("종료하시겠습니까?");
            builder.setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);
                            finish();
                            ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
                            am.restartPackage(getPackageName());
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.setTitle("종료");

            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            this.onFragmentChanged(1);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
