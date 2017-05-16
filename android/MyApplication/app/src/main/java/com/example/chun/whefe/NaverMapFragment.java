package com.example.chun.whefe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chun.whefe.nmap.NMapFragment;
import com.example.chun.whefe.nmap.NMapPOIflagType;
import com.example.chun.whefe.nmap.NMapViewerResourceProvider;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import java.util.ArrayList;
import java.util.List;


public class NaverMapFragment extends NMapFragment implements NMapView.OnMapStateChangeListener {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "NMapViewer";
    private static final String NAVER_CLIENT_ID = "fpin69EB6CPM5ATQLpgI";

    NMapView nMapView;
    NMapController nMapController;
    NMapLocationManager mMapLocationManager;
    NMapCompassManager mMapCompassManager;
    NMapMyLocationOverlay mMyLocationOverlay;
    private MapContainerView mMapContainerView;

    private List<CafeInfo> cafeInfos;

    View baseView;

    NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = null;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        baseView = inflater.inflate(R.layout.fragment_naver_map, container, false);
        nMapView = (NMapView) baseView.findViewById(R.id.nv_mapView);

        nMapView.setClientId(NAVER_CLIENT_ID);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Whefe");
        //NMapActivity.setMapDataProviderListener(onDataProviderListener);


        // Inflate the layout for this fragment
        return baseView;

    }
    @Override
    public void onStart() {
        super.onStart();
        nMapView.setBuiltInZoomControls(true, null);
        nMapView.setOnMapStateChangeListener(this);
        nMapView.setFocusable(true);
        nMapView.setFocusableInTouchMode(true);
        nMapView.setClickable(true);
        nMapView.requestFocus();
        nMapController = nMapView.getMapController();

        mMapViewerResourceProvider = new NMapViewerResourceProvider(getContext());
        mOverlayManager = new NMapOverlayManager(getContext(), nMapView,mMapViewerResourceProvider);

        startMyLocation();

        int markerId = NMapPOIflagType.PIN;


        /**
         *  카페 정보 넣기
         */
        NMapPOIdata poIdata= new NMapPOIdata(2, mMapViewerResourceProvider);

        setPOIData(poIdata,markerId);

        NMapPOIdataOverlay poidataOverlay = mOverlayManager.createPOIdataOverlay(poIdata,null);

        poidataOverlay.setOnStateChangeListener(new NMapPOIdataOverlay.OnStateChangeListener() {
            @Override
            public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
                if (nMapPOIitem != null) {
                    Log.i(LOG_TAG, "onFocusChanged: " + nMapPOIitem.toString());
                } else {
                    Log.i(LOG_TAG, "onFocusChanged: ");
                }
            }

            @Override
            public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {
                RelativeLayout relativeLayout = (RelativeLayout)getView().findViewById(R.id.nmap_info);
                relativeLayout.setVisibility(View.VISIBLE);

                int i = 0;
                for(i = 0; i<cafeInfos.size();i++){
                    if(nMapPOIitem.getTitle().equals(cafeInfos.get(i).getCafeName())){
                        break;
                    }
                }

                TextView nameView = (TextView)getView().findViewById(R.id.cafeInfoNameView);
                TextView addressView = (TextView)getView().findViewById(R.id.cafeInfoAddressView);
                TextView phoneView = (TextView)getView().findViewById(R.id.cafeInfoPhoneView);
                TextView timeView = (TextView)getView().findViewById(R.id.cafeInfoTimeView);
                TextView personView = (TextView)getView().findViewById(R.id.cafeInfoPersonView);
                Button button = (Button)getView().findViewById(R.id.cafeInfoButton);

                nameView.setText(cafeInfos.get(i).getCafeName());
                addressView.setText("주소 : " + cafeInfos.get(i).getCafeAddress());
                phoneView.setText("전화번호 : " + cafeInfos.get(i).getCafePhone());
                timeView.setText("영업시간 : " + cafeInfos.get(i).getCafeOpen() + " ~ " + cafeInfos.get(i).getCafeClose());
                String temp = "13";
                personView.setText("혼잡도 : " + temp + " / " + cafeInfos.get(i).getCafeMaximum());

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationActivity activity = (NavigationActivity)getActivity();
                        stopMyLocation();
                        activity.onFragmentChanged(1);
                    }
                });

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("INFO_PREFERENCE", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("name", cafeInfos.get(i).getCafeName());
                editor.putString("address", cafeInfos.get(i).getCafeAddress());
                editor.putString("phone", cafeInfos.get(i).getCafePhone());
                editor.putString("open", cafeInfos.get(i).getCafeOpen());
                editor.putString("close",cafeInfos.get(i).getCafeClose());
                editor.putString("person", cafeInfos.get(i).getCafeMaximum());
                editor.commit();
            }
        });
        mMapLocationManager = new NMapLocationManager(getContext());
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
        mMapLocationManager.enableMyLocation(false);
        mMapCompassManager = new NMapCompassManager(getActivity());
        //poIdataOverlay.showAllPOIdata(0);
        mMyLocationOverlay =  mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);
    }
    public NMapPOIdata setPOIData(NMapPOIdata poiData, int markerId){

        cafeInfos = new ArrayList<CafeInfo>();

        poiData.beginPOIdata(2);

        String CafeName = "그라지에 미래관";
        String CafeAddress = "서울특별시 성북구 삼선동2가 389 한성대학교 미래관 B1층";
        String CafePhone = "02-111-1111";
        String CafeOpen = "08:30";
        String CafeClose = "21:00";
        String CafeMaximum = "20";

        CafeInfo cafeInfo = new CafeInfo(CafeName,CafeAddress,CafePhone,CafeOpen,CafeClose,CafeMaximum);

        cafeInfos.add(cafeInfo);

        String info = cafeInfo.getCafeName();

        poiData.addPOIitem(127.010741, 37.582590, info, markerId, 0);

        CafeName = "그라지에 연구관";
        CafeAddress = "서울특별시 성북구 삼선동2가 389 한성대학교 연구관 2층";
        CafePhone = "02-222-2222";
        CafeOpen = "08:30";
        CafeClose = "21:00";
        CafeMaximum = "100";

        cafeInfo = new CafeInfo(CafeName,CafeAddress,CafePhone,CafeOpen,CafeClose,CafeMaximum);

        cafeInfos.add(cafeInfo);

        info = cafeInfo.getCafeName();

        poiData.addPOIitem(127.009844, 37.582323, info, markerId, 0);

        CafeName = "팥고당";
        CafeAddress = "서울특별시 성북구 삼선동2가 389 한성대학교 상상관 2층";
        CafePhone = "02-333-3333";
        CafeOpen = "08:30";
        CafeClose = "21:00";
        CafeMaximum = "20";

        cafeInfo = new CafeInfo(CafeName,CafeAddress,CafePhone,CafeOpen,CafeClose,CafeMaximum);

        cafeInfos.add(cafeInfo);

        info = cafeInfo.getCafeName();

        poiData.addPOIitem(127.010010, 37.582562, info, markerId, 0);

        poiData.endPOIdata();

        return poiData;
    }
    /* MyLocation Listener */
    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {

            if (nMapController != null) {
                nMapController.animateTo(myLocation);
            }

            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {}

        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {}

    };
    private void startMyLocation() {

        if (mMyLocationOverlay != null) {
            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                mOverlayManager.addOverlay(mMyLocationOverlay);
            }

            if (mMapLocationManager.isMyLocationEnabled()) {

                if (!nMapView.isAutoRotateEnabled()) {
                    mMyLocationOverlay.setCompassHeadingVisible(true);

                    mMapCompassManager.enableCompass();

                    nMapView.setAutoRotateEnabled(true, false);

                    mMapContainerView.requestLayout();
                } else {
                    stopMyLocation();
                }

                nMapView.postInvalidate();
            } else {
                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    Toast.makeText(getContext(), "Please enable a My Location source in system settings",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);

                    return;
                }
            }
        }
    }
    private void stopMyLocation() {
        if (mMyLocationOverlay != null) {
            mMapLocationManager.disableMyLocation();

            if (nMapView.isAutoRotateEnabled()) {
                mMyLocationOverlay.setCompassHeadingVisible(false);

                mMapCompassManager.disableCompass();

                nMapView.setAutoRotateEnabled(false, false);

                mMapContainerView.requestLayout();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("CGY","stopMyLocation");
        stopMyLocation();
    }

    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
        if(nMapError == null){
            nMapController.setZoomLevel(20);
            //startLocationService();
            //NGeoPoint point = new NGeoPoint(126.97362, 37.57570);
           // nMapController.setMapCenter(point,20);
        }

    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }
    Double newLatitude;
    Double newLongitude;
    LocationManager manager;

    private void startLocationService() {
        manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        // 위치 정보를 받을 리스너 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        try {
            // GPS를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0,
                    gpsListener);

            // 네트워크를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0,
                    0,
                    gpsListener);
            /*manager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);*/


            // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                newLatitude = lastLocation.getLatitude();
                newLongitude = lastLocation.getLongitude();
            }
            //nMapController.setMapCenter(new NGeoPoint(lastLocation.getLongitude(),lastLocation.getLatitude()),11);


        } catch(SecurityException ex) {
            ex.printStackTrace();
        }

        // Toast.makeText(getContext(), "위치 확인이 시작되었습니다. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }

    class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            //if(count ==1)

            newLatitude = location.getLatitude();
            newLongitude = location.getLongitude();

            // 맵 중앙 설정 및 애니메이션 효과
            nMapController.animateTo(new NGeoPoint(newLongitude,newLatitude));
            //mMapViewerResourceProvider.getLocationDot();
            String msg = "Latitude : " + newLatitude + "\nLongitude : " + newLongitude;
            Log.i("GPSListener", msg);
            Toast.makeText(getContext(), msg , Toast.LENGTH_SHORT).show();

            manager.removeUpdates(this);    // 업데이트 중지
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private class MapContainerView extends ViewGroup {

        public MapContainerView(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            final int width = getWidth();
            final int height = getHeight();
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);
                final int childWidth = view.getMeasuredWidth();
                final int childHeight = view.getMeasuredHeight();
                final int childLeft = (width - childWidth) / 2;
                final int childTop = (height - childHeight) / 2;
                view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }

            if (changed) {
                mOverlayManager.onSizeChanged(width, height);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            int h = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            int sizeSpecWidth = widthMeasureSpec;
            int sizeSpecHeight = heightMeasureSpec;

            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);

                if (view instanceof NMapView) {
                    if (nMapView.isAutoRotateEnabled()) {
                        int diag = (((int)(Math.sqrt(w * w + h * h)) + 1) / 2 * 2);
                        sizeSpecWidth = MeasureSpec.makeMeasureSpec(diag, MeasureSpec.EXACTLY);
                        sizeSpecHeight = sizeSpecWidth;
                    }
                }

                view.measure(sizeSpecWidth, sizeSpecHeight);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
