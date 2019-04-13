package com.example.mealsonwheels.ViewHolder;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.R;

import org.w3c.dom.Text;

public class RestrauntViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView res_name;
    public TextView res_address;
    public TextView res_type;
    public ImageView res_image;

    private ItemClickListener itemClickListener;

    public RestrauntViewHolder(View itemView)
    {
        super(itemView);
        res_name = (TextView) itemView.findViewById(R.id.restraunt_name);
        res_image = (ImageView) itemView.findViewById(R.id.restraunt_image);
        res_address = (TextView) itemView.findViewById(R.id.restraunt_address);
        res_type = (TextView) itemView.findViewById(R.id.restraunt_type);
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
