package com.example.mealsonwheels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.mealsonwheels.Models.Deliverer;
import com.example.mealsonwheels.Models.Order;
import com.example.mealsonwheels.Models.User;
import com.example.mealsonwheels.PublicClasses.JSONParser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {

    private Order newOrder;
    private User user;
    private String userID;
    private Deliverer deliverer;
    private String deliverID;
    private String mid,orderId,custid;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        Intent intent = getIntent();
        newOrder =(Order) intent.getSerializableExtra("order");
        user =(User) intent.getSerializableExtra("userinfo");
        userID = intent.getStringExtra("userID");
        deliverer =(Deliverer) intent.getSerializableExtra("deliverer");
        deliverID = intent.getStringExtra("deliID");

        orderId = getSaltString();
        custid = userID.replace('-','@');
        newOrder.setTransactionId(orderId);

        mid = "gqHkIh40947005643657"; /// your marchant key
        Log.d("paytm","after mid");
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {
        private ProgressDialog dialog = new ProgressDialog(checksum.this);
        //private String orderId , mid, custid, amt;
        String url ="http://mealsonwheelsiitg.000webhostapp.com/generateChecksum.php";
        String varifyurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        // "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String CHECKSUMHASH ="";
        @Override
        protected void onPreExecute() {
            Log.d("paytm","pre execute");
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }
        protected String doInBackground(ArrayList<String>... alldata) {
            Log.d("paytm","background");
            JSONParser jsonParser = new JSONParser(checksum.this);
            String param=
                    "MID="+mid+
                            "&ORDER_ID=" + orderId+
                            "&CUST_ID="+custid+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT="+Math.round(Double.parseDouble(newOrder.getTotalAmount()))+"&WEBSITE=WEBSTAGING"+
                            "&CALLBACK_URL="+ varifyurl+"&INDUSTRY_TYPE_ID=Retail";
            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {
                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            PaytmPGService Service = PaytmPGService.getStagingService();
            // when app is ready to publish use production service
            // PaytmPGService  Service = PaytmPGService.getProductionService();
            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", String.valueOf(Math.round(Double.parseDouble(newOrder.getTotalAmount()))));
            paramMap.put("WEBSITE", "WEBSTAGING");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(checksum.this, true, true,
                    checksum.this  );
        }
    }
    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum ", " respon true " + bundle.toString());
        deliverer.setIsFree("No");
        myRef.child("Deliverers").child(deliverID).setValue(deliverer);
        Log.d("checkout", newOrder.toString());
        newOrder.setPaymentMode("Paytm");
        newOrder.setDelivererLocation(",");
        String id = myRef.child("Transactions").child("notDelivered").push().getKey();
        myRef.child("Transactions").child("notDelivered").child(id).setValue(newOrder);
        Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(checksum.this, userHomePage.class);
        mainIntent.putExtra("userinfo",user);
        mainIntent.putExtra("userID",userID);
        startActivity(mainIntent);
        finish();
    }
    @Override
    public void networkNotAvailable() {
        deliverer.setIsFree("Yes");
        myRef.child("Deliverers").child(deliverID).setValue(deliverer);
        Toast.makeText(this, "Network Unavailable", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(checksum.this, userHomePage.class);
        mainIntent.putExtra("userinfo",user);
        mainIntent.putExtra("userID",userID);
        startActivity(mainIntent);
        finish();
    }
    @Override
    public void clientAuthenticationFailed(String s) {
        deliverer.setIsFree("Yes");
        myRef.child("Deliverers").child(deliverID).setValue(deliverer);
        Toast.makeText(this, "Auth Failed", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(checksum.this, userHomePage.class);
        mainIntent.putExtra("userinfo",user);
        mainIntent.putExtra("userID",userID);
        startActivity(mainIntent);
        finish();
    }
    @Override
    public void someUIErrorOccurred(String s) {
        deliverer.setIsFree("Yes");
        myRef.child("Deliverers").child(deliverID).setValue(deliverer);
        Toast.makeText(this, "UI error occured", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(checksum.this, userHomePage.class);
        mainIntent.putExtra("userinfo",user);
        mainIntent.putExtra("userID",userID);
        startActivity(mainIntent);
        finish();
    }
    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        deliverer.setIsFree("Yes");
        myRef.child("Deliverers").child(deliverID).setValue(deliverer);
        Toast.makeText(this, "Error Loading Web Page", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(checksum.this, userHomePage.class);
        mainIntent.putExtra("userinfo",user);
        mainIntent.putExtra("userID",userID);
        startActivity(mainIntent);
        finish();
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
    }
    @Override
    public void onBackPressedCancelTransaction() {
        deliverer.setIsFree("Yes");
        myRef.child("Deliverers").child(deliverID).setValue(deliverer);
        Toast.makeText(this, "Back Button Pressed", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(checksum.this, userHomePage.class);
        mainIntent.putExtra("userinfo",user);
        mainIntent.putExtra("userID",userID);
        startActivity(mainIntent);
        finish();
        Log.e("checksum ", " cancel call back respon  " );
    }
    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        deliverer.setIsFree("Yes");
        myRef.child("Deliverers").child(deliverID).setValue(deliverer);
        Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(checksum.this, userHomePage.class);
        mainIntent.putExtra("userinfo",user);
        mainIntent.putExtra("userID",userID);
        startActivity(mainIntent);
        finish();
        Log.e("checksum ", "  transaction cancel " );
    }


    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@-_";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 45) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
