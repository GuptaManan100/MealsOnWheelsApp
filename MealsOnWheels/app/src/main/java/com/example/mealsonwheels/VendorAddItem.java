package com.example.mealsonwheels;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class VendorAddItem extends AppCompatActivity {

    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_add_item);

        Spinner spinner = (Spinner) findViewById(R.id.add_mark_list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mark_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner1 = (Spinner) findViewById(R.id.add_category_list);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);



        final Button btn_save_changes= (Button) findViewById(R.id.add_btn_save_changes);
        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                /*
                mEdit = (EditText)findViewById(R.id.item_name);
                String item_name = mEdit.getText().toString();

                mDatabase.child("Menus").child("dscsdvcdscdsv").child("Beverages").push();
                */

                mEdit = (EditText)findViewById(R.id.add_item_name);
                String item_name = mEdit.getText().toString();

                mEdit = (EditText)findViewById(R.id.add_item_price);
                String item_price = mEdit.getText().toString();
                //int item_price = Integer.parseInt(price);

                mEdit = (EditText)findViewById(R.id.add_item_ingredients);
                String item_ingredients = mEdit.getText().toString();

                Spinner mSpin = (Spinner)findViewById(R.id.add_mark_list);
                String item_mark = mSpin.getSelectedItem().toString();

                mSpin = (Spinner)findViewById(R.id.add_category_list);
                String item_category = mSpin.getSelectedItem().toString();

                CheckBox spicy = (CheckBox)findViewById(R.id.add_checkbox_spicy);
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

                mEdit = (EditText)findViewById(R.id.add_item_name);
                mEdit.setText("");

                mEdit = (EditText)findViewById(R.id.add_item_price);
                mEdit.setText("");

                mEdit = (EditText)findViewById(R.id.add_item_ingredients);
                mEdit.setText("");

                mSpin = (Spinner)findViewById(R.id.add_mark_list);
                mSpin.setSelection(0);

                mSpin = (Spinner)findViewById(R.id.add_category_list);
                mSpin.setSelection(0);

                spicy = (CheckBox)findViewById(R.id.add_checkbox_spicy);
                spicy.setChecked(false);

            }
        });



    }
}
