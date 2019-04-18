package com.example.mealsonwheels;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mealsonwheels.Models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class accountFragment extends Fragment {

    private User currUser;
    private String id;
    private Button EditSubmitButton;
    private DatabaseReference ref;
    private Button LogOutButton;
    private EditText phoneNumText;
    private ImageView profileImage;
    private EditText AddressText;
    private EditText CityText;
    private EditText NameText;
    private EditText EmailText;
    private TextInputLayout AddressLayout;
    private TextInputLayout PhoneLayout;
    private TextInputLayout CityLayout;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private String address;
    private String city;
    private String Phone;
    private int type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        id = bundle.getString("userID");
        currUser = (User) bundle.getSerializable("userinfo");
        return inflater.inflate(R.layout.fragment_account,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddressLayout = (TextInputLayout) view.findViewById(R.id.address_text_input_layout);
        PhoneLayout = (TextInputLayout) view.findViewById(R.id.phone_text_input_layout);
        LogOutButton = (Button) view.findViewById(R.id.LogOutButt);
        phoneNumText = (EditText) view.findViewById(R.id.PhoneText);
        AddressText = (EditText) view.findViewById(R.id.AddressText);
        CityText = (EditText) view.findViewById(R.id.cityText);
        profileImage = (ImageView) view.findViewById(R.id.ProfileImageView);
        NameText = (EditText) view.findViewById(R.id.NameEditText);
        EmailText = (EditText) view.findViewById(R.id.EmailEditText);
        EditSubmitButton = (Button) view.findViewById(R.id.editButt);

        CityLayout = (TextInputLayout) view.findViewById(R.id.city_text_input_layout);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("755544742392-7p01maovpemddhc3q4edkesmtnosc5q1.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        ref = FirebaseDatabase.getInstance().getReference("Users");
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                mAuth.signOut();
                Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(mainIntent);
                getActivity().finish();
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

        splitString();

        NameText.setKeyListener(null);
        NameText.setText(currUser.getName());
        EmailText.setText(currUser.getEmail());
        EmailText.setKeyListener(null);
        phoneNumText.setText(currUser.getPhone());
        phoneNumText.setEnabled(false);
        AddressText.setText(address);
        AddressText.setEnabled(false);
        CityText.setEnabled(false);
        CityText.setText(city);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            //Toast.makeText(getActivity(), acct.getPhotoUrl().toString(), Toast.LENGTH_SHORT).show();
            //profileImage.setImageURI(acct.getPhotoUrl());
            //setProfilePic.
            Glide.with(this).load(acct.getPhotoUrl()).into(profileImage);
        }

        type = 1;//EditText
        EditSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1)
                {
                    CityText.setEnabled(true);
                    AddressText.setEnabled(true);
                    phoneNumText.setEnabled(true);
                    EditSubmitButton.setText("Submit Changes");
                    type = 2;
                }
                else
                {
                    try {
                        Phone = phoneNumText.getText().toString();
                        address = AddressText.getText().toString();
                        city = CityText.getText().toString();
                        //Toast.makeText(userSignup.this, newUser.toString(), Toast.LENGTH_LONG).show();
                        if (Phone.length() == 10 && CityText.getText().toString().length() > 0 && AddressText.getText().toString().length() > 0) {
                            //Add the user.
                            currUser.setPhone(Phone);
                            currUser.setDeliveryAddress(address+','+city);
                            ref.child(id).setValue(currUser);
                            Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(getActivity(), userHomePage.class);
                            mainIntent.putExtra("userinfo",currUser);
                            mainIntent.putExtra("userID",id);
                            startActivity(mainIntent);
                            getActivity().finish();
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
                        Log.e("change Details",e.getMessage());
                        Toast.makeText(getActivity(), "Change Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void splitString() {
        address = currUser.getDeliveryAddress();
        int siz = address.length();
        int index = siz-1;
        for(int j=0;j<siz;j++)
        {
            if(address.charAt(j)==',')
            {
                index = j;
            }
        }
        city = address.substring(index+1);
        address = address.substring(0,index);
    }

    /*


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
     */
}