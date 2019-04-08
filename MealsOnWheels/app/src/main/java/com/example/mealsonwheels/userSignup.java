package com.example.mealsonwheels;

import android.content.Intent;
import android.location.Address;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class userSignup extends AppCompatActivity {

    private Button signUpButton;
    private Button LogOutButton;
    private EditText phoneNumText;
    private EditText AddressText;
    private TextInputLayout AddressLayout;
    private TextInputLayout PhoneLayout;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private String address;
    private String Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        AddressLayout = (TextInputLayout) findViewById(R.id.address_text_input_layout);
        PhoneLayout = (TextInputLayout) findViewById(R.id.phone_text_input_layout);
        signUpButton = (Button) findViewById(R.id.signUpButt);
        LogOutButton = (Button) findViewById(R.id.LogOutButt);
        phoneNumText = (EditText) findViewById(R.id.PhoneText);
        AddressText = (EditText) findViewById(R.id.AddressText);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("755544742392-7p01maovpemddhc3q4edkesmtnosc5q1.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                mAuth.signOut();
                Intent mainIntent = new Intent(userSignup.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });

        PhoneLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    PhoneLayout.setError("Enter a valid Address");
                    PhoneLayout.setErrorEnabled(true);
                } else {
                    PhoneLayout.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {            }
        });

        AddressLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 10 ) {
                    PhoneLayout.setError("Enter a valid Phone Number");
                    PhoneLayout.setErrorEnabled(true);
                } else {
                    PhoneLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
}
