package com.example.mealsonwheels.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealsonwheels.R;

public class VendorMenuViewHolder extends RecyclerView.ViewHolder {

    public TextView item_name;
    public TextView item_price;
    public TextView item_ingredients;
    public ImageView item_image;
    public  ImageView image_nonVeg;
    public  ImageView image_spicy;

    public Button btn_edit;
    public Button btn_delete;

    public VendorMenuViewHolder(View itemView)
    {
        super(itemView);
        item_name = (TextView) itemView.findViewById(R.id.item_name);
        item_image = (ImageView) itemView.findViewById(R.id.items_image);
        item_price = (TextView) itemView.findViewById(R.id.item_price);
        item_ingredients = (TextView) itemView.findViewById(R.id.item_ingredients);
        image_nonVeg = (ImageView) itemView.findViewById(R.id.image_nonVeg);
        image_spicy = (ImageView) itemView.findViewById(R.id.image_isSpicy);

        btn_edit = (Button) itemView.findViewById(R.id.btn_edit);
        btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
    }

}
