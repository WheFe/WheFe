package com.example.chun.whefe;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.chun.whefe.R.layout.map;

public class MapActivity extends AppCompatActivity {
        //implements NavigationView.OnNavigationItemSelectedListener{
    private static final int REQ_PERMISSION = 2000;

    private List<Fragment> mapFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(map);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID_TEXT");
        String pass = intent.getStringExtra("Pass_TEXT");
        Toast.makeText(getApplicationContext(),"result : " + id + pass,Toast.LENGTH_SHORT).show();

        /*------------------------Tool bar-----------------------*/
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        toolbar.setTitle("Title");
        toolbar.setTitleTextColor(Color.parseColor("#ffff33"));
        toolbar.setSubtitle("id");
        toolbar.setNavigationIcon(R.drawable.listing_option);
        setSupportActionBar(toolbar);
        /*-------------------------------------------------------*/

        //Permission Check
        int permissionChk = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        // if Permission DENIED
        if(permissionChk == PackageManager.PERMISSION_DENIED){

            // Request Permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQ_PERMISSION);
        }
        else{
            // if Permission GRANTED
            initViewPager();
        }

        /*------------------navigation-------------------------*/
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }

    private void initViewPager() {
        mapFragments = new ArrayList<>();
        mapFragments.add(new NaverMapFragment());

        MapFragmetsPagerAdapter pagerAdapter = new MapFragmentsPageAdapter();

        NonSwipeViewPager viewPager = (NonSwipeViewPager) findViewById(R.id.vp_maps);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ_PERMISSION & grantResults.length > 0) {
            initViewPager();
        }
    }


   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/







    public void onListButtonClicked(View v){
        Intent intent = new Intent(MapActivity.this,ListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){

                Toast.makeText(getApplicationContext(),"result : " +data.getStringExtra("ID_TEXT") + data.getStringExtra("Pass_TEXT"),Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onStart(){
        super.onStart();

        Log.i("CGY","Map Activity start");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.i("CGY","Map Activity Destroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch(curId){
            case R.id.menu_setting:
                Toast.makeText(this,"setting selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_logout:
                Toast.makeText(this,"logout selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_exit:
                Toast.makeText(this,"exit selected",Toast.LENGTH_SHORT).show();
                moveTaskToBack(true);
                finish();
                ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
                am.restartPackage(getPackageName());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
