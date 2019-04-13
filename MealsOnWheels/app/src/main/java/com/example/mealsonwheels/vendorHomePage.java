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
import android.widget.FrameLayout;


public class vendorHomePage extends AppCompatActivity {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;


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

                        switch(menuItem.getItemId())
                        {
                            case R.id.drawer_store_details:
                                getSupportFragmentManager().beginTransaction().replace(R.id.vendor_frame, new FragmentVendorStoreDetails()).commit();
                                break;

                            case R.id.drawer_menu:
                                getSupportFragmentManager().beginTransaction().replace(R.id.vendor_frame, new FragmentVendorMenu()).commit();
                                break;

                            case R.id.drawer_current_orders:
                                getSupportFragmentManager().beginTransaction().replace(R.id.vendor_frame, new FragmentVendorCurrentOrders()).commit();
                                break;

                            case R.id.drawer_past_orders:
                                getSupportFragmentManager().beginTransaction().replace(R.id.vendor_frame, new FragmentVendorPastOrders()).commit();
                                break;

                        }

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        //

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
