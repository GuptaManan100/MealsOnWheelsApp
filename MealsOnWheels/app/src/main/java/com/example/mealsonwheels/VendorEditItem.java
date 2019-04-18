package com.example.mealsonwheels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mealsonwheels.Models.Vendor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class VendorEditItem extends AppCompatActivity {

    EditText mEdit;
    Vendor vendor;
    String vendor_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_edit_item);

        Bundle bundle = getIntent().getExtras();
        String qitem_name = bundle.getString("name");
        String qitem_mark = bundle.getString("mark");
        String qitem_price = bundle.getString("price");
        String qitem_category = bundle.getString("category");
        String qitem_spicy = bundle.getString("isSpicy");
        String qitem_ingredients = bundle.getString("ingredients");

        vendor = (Vendor) getIntent().getSerializableExtra("vendorInfo");
        vendor_id = getIntent().getStringExtra("vendorID");

        mEdit = (EditText)findViewById(R.id.item_name);
        mEdit.setText(qitem_name);

        mEdit = (EditText)findViewById(R.id.item_price);
        mEdit.setText(qitem_price);

        mEdit = (EditText)findViewById(R.id.item_ingredients);
        mEdit.setText(qitem_ingredients);

        CheckBox spicy = (CheckBox)findViewById(R.id.checkbox_spicy);
        if (qitem_spicy.equals("Yes"))
        {
            spicy.setChecked(true);
        }
        else
        {
            spicy.setChecked(false);
        }

        Spinner spinner = (Spinner) findViewById(R.id.mark_list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mark_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner1 = (Spinner) findViewById(R.id.category_list);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setEnabled(false);

        if(qitem_mark.equals("Veg"))
        {
            spinner.setSelection(0);
        }
        else
        {
            spinner.setSelection(1);
        }

        if(qitem_category.equals("Beverages"))
        {
            spinner1.setSelection(0);
        }
        else if(qitem_category.equals("Dessert"))
        {
            spinner1.setSelection(1);
        }
        else
        {
            spinner1.setSelection(2);
        }

        final Button btn_save_changes= (Button) findViewById(R.id.btn_save_changes);
        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                /*
                mEdit = (EditText)findViewById(R.id.item_name);
                String item_name = mEdit.getText().toString();

                mDatabase.child("Menus").child("dscsdvcdscdsv").child("Beverages").push();
                */

                mEdit = (EditText)findViewById(R.id.item_name);
                String item_name = mEdit.getText().toString();

                mEdit = (EditText)findViewById(R.id.item_price);
                String item_price = mEdit.getText().toString();
                //int item_price = Integer.parseInt(price);

                mEdit = (EditText)findViewById(R.id.item_ingredients);
                String item_ingredients = mEdit.getText().toString();

                Spinner mSpin = (Spinner)findViewById(R.id.mark_list);
                String item_mark = mSpin.getSelectedItem().toString();

                mSpin = (Spinner)findViewById(R.id.category_list);
                String item_category = mSpin.getSelectedItem().toString();

                CheckBox spicy = (CheckBox)findViewById(R.id.checkbox_spicy);
                String item_spicy;
                if (spicy.isChecked())
                {
                    item_spicy = "Yes";
                }
                else
                {
                    item_spicy = "No";
                }

                if (item_name.equals(""))
                {
                    Toast toast=Toast.makeText(getApplicationContext(),"Please Enter Item Name",Toast.LENGTH_SHORT);
                    //toast.setMargin(50,50);
                    toast.show();
                    return;
                }

                if (item_price.equals(""))
                {
                    Toast toast=Toast.makeText(getApplicationContext(),"Please Enter Item Price",Toast.LENGTH_SHORT);
                    //toast.setMargin(50,50);
                    toast.show();
                    return;
                }



                DatabaseReference usersRef = mDatabase.child("Menus").child("dscsdvcdscdsv").child(item_category).child(item_name);
                Map<String, String> userData = new HashMap<String, String>();

                //usersRef.setValue(item_name);

                userData.put("ingredients", item_ingredients);
                userData.put("isSpicy", item_spicy);
                userData.put("price", item_price);
                userData.put("mark", item_mark);

                usersRef.setValue(userData);

                Intent mainIntent = new Intent(VendorEditItem.this, vendorHomePage.class);
                mainIntent.putExtra("vendorInfo",vendor);
                mainIntent.putExtra("vendorID",vendor_id);
                startActivity(mainIntent);
                finish();

            }
        });



    }
}
