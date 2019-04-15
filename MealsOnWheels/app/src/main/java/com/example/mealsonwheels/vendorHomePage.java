package com.example.mealsonwheels;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class vendorHomePage extends AppCompatActivity{

    private Vendor vendor = new Vendor("My Ass,Guwahati", "dalal.shivang@gmail.com", "Foodies World", "1234567890", "10:00" , "23:00", "Italian");
    private String vendor_id = "dscsdvcdscdsv";

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;

    // Interface for edit item fragment ---------------------------------------------------------------------------------------

//    @Override
//    public void add_menu_item(String name, String mark, String price, String category, String isSpicy, String ingredients)
//    {
//
//        EditText mEdit;
//
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference usersRef = mDatabase.child("Menus").child("dscsdvcdscdsv").child(category).child(name);
//        Map<String, String> userData = new HashMap<String, String>();
//
//        //usersRef.setValue(item_name);
//
//        userData.put("ingredients", ingredients);
//        userData.put("isSpicy", isSpicy);
//        userData.put("price", price);
//        userData.put("mark", mark);
//
//        usersRef.setValue(userData);
//
//    }

    //------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_home_page);

        mDrawerlayout = (DrawerLayout) findViewById(R.id.vendor_drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NavigationView btn = (NavigationView) findViewById(R.id.vendor_navigation_view);
        final DrawerLayout drawerLayout = findViewById(R.id.vendor_drawer);

        btn.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped

                        drawerLayout.closeDrawers();

                        Fragment newFrag = null;
                        switch(menuItem.getItemId())
                        {
                            case R.id.drawer_store_details:
                                newFrag = new FragmentVendorStoreDetails();
                                break;

                            case R.id.drawer_menu:
                                newFrag = new FragmentVendorMenu();
                                break;

                            case R.id.drawer_current_orders:
                                newFrag = new FragmentVendorCurrentOrders();
                                break;

                            case R.id.drawer_past_orders:
                                newFrag = new FragmentVendorPastOrders();
                                break;

                        }

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("vendorinfo", vendor);
                        bundle.putString("vendorID", vendor_id);
                        newFrag.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.vendor_frame, newFrag).commit();

                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
