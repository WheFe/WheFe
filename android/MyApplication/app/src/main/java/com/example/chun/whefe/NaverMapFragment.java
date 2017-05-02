package com.example.chun.whefe;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chun.whefe.nmap.NMapFragment;
import com.example.chun.whefe.nmap.NMapPOIflagType;
import com.example.chun.whefe.nmap.NMapViewerResourceProvider;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;


public class NaverMapFragment extends NMapFragment implements NMapView.OnMapStateChangeListener {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "NMapViewer";
    private static final String NAVER_CLIENT_ID = "fpin69EB6CPM5ATQLpgI";

   /* private MapContainerView mMapContainerView;
    private SharedPreferences mPreferences;

   // private NMapOverlayManager mOverlayManager;

    private NMapMyLocationOverlay mMyLocationOverlay;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;

   // private NMapViewerResourceProvider mMapViewerResourceProvider;

    private NMapPOIdataOverlay mFloatingPOIdataOverlay;
    private NMapPOIitem mFloatingPOIitem;




    public NaverMapFragment() {
        // Required empty public constructor
    }

    private NMapController nMapController;
    private NMapView nMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_naver_map, container, false);
        nMapView = (NMapView) baseView.findViewById(R.id.nv_mapView);

        nMapView.setClientId(NAVER_CLIENT_ID);
        nMapView.setClickable(true);
        //NMapActivity.setMapDataProviderListener(onDataProviderListener);


        // Inflate the layout for this fragment
        return baseView;
    }

    private NMapOverlayManager mOverlayManager;
    private NMapResourceProvider mMapViewerResourceProvider;

    @Override
    public void onStart() {
        super.onStart();
        nMapView.setBuiltInZoomControls(true, null);
        nMapView.setOnMapStateChangeListener(this);
        nMapView.setFocusable(true);
        nMapView.setFocusableInTouchMode(true);
        nMapView.requestFocus();
        nMapController = nMapView.getMapController();



    }

    Double newLatitude;
    Double newLongitude;

    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
        if (nMapError == null) {  // success

            // 축척 레벨 최대(14)설정
            nMapController.setZoomLevel(14);
            startLocationService();

            Log.i("GPSListener", newLatitude + "\n" + newLongitude);


            NMapViewerResourceProvider mMapViewerResourceProvider = new NMapViewerResourceProvider(getContext());
            NMapOverlayManager mOverlayManager = new NMapOverlayManager(getContext(), nMapView, mMapViewerResourceProvider);

            int markerId = NMapPOIflagType.PIN;

            // set POI data
            NMapPOIdata poiData = new NMapPOIdata(2, null);
            poiData.beginPOIdata(2);
            poiData.addPOIitem(127.0630205, 37.5091300, "Pizza 777-111", markerId, 0);
            poiData.addPOIitem(127.061, 37.51, "Pizza 123-456", markerId, 0);
            poiData.endPOIdata();

            // create resource provider


            // create POI data
            NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData,null);
         //   poiDataOverlay.showAllPOIdata(0);
            poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

        } else {
            Log.e("NMapView", "onMapInitHandler : error = " + nMapError.toString());
        }
    }

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

    private void testPathDataOverlay() {

        // set path data points
        NMapPathData pathData = new NMapPathData(9);

        pathData.initPathData();
        pathData.addPathPoint(127.108099, 37.366034, NMapPathLineStyle.TYPE_SOLID);
        pathData.addPathPoint(127.108088, 37.366043, 0);
        pathData.addPathPoint(127.108079, 37.365619, 0);
        pathData.addPathPoint(127.107458, 37.365608, 0);
        pathData.addPathPoint(127.107232, 37.365608, 0);
        pathData.addPathPoint(127.106904, 37.365624, 0);
        pathData.addPathPoint(127.105933, 37.365621, NMapPathLineStyle.TYPE_DASH);
        pathData.addPathPoint(127.105929, 37.366378, 0);
        pathData.addPathPoint(127.106279, 37.366380, 0);
        pathData.endPathData();

        NMapPathDataOverlay pathDataOverlay = mOverlayManager.createPathDataOverlay(pathData);
        if (pathDataOverlay != null) {

            // add path data with polygon type
            NMapPathData pathData2 = new NMapPathData(4);
            pathData2.initPathData();
            pathData2.addPathPoint(127.106, 37.367, NMapPathLineStyle.TYPE_SOLID);
            pathData2.addPathPoint(127.107, 37.367, 0);
            pathData2.addPathPoint(127.107, 37.368, 0);
            pathData2.addPathPoint(127.106, 37.368, 0);
            pathData2.endPathData();
            pathDataOverlay.addPathData(pathData2);
            // set path line style
            NMapPathLineStyle pathLineStyle = new NMapPathLineStyle(nMapView.getContext());
            pathLineStyle.setPataDataType(NMapPathLineStyle.DATA_TYPE_POLYGON);
            pathLineStyle.setLineColor(0xA04DD2, 0xff);
            pathLineStyle.setFillColor(0xFFFFFF, 0x00);
            pathData2.setPathLineStyle(pathLineStyle);

            // add circle data
            NMapCircleData circleData = new NMapCircleData(1);
            circleData.initCircleData();
            circleData.addCirclePoint(127.1075, 37.3675, 50.0F);
            circleData.endCircleData();
            pathDataOverlay.addCircleData(circleData);
            // set circle style
            NMapCircleStyle circleStyle = new NMapCircleStyle(nMapView.getContext());
            circleStyle.setLineType(NMapPathLineStyle.TYPE_DASH);
            circleStyle.setFillColor(0x000000, 0x00);
            circleData.setCircleStyle(circleStyle);

            // show all path data
            pathDataOverlay.showAllPathData(0);
        }
    }

    private void testPathPOIdataOverlay() {

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(4, mMapViewerResourceProvider, true);
        poiData.beginPOIdata(4);
        poiData.addPOIitem(349652983, 149297368, "Pizza 124-456", NMapPOIflagType.FROM, null);
        poiData.addPOIitem(349652966, 149296906, null, NMapPOIflagType.NUMBER_BASE + 1, null);
        poiData.addPOIitem(349651062, 149296913, null, NMapPOIflagType.NUMBER_BASE + 999, null);
        poiData.addPOIitem(349651376, 149297750, "Pizza 000-999", NMapPOIflagType.TO, null);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

        // set event listener to the overlay
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

    }

    private void testPOIdataOverlay() {

        // Markers for POI item
        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, mMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        NMapPOIitem item = poiData.addPOIitem(127.0630205, 37.5091300, "Pizza 777-111", markerId, 0);
        item.setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW);
        poiData.addPOIitem(127.061, 37.51, "Pizza 123-456", markerId, 0);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

        // set event listener to the overlay
        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

        // select an item
        poiDataOverlay.selectPOIitem(0, true);

        // show all POI data
        //poiDataOverlay.showAllPOIdata(0);
    }

    private void testFloatingPOIdataOverlay() {
        // Markers for POI item
        int marker1 = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
        poiData.beginPOIdata(1);
        NMapPOIitem item = poiData.addPOIitem(null, "Touch & Drag to Move", marker1, 0);
        if (item != null) {
            // initialize location to the center of the map view.
            item.setPoint(nMapController.getMapCenter());
            // set floating mode
            item.setFloatingMode(NMapPOIitem.FLOATING_TOUCH | NMapPOIitem.FLOATING_DRAG);
            // show right button on callout
            item.setRightButton(true);

            mFloatingPOIitem = item;
        }
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        if (poiDataOverlay != null) {
            poiDataOverlay.setOnFloatingItemChangeListener(onPOIdataFloatingItemChangeListener);

            // set event listener to the overlay
            poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

            poiDataOverlay.selectPOIitem(0, false);

            mFloatingPOIdataOverlay = poiDataOverlay;
        }
    }

    *//* NMapDataProvider Listener *//*
    private final NMapActivity.OnDataProviderListener onDataProviderListener = new NMapActivity.OnDataProviderListener() {

        @Override
        public void onReverseGeocoderResponse(NMapPlacemark placeMark, NMapError errInfo) {

            if (DEBUG) {
                Log.i(LOG_TAG, "onReverseGeocoderResponse: placeMark="
                        + ((placeMark != null) ? placeMark.toString() : null));
            }

            if (errInfo != null) {
                Log.e(LOG_TAG, "Failed to findPlacemarkAtLocation: error=" + errInfo.toString());

                Toast.makeText(getContext(), errInfo.toString(), Toast.LENGTH_LONG).show();
                return;
            }

            if (mFloatingPOIitem != null && mFloatingPOIdataOverlay != null) {
                mFloatingPOIdataOverlay.deselectFocusedPOIitem();

                if (placeMark != null) {
                    mFloatingPOIitem.setTitle(placeMark.toString());
                }
                mFloatingPOIdataOverlay.selectPOIitemBy(mFloatingPOIitem.getId(), false);
            }
        }

    };

    *//* MyLocation Listener *//*
    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {

            if (nMapController != null) {
                nMapController.animateTo(myLocation);
            }

            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

            // stop location updating
            //			Runnable runnable = new Runnable() {
            //				public void run() {
            //					stopMyLocation();
            //				}
            //			};
            //			runnable.run();

            Toast.makeText(getContext(), "Your current location is temporarily unavailable.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {

            Toast.makeText(getContext(), "Your current location is unavailable area.", Toast.LENGTH_LONG).show();

            stopMyLocation();
        }

    };

    *//* MapView State Change Listener*//*
    private final NMapView.OnMapStateChangeListener onMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {

        @Override
        public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {

            if (errorInfo == null) { // success
                // restore map view state such as map center position and zoom level.
             //   restoreInstanceState();

            } else { // fail
                Log.e(LOG_TAG, "onFailedToInitializeWithError: " + errorInfo.toString());

                Toast.makeText(getContext(), errorInfo.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onAnimationStateChange(NMapView mapView, int animType, int animState) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onAnimationStateChange: animType=" + animType + ", animState=" + animState);
            }
        }

        @Override
        public void onMapCenterChange(NMapView mapView, NGeoPoint center) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onMapCenterChange: center=" + center.toString());
            }
        }

        @Override
        public void onZoomLevelChange(NMapView mapView, int level) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onZoomLevelChange: level=" + level);
            }
        }

        @Override
        public void onMapCenterChangeFine(NMapView mapView) {

        }
    };

    private final NMapView.OnMapViewTouchEventListener onMapViewTouchEventListener = new NMapView.OnMapViewTouchEventListener() {

        @Override
        public void onLongPress(NMapView mapView, MotionEvent ev) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLongPressCanceled(NMapView mapView) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSingleTapUp(NMapView mapView, MotionEvent ev) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTouchDown(NMapView mapView, MotionEvent ev) {

        }

        @Override
        public void onScroll(NMapView mapView, MotionEvent e1, MotionEvent e2) {
        }

        @Override
        public void onTouchUp(NMapView mapView, MotionEvent ev) {
            // TODO Auto-generated method stub

        }

    };

    private final NMapView.OnMapViewDelegate onMapViewTouchDelegate = new NMapView.OnMapViewDelegate() {

        @Override
        public boolean isLocationTracking() {
            if (mMapLocationManager != null) {
                if (mMapLocationManager.isMyLocationEnabled()) {
                    return mMapLocationManager.isMyLocationFixed();
                }
            }
            return false;
        }

    };

    *//* POI data State Change Listener*//*
    private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {

        @Override
        public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            if (DEBUG) {
                Log.i(LOG_TAG, "onCalloutClick: title=" + item.getTitle());
            }

            // [[TEMP]] handle a click event of the callout
            Toast.makeText(getContext(), "onCalloutClick: " + item.getTitle(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            if (DEBUG) {
                if (item != null) {
                    Log.i(LOG_TAG, "onFocusChanged: " + item.toString());
                } else {
                    Log.i(LOG_TAG, "onFocusChanged: ");
                }
            }
        }
    };

    private final NMapPOIdataOverlay.OnFloatingItemChangeListener onPOIdataFloatingItemChangeListener = new NMapPOIdataOverlay.OnFloatingItemChangeListener() {

        @Override
        public void onPointChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            NGeoPoint point = item.getPoint();

            if (DEBUG) {
                Log.i(LOG_TAG, "onPointChanged: point=" + point.toString());
            }

         //   findPlacemarkAtLocation(point.longitude, point.latitude);

            item.setTitle(null);

        }
    };

    private final NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener = new NMapOverlayManager.OnCalloutOverlayListener() {

        @Override
        public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay, NMapOverlayItem overlayItem,
                                                         Rect itemBounds) {

            // handle overlapped items
            if (itemOverlay instanceof NMapPOIdataOverlay) {
                NMapPOIdataOverlay poiDataOverlay = (NMapPOIdataOverlay)itemOverlay;

                // check if it is selected by touch event
                if (!poiDataOverlay.isFocusedBySelectItem()) {
                    int countOfOverlappedItems = 1;

                    NMapPOIdata poiData = poiDataOverlay.getPOIdata();
                    for (int i = 0; i < poiData.count(); i++) {
                        NMapPOIitem poiItem = poiData.getPOIitem(i);

                        // skip selected item
                        if (poiItem == overlayItem) {
                            continue;
                        }

                        // check if overlapped or not
                        if (Rect.intersects(poiItem.getBoundsInScreen(), overlayItem.getBoundsInScreen())) {
                            countOfOverlappedItems++;
                        }
                    }

                    if (countOfOverlappedItems > 1) {
                        String text = countOfOverlappedItems + " overlapped items for " + overlayItem.getTitle();
                        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }

            // use custom old callout overlay
            if (overlayItem instanceof NMapPOIitem) {
                NMapPOIitem poiItem = (NMapPOIitem)overlayItem;

                if (poiItem.showRightButton()) {
        //            return new NMapCalloutCustomOldOverlay(itemOverlay, overlayItem, itemBounds,
        //                    mMapViewerResourceProvider);
                }
            }

            // use custom callout overlay
            return new NMapCalloutCustomOverlay(itemOverlay, overlayItem, itemBounds, mMapViewerResourceProvider);

            // set basic callout overlay
            //return new NMapCalloutBasicOverlay(itemOverlay, overlayItem, itemBounds);
        }

    };

    private final NMapOverlayManager.OnCalloutOverlayViewListener onCalloutOverlayViewListener = new NMapOverlayManager.OnCalloutOverlayViewListener() {

        @Override
        public View onCreateCalloutOverlayView(NMapOverlay itemOverlay, NMapOverlayItem overlayItem, Rect itemBounds) {

            if (overlayItem != null) {
                // [TEST] 말풍선 오버레이를 뷰로 설정함
                String title = overlayItem.getTitle();
                if (title != null && title.length() > 5) {
                    return new NMapCalloutCustomOverlayView(getContext(), itemOverlay, overlayItem, itemBounds);
                }
            }

            // null을 반환하면 말풍선 오버레이를 표시하지 않음
            return null;
        }

    };


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
               *//* Toast.makeText(this,"setting selected",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapActivity.this,ListActivity.class);
                startActivity(intent);*//*
                break;
            case R.id.menu_logout:
            //    Toast.makeText(this,"logout selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_exit:
           //     Toast.makeText(this,"exit selected",Toast.LENGTH_SHORT).show();
            //    moveTaskToBack(true);
           //     finish();
            //    ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
            //    am.restartPackage(getPackageName());
                break;
            case R.id.action_revert:
                if (mMyLocationOverlay != null) {
                    stopMyLocation();
                    mOverlayManager.removeOverlay(mMyLocationOverlay);
                }

                nMapController.setMapViewMode(NMapView.VIEW_MODE_VECTOR);
                nMapController.setMapViewTrafficMode(false);
                nMapController.setMapViewBicycleMode(false);

                mOverlayManager.clearOverlays();
                return true;
            case R.id.action_vector:
                invalidateMenu();
                nMapController.setMapViewMode(NMapView.VIEW_MODE_VECTOR);
                return true;

            case R.id.action_satellite:
                invalidateMenu();
                nMapController.setMapViewMode(NMapView.VIEW_MODE_HYBRID);
                return true;

            case R.id.action_traffic:
                invalidateMenu();
                nMapController.setMapViewTrafficMode(!nMapController.getMapViewTrafficMode());
                return true;

            case R.id.action_bicycle:
                invalidateMenu();
                nMapController.setMapViewBicycleMode(!nMapController.getMapViewBicycleMode());
                return true;

            case R.id.action_alpha_layer:
                invalidateMenu();
            //    nMapController.setMapAlphaLayerMode(!nMapController.getMapAlphaLayerMode(), 0xccFFFFFF);
                return true;

            case R.id.action_zoom:
                nMapView.displayZoomControls(true);
                return true;

            case R.id.action_my_location:
                startMyLocation();
                return true;

            case R.id.action_poi_data:
                mOverlayManager.clearOverlays();

                // add POI data overlay
                testPOIdataOverlay();
                return true;

            case R.id.action_path_data:
                mOverlayManager.clearOverlays();

                // add path data overlay
                testPathDataOverlay();

                // add path POI data overlay
                testPathPOIdataOverlay();
                return true;

            case R.id.action_floating_data:
                mOverlayManager.clearOverlays();
                testFloatingPOIdataOverlay();
                return true;

           *//* case R.id.action_new_activity:
                Intent intent = new Intent(this, FragmentMapActivity.class);
                startActivity(intent);
                return true;*//*

            case R.id.action_visible_bounds:
                // test visible bounds
                Rect viewFrame = nMapView.getMapController().getViewFrameVisible();
                nMapController.setBoundsVisible(0, 0, viewFrame.width(), viewFrame.height() - 200);

                // add POI data overlay
                mOverlayManager.clearOverlays();

                testPathDataOverlay();
                return true;

            case R.id.action_scale_factor:
                if (nMapView.getMapProjection().isProjectionScaled()) {
                    if (nMapView.getMapProjection().isMapHD()) {
                        nMapView.setScalingFactor(2.0F, false);
                    } else {
                        nMapView.setScalingFactor(1.0F, false);
                    }
                } else {
                    nMapView.setScalingFactor(2.0F, true);
                }
             //   mIsMapEnlared = nMapView.getMapProjection().isProjectionScaled();
                return true;

            case R.id.action_auto_rotate:
                if (nMapView.isAutoRotateEnabled()) {
                    nMapView.setAutoRotateEnabled(false, false);

                    mMapContainerView.requestLayout();

                    mHnadler.removeCallbacks(mTestAutoRotation);
                } else {

                    nMapView.setAutoRotateEnabled(true, false);

                    nMapView.setRotateAngle(30);
                    mHnadler.postDelayed(mTestAutoRotation, AUTO_ROTATE_INTERVAL);

                    mMapContainerView.requestLayout();
                }
                return true;
            case R.id.action_navermap:
                nMapView.executeNaverMap();
                return true;

        }
        return false;
    }
    private void invalidateMenu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
         //   invalidateOptionsMenu();
        }
    }
    private static final long AUTO_ROTATE_INTERVAL = 2000;
    private final Handler mHnadler = new Handler();
    private final Runnable mTestAutoRotation = new Runnable() {
        @Override
        public void run() {
//        	if (mMapView.isAutoRotateEnabled()) {
//    			float degree = (float)Math.random()*360;
//
//    			degree = mMapView.getRoateAngle() + 30;
//
//    			mMapView.setRotateAngle(degree);
//
//            	mHnadler.postDelayed(mTestAutoRotation, AUTO_ROTATE_INTERVAL);
//        	}
        }
    };
    *//**
     * Container view class to rotate map view.
     *//*
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
            *//*manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);*//*


            // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                newLatitude = lastLocation.getLatitude();
                newLongitude = lastLocation.getLongitude();


                // textView.setText("내 위치 : " + latitude + ", " + longitude);
                Toast.makeText(getContext(), "Last Known Location : " + "Latitude : " + newLatitude + "\nLongitude:" + newLongitude, Toast.LENGTH_LONG).show();
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
*/
    NMapView nMapView;
    NMapController nMapController;

    NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = null;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View baseView = inflater.inflate(R.layout.fragment_naver_map, container, false);
        nMapView = (NMapView) baseView.findViewById(R.id.nv_mapView);

        nMapView.setClientId(NAVER_CLIENT_ID);

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

        int markerId = NMapPOIflagType.PIN;

        NMapPOIdata poIdata= new NMapPOIdata(2, mMapViewerResourceProvider);
        poIdata.beginPOIdata(2);
        poIdata.addPOIitem(127.010741, 37.582590, "그라지에 미래관 123-1245", markerId, 0);
        poIdata.addPOIitem(127.009844, 37.582323, "그라지에 연구관 123-456", markerId, 0);
        poIdata.endPOIdata();

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
                Toast.makeText(getContext(), "onCalloutClick :  " + nMapPOIitem.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        //poIdataOverlay.showAllPOIdata(0);
    }
    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
        if(nMapError == null){
            nMapController.setZoomLevel(20);
            startLocationService();
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


                // textView.setText("내 위치 : " + latitude + ", " + longitude);
                Toast.makeText(getContext(), "Last Known Location : " + "Latitude : " + newLatitude + "\nLongitude:" + newLongitude, Toast.LENGTH_LONG).show();
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
}
