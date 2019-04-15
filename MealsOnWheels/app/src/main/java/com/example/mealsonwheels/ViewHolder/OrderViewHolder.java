package com.example.mealsonwheels.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView vendor_name;
    public TextView order_price;
    public TextView orderList;
    public TextView mode_payment;
    public TextView order_date;
    public ImageView back_image;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView)
    {
        super(itemView);
        order_date = (TextView) itemView.findViewById(R.id.darte_order);
        back_image = (ImageView) itemView.findViewById(R.id.imageView4);
        order_price = (TextView) itemView.findViewById(R.id.order_price);
        mode_payment = (TextView) itemView.findViewById(R.id.order_paymentMode);
        orderList = (TextView) itemView.findViewById(R.id.order_list);
        vendor_name = (TextView) itemView.findViewById(R.id.restraunt_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
