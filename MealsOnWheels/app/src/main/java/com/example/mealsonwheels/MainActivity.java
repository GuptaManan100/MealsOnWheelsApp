package com.example.mealsonwheels;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText phoneText;
    private EditText otptext;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    private Button sendButton;
    private Button verifyOTPbutton;
    private TextView changeNumText;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        phoneText = (EditText) findViewById(R.id.PhoneNumberText);
        otptext = (EditText) findViewById(R.id.OtpText);
        sendButton = (Button) findViewById(R.id.LoginButton);
        verifyOTPbutton = (Button) findViewById(R.id.VerifyButton);
        changeNumText = (TextView) findViewById(R.id.ChangeNumText);
        mAuth = FirebaseAuth.getInstance();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneText.setEnabled(false);
                sendButton.setVisibility(View.INVISIBLE);


                String phoneNumber = "+91" + phoneText.getText().toString();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,
                        60,
                        TimeUnit.SECONDS,
                        MainActivity.this,
                        mCallBack
                );
            }
        });

        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d("Login", "onVerificationCompleted:" + phoneAuthCredential);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                changeNumText.setVisibility(View.VISIBLE);
                Log.w("Login", "signInWithCredential:failure");
                Toast.makeText(MainActivity.this, "Login Failed!!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("Login", "onCodeSent:" + verificationId);
                otptext.setVisibility(View.VISIBLE);
                verifyOTPbutton.setVisibility(View.VISIBLE);
                changeNumText.setVisibility(View.VISIBLE);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };

        changeNumText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneText.setEnabled(true);
                sendButton.setVisibility(View.VISIBLE);
                otptext.setVisibility(View.INVISIBLE);
                verifyOTPbutton.setVisibility(View.INVISIBLE);
                changeNumText.setVisibility(View.INVISIBLE);
            }
        });

        verifyOTPbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verifactionCode = otptext.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verifactionCode);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Intent homeIntent = new Intent(MainActivity.this, userHomePage.class);
                            startActivity(homeIntent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            changeNumText.setVisibility(View.VISIBLE);
                            Log.w("Login", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Login Failed!!", Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
        {
            Intent homeIntent = new Intent(MainActivity.this, userHomePage.class);
            startActivity(homeIntent);
            finish();
        }
    }
}
