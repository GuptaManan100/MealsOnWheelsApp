package com.example.mealsonwheels.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.Models.MenuItem;
import com.example.mealsonwheels.Models.Vendor;
import com.example.mealsonwheels.R;
import com.example.mealsonwheels.ViewHolder.MenuViewHolder;
import com.example.mealsonwheels.ViewHolder.RestrauntViewHolder;
import com.example.mealsonwheels.restrauntPage;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    List<MenuItem> menuList;
    //List<Integer> quantity;
    Context context;

    public MenuAdapter(Context context) {
        this.menuList = new ArrayList<>();
        //this.quantity = new ArrayList<>();
        this.context = context;
    }

    public void addAll(List<MenuItem> newVend)
    {
        int initsize = menuList.size();
        menuList.addAll(newVend);
        notifyItemRangeChanged(initsize,newVend.size());
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, viewGroup, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int i) {
        final MenuItem curr = menuList.get(i);
        final int x = i;
        holder.item_name.setText(curr.getName());
        holder.item_ingredients.setText(curr.getIngredients());
        holder.item_price.setText("Rs. "+curr.getPrice());
        if(curr.getIsSpicy()!=null){
            if(curr.getIsSpicy().equals("Yes"))
            {
                holder.image_spicy.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.image_spicy.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            holder.image_spicy.setVisibility(View.INVISIBLE);
        }

        if(curr.getMark()!=null)
        {
            if(curr.getMark().equals("NonVeg"))
            {
                holder.image_nonVeg.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.image_nonVeg.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            holder.image_nonVeg.setVisibility(View.INVISIBLE);
        }
        if(curr.getType().equals("Beverages"))
        {
            holder.item_image.setImageResource(R.drawable.beverage);
        }
        else if(curr.getType().equals("Dessert"))
        {
            holder.item_image.setImageResource(R.drawable.desert);
        }
        else
        {
            holder.item_image.setImageResource(R.drawable.maincourse);
        }

        final ElegantNumberButton button = holder.item_quantity;
        button.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show();
                menuList.get(x).setQuantity(button.getNumber());
            }
        });
    }

    public List<MenuItem>getMenu()
    {
        return menuList;
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
