package com.example.mealsonwheels;

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

import com.example.mealsonwheels.Models.Vendor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentVendorStoreDetails extends Fragment {

    public TextView name, address, avg, time, type, phone, email;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private RatingBar rt;
    public store_detail str;
    private Button btn;

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
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name);
        address = view.findViewById(R.id.address);
        time = view.findViewById(R.id.time);
        type = view.findViewById(R.id.type);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        rt = view.findViewById(R.id.ratingbar);

        database = FirebaseDatabase.getInstance( );
        ref = database.getReference( );

        ref.child("Vendors").child("hardikatyal").addValueEventListener(
                new ValueEventListener( ) {
                    @Override
                    public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                        store_detail contact = dataSnapshot.getValue(store_detail.class);
                        name.setText(contact.getName( )); // "John Doe"
                        address.setText(contact.getAddress( )); // "Te
                        type.setText(contact.getType( ));
                        phone.setText(contact.getPhone( ));
                        email.setText(contact.getEmail( ));
                        time.setText("opening time \n" + contact.getOpeningTime( ) + " to " + contact.getClosingTime( ));
                        rt.setMax(10);
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

