package com.example.mealsonwheels;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.Vendor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.sql.StatementEvent;


public class mapFragmentPickup extends Fragment implements OnMapReadyCallback {

    public Order currOrder;
    private String currOrderId;
    private MapView mMapView;
    //private static final float MAP_ZOOM =  15f;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public Context context;
    private Deliverer currUser;
    private DatabaseReference ref;
    private String id;
    private ProgressBar Prog;
    private double Lat=0;
    private double Long=0;
    private String phone;
    private String address;
    private TextView Address;
    private String vendor_name;
    private TextView Vendor_name;
    private String locate_naviagte;
    private Button pickup_call;
    private Button Navigate;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_fragment_pickup, container, false);
        Bundle bundle = this.getArguments();
        id = bundle.getString("delivererID");
        currUser = (Deliverer) bundle.getSerializable("delivererinfo");
        ref = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered");
        Prog = (ProgressBar) view.findViewById(R.id.progressBar2);
        Address = (TextView) view.findViewById(R.id.Location);
        Vendor_name = (TextView) view.findViewById(R.id.Name_Vendor);
        mMapView = (MapView) view.findViewById(R.id.mapView2);
        pickup_call = (Button) view.findViewById(R.id.Pickup_call);
        Navigate = (Button) view.findViewById(R.id.navigation_button);


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);

       // Load_task load_task = new Load_task();
        //
        // load_task.execute();


        Query query = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered").orderByChild("deliverer").equalTo(id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    currOrder = child.getValue(Order.class);
                }
                String Location = currOrder.getCustomerLocation();
//                Toast.makeText(getActivity(),currOrder.getCustomerLocation(), Toast.LENGTH_LONG).show();
                locate_naviagte = currOrder.getCustomerLocation();


                Query query2 = FirebaseDatabase.getInstance().getReference("Vendors");

                query2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            if(currOrder.getVendor().equals(child.getKey())){
                                locate_naviagte = child.getValue(Vendor.class).getLocation();
                                phone = child.getValue(Vendor.class).getPhone();
                                address = child.getValue(Vendor.class).getAddress();
                                vendor_name =  child.getValue(Vendor.class).getName();
                            }
                        }

                        String Location = locate_naviagte;
                        int siz = Location.length();
                        int index = siz-1;
                        for(int j=0;j<siz;j++)
                        {
                            if(Location.charAt(j)==',')
                            {
                                index = j;
                            }
                        }
                        String Longitude = Location.substring(index+1);
                        String Latitude = Location.substring(0,index);

                        Long = Double.parseDouble(Longitude);
                        Lat = Double.parseDouble(Latitude);


                        set_map();

                        Prog.setVisibility(View.INVISIBLE);
                        Vendor_name.setText(vendor_name);
                        Address.setText(address);
                        set_button();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    public void set_map(){
        mMapView.getMapAsync(this);

    }

    private void set_button(){
        pickup_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission toh leni chahiye thi naa", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + phone));
                    startActivity(callIntent);
                }

            }
        });
        Navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "This is my Toast message!", Toast.LENGTH_LONG).show();
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+locate_naviagte);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


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
    public void onMapReady(GoogleMap map2) {


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        map2.setMyLocationEnabled(true);
       // Toast.makeText(getActivity(),String.valueOf(Lat),Toast.LENGTH_LONG).show();
       // Toast.makeText(getActivity(),String.valueOf(Long),Toast.LENGTH_LONG).show();
        Marker Pickup = map2.addMarker(new MarkerOptions().position(new LatLng(Lat, Long)).title("Yaha Jaana he").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        map2.animateCamera(CameraUpdateFactory.newLatLngZoom(Pickup.getPosition(), 14));
        map2.addMarker(new MarkerOptions().position(new LatLng(Lat,Long)).title("Marker"));


    }


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

    /*private class Load_task extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Prog.setVisibility(View.VISIBLE);
            pickup_call.setVisibility(View.INVISIBLE);
            Navigate.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Query query = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered").orderByChild("deliverer").equalTo(id);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        currOrder = child.getValue(Order.class);
                    }
                    String Location = currOrder.getCustomerLocation();
                    locate_naviagte = currOrder.getCustomerLocation();
                    int siz = Location.length();
                    int index = siz-1;
                    for(int j=0;j<siz;j++)
                    {
                        if(Location.charAt(j)==',')
                        {
                            index = j;
                        }
                    }
                    String Longitude = Location.substring(index+1);
                    String Latitude = Location.substring(0,index);

                    Long = Double.parseDouble(Longitude);
                    Lat = Double.parseDouble(Latitude);


                    Query query2 = FirebaseDatabase.getInstance().getReference("Vendors");

                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        if(currOrder.getVendor().equals(child.getKey())){
                                            phone = child.getValue(Vendor.class).getPhone();
                                        }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            set_map();
            set_button();
            pickup_call.setVisibility(View.VISIBLE);
            Navigate.setVisibility(View.VISIBLE);
            super.onPostExecute(aVoid);
        }
    }*/

}
