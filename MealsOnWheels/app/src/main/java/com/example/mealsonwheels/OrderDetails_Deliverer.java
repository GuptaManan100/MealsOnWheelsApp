package com.example.mealsonwheels;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.DelivererHistory;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDetails_Deliverer extends AppCompatActivity {

    private Deliverer currUser;
    private String id;
    private DatabaseReference ref;
    private String orderid;
    public Order currOrder;
    private Button Confirm;
    FirebaseDatabase database;
    List<DelivererHistory> list;
    List<User> list2;
    private TextView dt_date;
    private TextView dt_vendor;
    private TextView dt_customer;
    private TextView dt_amount;
    private TextView dt_mode;
    private TextView dt_item;
    private String customerID;
    private String customerName;
    private String order;
    private String item;
    private String quantity;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details__deliverer);

        currUser = (Deliverer) getIntent().getSerializableExtra("delivererinfo");
        id = getIntent().getStringExtra("delivererID");

        dt_date = (TextView) findViewById(R.id.dt_date);
        dt_vendor = (TextView) findViewById(R.id.dt_vendor);
        dt_customer = (TextView) findViewById(R.id.dt_customer);
        dt_amount = (TextView) findViewById(R.id.dt_amount);
        dt_mode = (TextView) findViewById(R.id.dt_mode);
        dt_item = (TextView) findViewById(R.id.dt_item);


        Confirm = (Button) findViewById(R.id.Confirm_Pickup);

        database = FirebaseDatabase.getInstance();
        Query query1 = database.getReference().child("Transactions").child("notDelivered").orderByChild("deliverer").equalTo(id);
        query1.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<DelivererHistory>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    DelivererHistory value = dataSnapshot1.getValue(DelivererHistory.class);
                    String date = value.getDate();
                    String vendorName = value.getVendorName();
                    String transactionId = value.getTransactionId();
                    String totalAmount = value.getTotalAmount();
                    String paymentMode = value.getPaymentMode();
                    customerID = value.getCustomer();
                    HashMap itemsOrdered = value.getItemsOrdered();
                    order = itemsOrdered.toString();
                    String a = orderbreak(order);

                    dt_date.setText(date);
                    dt_vendor.setText(vendorName);
                    dt_mode.setText(paymentMode);
                    dt_date.setText(date);
                    dt_amount.setText(totalAmount);
                    dt_item.setText(a);
                }
            }
            //{Brownie={Price=100,Quantity=1},Coffee={Price=100,Quantity=1}}
            private String orderbreak(String order){
                order = order.replace("{","");
                order = order.replace("}","");
                order = order.replace("Price=","");
                order = order.replace("Quantity=","");
                order = order.replace("=",",");
                order = order.replace(",","                           ");
                return order;
                /*String newstr = order.replaceAll("[^A-Za-z0-9]+", "");
                newstr = newstr.replaceAll("[0-9]","\n");
                newstr = newstr.replaceAll("(\n)\\1{2,}", "$1");
                String newstr2 = order.replace(",","\n");
                newstr2 = newstr2.replaceAll("[^A-Za-z0-9]+", "");
                newstr2 = newstr2.replaceAll("[A-Za-z]","\n");
                newstr2 = newstr2.replaceAll("(\n)\\1{2,}", "$1");
                newstr2 = newstr2.substring(1, newstr2.length()-1);
                Toast.makeText(getApplicationContext(), order, Toast.LENGTH_SHORT).show();*/
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query2 = database.getReference().child("Users");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    if(currOrder.getCustomer().equals(child.getKey())){
                        customerName = child.getValue(User.class).getName();
                    }
                }
                dt_customer.setText(customerName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered").orderByChild("deliverer").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    orderid = child.getKey();
                    currOrder = child.getValue(Order.class);
                }
                setButton();
                //Toast.makeText(OrderDetails_Deliverer.this,orderid, Toast.LENGTH_LONG).show();
                ref = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered").child(orderid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //ref = FirebaseDatabase.getInstance().getReference().child("Transactions").child("notDelivered").child(orderid);







    }

    private void setButton(){
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReference();
                Intent i = new Intent(OrderDetails_Deliverer.this,DropLocationDeliverer.class);
                i.putExtra("delivererinfo",currUser);
                i.putExtra("delivererID",id);
                startActivity(i);
                finish();
            }
        });
    }

    private void setReference(){
        currOrder.setStatus("picked");
        ref.setValue(currOrder);
    }
}
