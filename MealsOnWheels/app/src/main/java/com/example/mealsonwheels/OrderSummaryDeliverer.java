package com.example.mealsonwheels;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OrderSummaryDeliverer extends AppCompatActivity {

    private Deliverer currUser;
    private String id;
    private DatabaseReference ref;
    private DatabaseReference toref;
    private DatabaseReference refer;
    private String orderid;
    private String  address;
    private String customer_name;
    private TextView OrderID;
    private TextView CustName;
    private TextView Address;
    private TextView PayMode;
    private TextView Amount;

    public Order currOrder;
    private Button Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary_deliverer);

        currUser = (Deliverer) getIntent().getSerializableExtra("delivererinfo");
        id = getIntent().getStringExtra("delivererID");
        OrderID = (TextView) findViewById(R.id.Order_id);
        CustName= (TextView) findViewById(R.id.Customer_name);
        Address = (TextView) findViewById(R.id.Adress);
        PayMode= (TextView) findViewById(R.id.Payment_mode);
        Amount= (TextView) findViewById(R.id.Total_Amount);
        Confirm = (Button) findViewById(R.id.Order_delivered);
        Query query = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered").orderByChild("deliverer").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    orderid = child.getKey();
                    currOrder = child.getValue(Order.class);
                }
                setButton();
                refer = FirebaseDatabase.getInstance().getReference("Deliverers");
                //Toast.makeText(OrderDetails_Deliverer.this,orderid, Toast.LENGTH_LONG).show();
                ref = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered").child(orderid);
                toref = FirebaseDatabase.getInstance().getReference().child("Transactions").child("delivered");

                Query query3 = FirebaseDatabase.getInstance().getReference("Users");
                query3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            if(currOrder.getCustomer().equals(child.getKey())){
                                address = child.getValue(User.class).getDeliveryAddress();
                                customer_name =  child.getValue(User.class).getName();

                            }
                        }





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void setButton(){
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setReference();
                customDialog("Payment Confirmation","Have you collected the cash amount from the customer", "cancelMethod1","okMethod1");

            }
        });
        Amount.setText(currOrder.getTotalAmount());
        PayMode.setText(currOrder.getPaymentMode());
        Address.setText(address);
        CustName.setText(customer_name);
        OrderID.setText(orderid);
    }


    private void moveGameRoom(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.push().setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");

                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void cancelMethod1(){
    }

    private void okMethod1(){
        moveGameRoom(ref,toref);
        ref.removeValue();
        currUser.setIsFree("No");
        refer.child(id).setValue(currUser);
        setActivity();

    }

    private void setActivity(){
        Intent i = new Intent(OrderSummaryDeliverer.this,HomePageDelivery.class);
        i.putExtra("delivererinfo",currUser);
        i.putExtra("delivererID",id);
        startActivity(i);
        finish();
    }


    public void customDialog(String title, String message, final String cancelMethod, final String okMethod){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);

        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("NOtOK", "onClick: Cancel Called.");
                        if(cancelMethod.equals("cancelMethod1")){
                            cancelMethod1();
                        }
                    }
                });

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("OK", "onClick: OK Called.");
                        if(okMethod.equals("okMethod1")){
                            okMethod1();
                        }
                    }
                });


        builderSingle.show();
    }
}
