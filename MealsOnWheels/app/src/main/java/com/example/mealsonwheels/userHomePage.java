package com.example.mealsonwheels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.mealsonwheels.Models.User;


public class userHomePage extends AppCompatActivity {

    private User currUser;
    private String id;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            try {
                Fragment newFrag = null;
                switch (item.getItemId()) {
                    case R.id.navigation_near_me:
                        newFrag = new nearMeFragment();
                        break;
                    case R.id.navigation_explore:
                        newFrag = new exploreFragment();
                        break;
                    case R.id.navigation_cart:
                        newFrag = new cartFragment();
                        break;
                    case R.id.navigation_account:
                        newFrag = new accountFragment();
                        break;
                }

                Bundle bundle = new Bundle();
                bundle.putSerializable("userinfo", currUser);
                bundle.putString("userID", id);
                newFrag.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFrag).commit();
            }
            catch (Exception e)
            {
                Log.e("Navigation","Error Occurred" + e.getMessage());
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        currUser = (User) getIntent().getSerializableExtra("userinfo");
        id = getIntent().getStringExtra("userID");
        //mAuth = FirebaseAuth.getInstance();
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment newFrag = new nearMeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("userinfo",currUser);
        bundle.putString("userID",id);
        newFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,newFrag).commit();
       /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("755544742392-7p01maovpemddhc3q4edkesmtnosc5q1.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        butto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                mAuth.signOut();
                Intent mainIntent = new Intent(userHomePage.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });*/
    }

}
