package com.example.mealsonwheels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class userHomePage extends AppCompatActivity {

    private User currUser;
    private String id;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment newFrag = null;
            switch (item.getItemId()) {
                case R.id.navigation_near_me:
                    newFrag = new nearMeFragment();
                case R.id.navigation_explore:
                    newFrag = new exploreFragment();
                case R.id.navigation_cart:
                    newFrag = new cartFragment();
                case R.id.navigation_account:
                    newFrag = new accountFragment();
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("userinfo",currUser);
            bundle.putString("userID",id);
            newFrag.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,newFrag).commit();
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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
