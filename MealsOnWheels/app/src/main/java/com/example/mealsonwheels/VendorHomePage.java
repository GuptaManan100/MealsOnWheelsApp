package com.example.mealsonwheels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class VendorHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_home_page);

        final Button btn_menu= (Button) findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                startActivity(new Intent(VendorHomePage.this, Menu_Page.class));
            }
        });

        final Button btn_current_order= (Button) findViewById(R.id.btn_current_order);
        btn_current_order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(VendorHomePage.this, VendorCurrentOrder.class));
            }
        });

        final Button btn_details= (Button) findViewById(R.id.btn_details);
        btn_details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(VendorHomePage.this, Vendor_Details.class));
            }
        });
    }
}
