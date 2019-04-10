package com.example.mealsonwheels;

import android.content.Intent;
import android.location.Address;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userSignup extends AppCompatActivity {

    private Button signUpButton;
    private Button LogOutButton;
    private EditText phoneNumText;
    private EditText AddressText;
    private EditText CityText;
    private TextInputLayout AddressLayout;
    private TextInputLayout PhoneLayout;
    private TextInputLayout CityLayout;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private String address;
    private String Phone;
    private String emailId;
    private String Name;
    private DatabaseReference ref;

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
        CityText = (EditText) findViewById(R.id.cityText);
        CityLayout = (TextInputLayout) findViewById(R.id.city_text_input_layout);
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
                if (s.length() != 10 ) {
                    PhoneLayout.setError("Enter a valid Phone Number");
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
                if (s.length() == 0 ) {
                    AddressLayout.setError("Enter a valid Address");
                    AddressLayout.setErrorEnabled(true);
                } else {
                    AddressLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CityLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 ) {
                    CityLayout.setError("Enter a valid city");
                    CityLayout.setErrorEnabled(true);
                } else {
                    CityLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Users");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Phone = phoneNumText.getText().toString();
                    address = AddressText.getText().toString() + ',' + CityText.getText().toString();
                    User newUser = new User(address, emailId, Name, Phone);
                    //Toast.makeText(userSignup.this, newUser.toString(), Toast.LENGTH_LONG).show();
                    if (Phone.length() == 10 && CityText.getText().toString().length() > 0 && AddressText.getText().toString().length() > 0) {
                        //Add the user.
                        String id = ref.push().getKey();
                        ref.child(id).setValue(newUser);
                        Toast.makeText(userSignup.this, "You are now registered!", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(userSignup.this, userHomePage.class);
                        mainIntent.putExtra("userinfo",newUser);
                        mainIntent.putExtra("userID",id);
                        startActivity(mainIntent);
                        finish();
                        //Toast.makeText(userSignup.this, newUser.toString(), Toast.LENGTH_LONG).show();
                    } else {
                        if (Phone.length() != 10) {
                            phoneNumText.setText("");
                        } else {
                            AddressText.setHint("Enter a valid address");
                            AddressText.setText("");
                            CityText.setText("");
                            CityText.setHint("Enter a valid City");
                        }
                    }
                }
                catch(Exception e)
                {
                    Log.e("SignUp",e.getMessage());
                    Toast.makeText(userSignup.this, "SignUp Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            Name = acct.getDisplayName();
            emailId = acct.getEmail();
        }
        else{
            Name = "error";
            emailId = "error";
        }
    }
}
