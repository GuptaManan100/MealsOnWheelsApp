package com.example.mealsonwheels.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.mealsonwheels.FragmentVendorMenu;
import com.example.mealsonwheels.Models.MenuItem;
import com.example.mealsonwheels.Models.Vendor;
import com.example.mealsonwheels.R;
import com.example.mealsonwheels.VendorEditItem;
import com.example.mealsonwheels.ViewHolder.VendorMenuViewHolder;
import com.example.mealsonwheels.restrauntPage;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VendorMenuAdapter extends RecyclerView.Adapter<VendorMenuViewHolder> {

    List<MenuItem> menuList;
    //List<Integer> quantity;
    Context context;
    String vendor_id;
    Vendor currVendor;

    public void setCurrVendor(Vendor currVendor) {
        this.currVendor = currVendor;
    }

    public VendorMenuAdapter(Context context, String vendor_id) {
        this.menuList = new ArrayList<>();
        this.vendor_id = vendor_id;
        this.context = context;
    }

    public void addAll(List<MenuItem> newVend)
    {
        int initsize = menuList.size();
        menuList = new ArrayList<>();
        menuList.addAll(newVend);
        notifyItemRangeChanged(initsize,newVend.size());
    }

    @NonNull
    @Override
    public VendorMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.vendor_menu_item, viewGroup, false);
        return new VendorMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VendorMenuViewHolder holder, int i) {
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

//        final Button btn_edit = holder.btn_edit;
//        btn_edit.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(context, "Hi", Toast.LENGTH_SHORT).show();
//                menuList.get(x).setQuantity(button.getNumber());
//            }
//        });

        final Button btn_delete = holder.btn_delete;
        btn_delete.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuList.remove(curr);

                //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                //DatabaseReference usersRef = mDatabase.child("Menus").child(vendor_id).child(curr.getType()).child(curr.getName()).getRef().re;

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Menus").child(vendor_id).child(curr.getType()).child(curr.getName()).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().removeValue();
                                menuList.remove(curr);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }

        });

        Button btn_edit = holder.btn_edit;
        btn_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(context, VendorEditItem.class);

                Bundle bundle = new Bundle();

                bundle.putString("name", curr.getName());
                bundle.putString("mark", curr.getMark());
                bundle.putString("price", curr.getPrice());
                bundle.putString("category", curr.getType());
                bundle.putString("isSpicy", curr.getIsSpicy());
                bundle.putString("ingredients", curr.getIngredients());
                bundle.putSerializable("vendorInfo",currVendor);
                bundle.putString("vendorID",vendor_id);
                myIntent.putExtras(bundle);

                context.startActivity(myIntent);
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

