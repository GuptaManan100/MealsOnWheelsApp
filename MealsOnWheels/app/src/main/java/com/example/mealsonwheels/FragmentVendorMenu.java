package com.example.mealsonwheels;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentVendorMenu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.layout_vendor_menu, container, false);

        Button btn = (Button) view.findViewById(R.id.btn_add_item);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.vendor_frame, new FragmentVendorEditItem()).commit();
            }
        });
       return view;
    }
}

