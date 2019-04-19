package com.example.mealsonwheels;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class mapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;
    //private static final float MAP_ZOOM =  15f;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);

        //GeoDataClient mGeoDataClient = Places.getGeoDataClient(this, null);
        //PlaceDetectionClient mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        //FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        //map.addMarker(new MarkerOptions().position(new LatLng(26.167997, 91.705307)).title("Marker"));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        map.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Marker mader = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Maaaaaaarker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(mader.getPosition(), 14));

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
        }

        //updateCurrentLocation(map);
       /* Activity activity = Activity.getRootActivity();
        LocationManager lm = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        long double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        map.moveCamera(CameraUpdateFactory.newLatLng(location.getLongitude(),location.getLatitude());*/

    }

    /*private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            yourfunction();
            handler.postDelayed(this, 1000);
        }
    };

//Start
handler.postDelayed(runnable,1000);*/


    /*final Handler handler = new Handler();
handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            // Do something after 5s = 5000ms
            buttons[inew][jnew].setBackgroundColor(Color.BLACK);
        }
    }, 5000);*/

   /*public void updateCurrentLocation(GoogleMap map) {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if ((ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            Marker mader = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Maaaaaaarker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(mader.getPosition(), 14));

        }

        catch (Exception e)
        {
            Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
        }
    }*/


    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
