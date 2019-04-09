package com.example.mealsonwheels;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 2;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseDatabase database;
    private DatabaseReference myRefUser;
    private DatabaseReference myRef;
    private DatabaseReference myRefVendor;
    private DatabaseReference myRefDeliverer;
    private String emailId;

    private TaskCompletionSource<DataSnapshot> userSource = new TaskCompletionSource<>();
    private Task userTask = userSource.getTask();
    private TaskCompletionSource<DataSnapshot> venSource = new TaskCompletionSource<>();
    private Task vendorTask = venSource.getTask();
    private TaskCompletionSource<DataSnapshot> delivererSource = new TaskCompletionSource<>();
    private Task delivererTask = delivererSource.getTask();

    private Task<Void> allTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInButton = (SignInButton) findViewById(R.id.signInButton);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRefUser = database.getReference("Users");
        myRefVendor = database.getReference("Vendors");
        myRefDeliverer = database.getReference("Deliverers");
        myRef = database.getReference();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("755544742392-7p01maovpemddhc3q4edkesmtnosc5q1.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
            updateUI(currentUser);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                signInButton.setEnabled(false);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                Log.w("Login", "Google sign in failed" + e.getMessage(), e);
                signInButton.setEnabled(true);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("Login", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            signInButton.setEnabled(false);
                            Log.d("Login", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            mGoogleSignInClient.signOut();
                            signInButton.setEnabled(true);
                            Log.w("Login", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Login Failure", Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        //All children Access

        checkIfUserExists(user);
        checkIfDelivererExists(user);
        checkIfVendorExists(user);

        allTask = Tasks.whenAll(userTask, vendorTask, delivererTask);
        allTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(!isFinishing()) {
                    Toast.makeText(MainActivity.this, "You are not yet registered!", Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(MainActivity.this, userSignup.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });
        allTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                signInButton.setEnabled(true);
            }
        });
    }

    private void checkIfUserExists(FirebaseUser user)
    {
        emailId = user.getEmail();
        Query query = myRefUser.orderByChild("email").equalTo(emailId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    Toast.makeText(MainActivity.this, "Login Successfull!!", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(MainActivity.this, userHomePage.class);
                    startActivity(mainIntent);
                    finish();
                }
                userSource.setResult(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                userSource.setException(databaseError.toException());
            }
        });
    }

    private void checkIfVendorExists(FirebaseUser user) {
        emailId = user.getEmail();
        Query query1 = myRefVendor.orderByChild("email").equalTo(emailId);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Toast.makeText(MainActivity.this, "Login Successfull!!", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(MainActivity.this, vendorHomePage.class);
                    startActivity(mainIntent);
                    finish();
                }
                venSource.setResult(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                venSource.setException(databaseError.toException());
            }
        });
    }

    private void checkIfDelivererExists(FirebaseUser user) {
        emailId = user.getEmail();
        Query query = myRefDeliverer.orderByChild("email").equalTo(emailId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Toast.makeText(MainActivity.this, "Login Successfull!!", Toast.LENGTH_LONG).show();
                    Intent mainIntent = new Intent(MainActivity.this, deliveryHomePage.class);
                    startActivity(mainIntent);
                    finish();
                }
                delivererSource.setResult(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                delivererSource.setException(databaseError.toException());
            }
        });
    }




    //All table access
    /*
    private void checkIfUserExists(FirebaseUser user) {
        emailId = user.getEmail();
        myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User curr = child.getValue(User.class);
                    Log.d("Login",curr.getEmail());
                    if(curr.getEmail().equals(emailId))
                    {
                        Toast.makeText(MainActivity.this, "Login Successfull!!", Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(MainActivity.this, userHomePage.class);

                        startActivity(mainIntent);
                        finish();
                    }
                    //Toast.makeText(MainActivity.this, curr.toString(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        myRefVendor.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Vendor curr = child.getValue(Vendor.class);
                    Log.d("Login",curr.getEmail());
                    if(curr.getEmail().equals(emailId))
                    {
                        Toast.makeText(MainActivity.this, "Login Successfull!!", Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(MainActivity.this, vendorHomePage.class);
                        startActivity(mainIntent);
                        finish();
                    }
                    //Toast.makeText(MainActivity.this, curr.toString(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        myRefDeliverer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Deliverer curr = child.getValue(Deliverer.class);
                    Log.d("Login",curr.getEmail());
                    if(curr.getEmail().equals(emailId))
                    {
                        Toast.makeText(MainActivity.this, "Login Successfull!!", Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(MainActivity.this, deliveryHomePage.class);
                        startActivity(mainIntent);
                        finish();
                    }
                    //Toast.makeText(MainActivity.this, curr.toString(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    */
}
