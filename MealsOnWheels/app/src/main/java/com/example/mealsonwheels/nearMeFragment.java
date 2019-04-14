package com.example.mealsonwheels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealsonwheels.Adapter.RestrauntAdapter;
import com.example.mealsonwheels.Interfaces.ItemClickListener;
import com.example.mealsonwheels.Models.User;
import com.example.mealsonwheels.Models.Vendor;
import com.example.mealsonwheels.ViewHolder.RestrauntViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class nearMeFragment extends Fragment {

    private TextView textV;
    private User currUser;
    private String id;
    private String city;

    private RecyclerView recycler_restraunt;
    private RestrauntAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        id = bundle.getString("userID");
        currUser = (User) bundle.getSerializable("userinfo");
        return inflater.inflate(R.layout.fragment_near_me,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textV = (TextView) view.findViewById(R.id.textView);
        textV.setText(currUser.getDeliveryAddress());
        city = splitString(currUser.getDeliveryAddress());
        recycler_restraunt = (RecyclerView) view.findViewById(R.id.recycler_restraunt);
        recycler_restraunt.hasFixedSize();
        recycler_restraunt.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RestrauntAdapter(getActivity());
        recycler_restraunt.setAdapter(adapter);
        loadRestraunts();
    }

    private void loadRestraunts() {
        Log.d("Load Restraunts","Started Load");

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Vendors");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Vendor> Newvendors = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Vendor curr = child.getValue(Vendor.class);
                    if(city.trim().equals(splitString(curr.getAddress()).trim())) {
                        Newvendors.add(curr);
                    }
                }
                adapter.addAll(Newvendors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*FirebaseRecyclerOptions<Vendor> options =
                new FirebaseRecyclerOptions.Builder<Vendor>()
                        .setQuery(query, Vendor.class)
                        .build();
        Log.d("Load Restraunts",options.toString());
        FirebaseRecyclerAdapter<Vendor, RestrauntViewHolder> adapter = new FirebaseRecyclerAdapter<Vendor, RestrauntViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RestrauntViewHolder holder, int position, @NonNull Vendor model) {
                Log.d("Load Restraunts","Binding View Holder");
                holder.res_name.setText(model.getName());
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(), "It works!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public RestrauntViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                Log.d("Load Restraunts","ViewHolderCreated");
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.restraunt_item, viewGroup, false);
                return new RestrauntViewHolder(view);
            }
        };

        Log.d("Load Restraunts",""+adapter.getItemCount());
        Log.d("Load Restraunts",""+adapter.toString());

        */
    }

    private String splitString(String address) {
        int siz = address.length();
        int index = siz-1;
        for(int j=0;j<siz;j++)
        {
            if(address.charAt(j)==',')
            {
                index = j;
            }
        }
        return address.substring(index+1);
    }
}
