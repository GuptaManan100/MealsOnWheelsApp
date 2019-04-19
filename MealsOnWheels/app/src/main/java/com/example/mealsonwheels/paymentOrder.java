package com.example.mealsonwheels;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mealsonwheels.Adapter.OrderAdapter;
import com.example.mealsonwheels.Models.CartItem;
import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.MenuItem;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class paymentOrder extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Order newOrder;
    private RecyclerView recycler_orders;
    private OrderAdapter adapter;

    private RadioButton cashRadio;
    private RadioButton paytmRadio;
    private Button submitButton;
    private User currUser;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_order);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        newOrder = (Order) getIntent().getSerializableExtra("order");
        currUser = (User) getIntent().getSerializableExtra("userInfo");
        userID = getIntent().getStringExtra("userId");
        cashRadio = (RadioButton) findViewById(R.id.cashRadioButton);
        paytmRadio = (RadioButton) findViewById(R.id.payTMradioButton);
        recycler_orders = (RecyclerView) findViewById(R.id.recycler_order);
        submitButton = (Button) findViewById(R.id.checkoutButton);
        recycler_orders.hasFixedSize();
        recycler_orders.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderAdapter(this);
        recycler_orders.setAdapter(adapter);

        List<Order> NewOrdres = new ArrayList<>();
        List<String> NewOrdresIds = new ArrayList<>();
        NewOrdres.add(newOrder);
        NewOrdresIds.add("dontCare");
        adapter.addAll(NewOrdres,5,NewOrdresIds);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cashRadio.isChecked())
                {
                    Query query = myRef.child("Deliverers").orderByChild("isFree").equalTo("Yes");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int x = 0;
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                x = 1;
                                Deliverer deliverer = child.getValue(Deliverer.class);
                                newOrder.setDeliverer(child.getKey());
                                newOrder.setDelivererName(deliverer.getName());
                                newOrder.setStatus("Not Picked");
                                deliverer.setIsFree("InProcess");
                                myRef.child("Deliverers").child(child.getKey()).setValue(deliverer);
                                Log.d("checkout", newOrder.toString());
                                newOrder.setPaymentMode("Cash On Delivery");
                                newOrder.setTransactionId("None");
                                newOrder.setDelivererLocation(",");
                                String id = myRef.child("Transactions").child("notDelivered").push().getKey();
                                myRef.child("Transactions").child("notDelivered").child(id).setValue(newOrder);
                                Intent mainIntent = new Intent(paymentOrder.this, userHomePage.class);
                                mainIntent.putExtra("userinfo",currUser);
                                mainIntent.putExtra("userID",userID);
                                startActivity(mainIntent);
                                finish();
                                Toast.makeText(paymentOrder.this, "Order Placed", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            if(x==0)
                            {
                                Toast.makeText(paymentOrder.this, "No deliverer free right now!!", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                }
                else
                {
                    if (ContextCompat.checkSelfPermission(paymentOrder.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(paymentOrder.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                    }
                    Query query = myRef.child("Deliverers").orderByChild("isFree").equalTo("Yes");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int x = 0;
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                x = 1;
                                Deliverer deliverer = child.getValue(Deliverer.class);
                                newOrder.setDeliverer(child.getKey());
                                newOrder.setDelivererName(deliverer.getName());
                                newOrder.setStatus("Not Picked");
                                deliverer.setIsFree("processing");
                                myRef.child("Deliverers").child(child.getKey()).setValue(deliverer);
                                Log.d("checkout", newOrder.toString());
                                newOrder.setPaymentMode("Paytm");
                                newOrder.setDelivererLocation(",");
                                //String id = myRef.child("Transactions").child("notDelivered").push().getKey();
                                //myRef.child("Transactions").child("notDelivered").child(id).setValue(newOrder);
                                Intent mainIntent = new Intent(paymentOrder.this, checksum.class);
                                mainIntent.putExtra("userinfo",currUser);
                                mainIntent.putExtra("userID",userID);
                                mainIntent.putExtra("order",newOrder);
                                mainIntent.putExtra("deliverer",deliverer);
                                mainIntent.putExtra("deliID",child.getKey());
                                startActivity(mainIntent);
                                finish();
                                break;
                            }
                            if(x==0)
                            {
                                Toast.makeText(paymentOrder.this, "No deliverer free right now!!", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                    /*PaytmPGService Service = PaytmPGService.getStagingService();
                    HashMap<String, String> paramMap = new HashMap<String,String>();
                    paramMap.put( "MID" , "rxazcv89315285244163");
// Key in your staging and production MID available in your dashboard
                    paramMap.put( "ORDER_ID" , "order1");
                    paramMap.put( "CUST_ID" , "cust123");
                    paramMap.put( "MOBILE_NO" , "7777777777");
                    paramMap.put( "EMAIL" , "username@emailprovider.com");
                    paramMap.put( "CHANNEL_ID" , "WAP");
                    paramMap.put( "TXN_AMOUNT" , "100.12");
                    paramMap.put( "WEBSITE" , "WEBSTAGING");
// This is the staging value. Production value is available in your dashboard
                    paramMap.put( "INDUSTRY_TYPE_ID" , "Retail");
// This is the staging value. Production value is available in your dashboard
                    paramMap.put( "CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=order1");
                    paramMap.put( "CHECKSUMHASH" , "w2QDRMgp1234567JEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");
                    PaytmOrder Order = new PaytmOrder(paramMap);*/

                    //String checkSum =  CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(MercahntKey, paramMap);
                }
            }
        });


    }
}
