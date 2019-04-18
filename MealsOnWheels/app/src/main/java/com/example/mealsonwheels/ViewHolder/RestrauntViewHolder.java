package com.example.mealsonwheels.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.R;

public class RestrauntViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView res_name;
    public TextView res_address;
    public TextView res_type;
    public ImageView res_image;
    public TextView res_rating;
    public TextView res_avgPrice;

    private ItemClickListener itemClickListener;

    public RestrauntViewHolder(View itemView)
    {
        super(itemView);
        res_name = (TextView) itemView.findViewById(R.id.transaction_id);
        res_image = (ImageView) itemView.findViewById(R.id.item_image);
        res_address = (TextView) itemView.findViewById(R.id.restraunt_address);
        res_type = (TextView) itemView.findViewById(R.id.restraunt_type);
        res_avgPrice = (TextView) itemView.findViewById(R.id.restrunt_avgPrice);
        res_rating = (TextView) itemView.findViewById(R.id.restraunt_rating);
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
