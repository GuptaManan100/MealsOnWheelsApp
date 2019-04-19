package com.example.mealsonwheels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsonwheels.Models.Vendor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class FragmentVendorStoreDetails extends Fragment {

    public TextView name, address, avg, opening_time, type, phone, email,closing_time;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private RatingBar rt;
    public store_detail str;
    private Button update,save;

    private Integer x;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    private Vendor vendor;
    private String vendor_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = this.getArguments();
        vendor_id = bundle.getString("vendorID");
        vendor = (Vendor) bundle.getSerializable("vendorinfo");

        View v = inflater.inflate(R.layout.layout_vendor_store_details, container, false);

        x = 0;
        update= (Button) v.findViewById(R.id.update);
        opening_time = v.findViewById(R.id.opening_time);
        closing_time=v.findViewById(R.id.closing_time);

        update.setText("Edit");
        opening_time.setEnabled(false);
        closing_time.setEnabled(false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name);
        address = view.findViewById(R.id.address);
        opening_time = view.findViewById(R.id.opening_time);
        closing_time=view.findViewById(R.id.closing_time);
        type = view.findViewById(R.id.type);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        rt = view.findViewById(R.id.ratingbar);
        update=view.findViewById(R.id.update);
        // save =view.findViewById(R.id.save);

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (x == 0) {
                    x = 1;
                    update.setText("Update");
                    opening_time.setEnabled(true);
                    closing_time.setEnabled(true);
                }
                else
                {
                    DatabaseReference usersRef = mDatabase.child("Vendors").child(vendor_id);
                    final Map<String, String> userData = new HashMap<String, String>();

                    userData.put("closingTime",closing_time.getText().toString());
                    userData.put("openingTime",opening_time.getText().toString());
                    userData.put("name",vendor.getName());
                    userData.put("avgPrice",vendor.getAvgPrice());

                    userData.put("address",vendor.getAddress());
                    userData.put("email",vendor.getEmail());


                    userData.put("phone",vendor.getPhone());
                    userData.put("noOfRatings",vendor.getNoOfRatings());

                    userData.put("rating",vendor.getRating());

                    userData.put("location",vendor.getLocation());

                    userData.put("type",vendor.getType());

                    usersRef.setValue(userData);

                    Toast.makeText(getActivity(),"Updated Successfully",Toast.LENGTH_SHORT).show();

                    x = 0;
                    update.setText("Edit");
                    opening_time.setEnabled(false);
                    closing_time.setEnabled(false);

                }

            }


            public void onCancelled ( @NonNull DatabaseError databaseError ) {

            }

        });






        database = FirebaseDatabase.getInstance( );
        ref = database.getReference( );

        ref.child("Vendors").child(vendor_id).addValueEventListener(
                new ValueEventListener( ) {
                    @Override
                    public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                        store_detail contact = dataSnapshot.getValue(store_detail.class);
                        name.setText(contact.getName( )); // "John Doe"
                        address.setText(contact.getAddress( )); // "Te
                        type.setText(contact.getType( ));
                        phone.setText(contact.getPhone( ));
                        email.setText(contact.getEmail( ));
                        opening_time.setText(contact.getOpeningTime( ));
                        closing_time.setText(contact.getClosingTime());
                        rt.setMax(50);
                        //rt.setRating();
                        float f = Float.parseFloat(contact.getRating( ));
                        rt.setRating((float) f);
                        //rt.setActivated(0);
                        rt.setEnabled(false);
                    }

                    @Override
                    public void onCancelled ( @NonNull DatabaseError databaseError ) {

                    }
                }
        );
    }
}

