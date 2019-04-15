package com.example.mealsonwheels.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    public TextView item_name;
    public TextView item_price;
    public TextView item_ingredients;
    public ImageView item_image;
    public ElegantNumberButton item_quantity;
    public  ImageView image_nonVeg;
    public  ImageView image_spicy;

    public MenuViewHolder(View itemView)
    {
        super(itemView);
        item_name = (TextView) itemView.findViewById(R.id.item_name);
        item_image = (ImageView) itemView.findViewById(R.id.items_image);
        item_price = (TextView) itemView.findViewById(R.id.item_price);
        item_ingredients = (TextView) itemView.findViewById(R.id.item_ingredients);
        item_quantity = (ElegantNumberButton) itemView.findViewById(R.id.item_quantity);
        image_nonVeg = (ImageView) itemView.findViewById(R.id.image_nonVeg);
        image_spicy = (ImageView) itemView.findViewById(R.id.image_isSpicy);
    }

}
