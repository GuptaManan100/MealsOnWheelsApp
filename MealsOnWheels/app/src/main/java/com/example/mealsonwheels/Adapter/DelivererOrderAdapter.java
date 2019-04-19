package com.example.mealsonwheels.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mealsonwheels.Models.DelivererHistory;
import com.example.mealsonwheels.R;

import java.util.List;

public class DelivererOrderAdapter extends RecyclerView.Adapter<DelivererOrderAdapter.MyHoder>{

    List<DelivererHistory> list;
    Context context;

    public DelivererOrderAdapter(List<DelivererHistory> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.deliverer_history,parent,false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        DelivererHistory mylist = list.get(position);
        holder.date.setText(mylist.getDate());
        //holder.customer.setText(mylist.getName());
        holder.vendorName.setText(mylist.getVendorName());
        holder.transactionId.setText(mylist.getTransactionId());
        holder.amount.setText(mylist.getTotalAmount());
        holder.mode.setText(mylist.getPaymentMode());
        //holder.item.setText(mylist.getItemsOrdered().toString());
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try{
            if(list.size()==0){
                arr = 0;
            }
            else{
                arr=list.size();
            }
        }catch (Exception e){

        }
        return arr;
    }

    class MyHoder extends RecyclerView.ViewHolder{
        TextView date;
        TextView customer;
        TextView vendorName;
        TextView transactionId;
        TextView amount;
        TextView mode;
        TextView item;
        TextView qty;
        TextView price;

        public MyHoder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.order_date);
            //customer = (TextView) itemView.findViewById(R.id.order_customer);
            vendorName = (TextView) itemView.findViewById(R.id.order_vendorName);
            transactionId = (TextView) itemView.findViewById(R.id.order_transactionId);
            amount = (TextView) itemView.findViewById(R.id.order_totalAmount);
            mode = (TextView) itemView.findViewById(R.id.order_payment_mode);
            //item = (TextView) itemView.findViewById(R.id.order_item);
            //qty = (TextView) itemView.findViewById(R.id.order_quantity);
            //price = (TextView) itemView.findViewById(R.id.order_item_price);
        }
    }

}
