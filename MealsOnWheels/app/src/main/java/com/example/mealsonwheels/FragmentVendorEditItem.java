package com.example.mealsonwheels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class FragmentVendorEditItem extends Fragment {

    EditText mEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_vendor_edit_item, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.mark_list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.mark_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner1 = (Spinner) view.findViewById(R.id.category_list);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.category_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);


        final Button btn_save_changes= (Button)view.findViewById(R.id.btn_save_changes);
        btn_save_changes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                /*
                mEdit = (EditText)findViewById(R.id.item_name);
                String item_name = mEdit.getText().toString();

                mDatabase.child("Menus").child("dscsdvcdscdsv").child("Beverages").push();
                */

                mEdit = (EditText)v.findViewById(R.id.item_name);
                String item_name = mEdit.getText().toString();

                mEdit = (EditText)v.findViewById(R.id.item_price);
                String item_price = mEdit.getText().toString();
                //int item_price = Integer.parseInt(price);

                mEdit = (EditText)v.findViewById(R.id.item_ingredients);
                String item_ingredients = mEdit.getText().toString();

                Spinner mSpin = (Spinner)v.findViewById(R.id.mark_list);
                String item_mark = mSpin.getSelectedItem().toString();

                mSpin = (Spinner)v.findViewById(R.id.category_list);
                String item_category = mSpin.getSelectedItem().toString();

                CheckBox spicy = (CheckBox)v.findViewById(R.id.checkbox_spicy);
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
                    Toast toast=Toast.makeText(getActivity(),"Please Enter Item Name",Toast.LENGTH_SHORT);
                    //toast.setMargin(50,50);
                    toast.show();
                    return;
                }

                if (item_price.equals(""))
                {
                    Toast toast=Toast.makeText(getActivity(),"Please Enter Item Price",Toast.LENGTH_SHORT);
                    //toast.setMargin(50,50);
                    toast.show();
                    return;
                }



                DatabaseReference usersRef = mDatabase.child("Menus").child("dscsdvcdscdsv").child(item_category).child(item_name);
                Map<String, String> userData = new HashMap<String, String>();

                //usersRef.setValue(item_name);

                userData.put("Ingredients", item_ingredients);
                userData.put("IsSpicy", item_spicy);
                userData.put("Price", item_price);
                userData.put("Mark", item_mark);

                usersRef.setValue(userData);

                mEdit = (EditText)v.findViewById(R.id.item_name);
                mEdit.setText("");

                mEdit = (EditText)v.findViewById(R.id.item_price);
                mEdit.setText("");

                mEdit = (EditText)v.findViewById(R.id.item_ingredients);
                mEdit.setText("");

                mSpin = (Spinner)v.findViewById(R.id.mark_list);
                mSpin.setSelection(0);

                mSpin = (Spinner)v.findViewById(R.id.category_list);
                mSpin.setSelection(0);

                spicy = (CheckBox)v.findViewById(R.id.checkbox_spicy);
                spicy.setChecked(false);

            }
        });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_vendor_edit_item, container, false);
    }
}

