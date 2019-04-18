package com.example.mealsonwheels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mealsonwheels.Adapter.RestrauntAdapter;
import com.example.mealsonwheels.Models.User;
import com.example.mealsonwheels.Models.Vendor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class exploreFragment extends Fragment {
    private User currUser;
    private String id;

    private SearchView searchView;
    private RecyclerView recycler_restraunt;
    private RestrauntAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        id = bundle.getString("userID");
        currUser = (User) bundle.getSerializable("userinfo");
        return inflater.inflate(R.layout.fragment_explore,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_restraunt = (RecyclerView) view.findViewById(R.id.recycler_restraunt);
        searchView = (SearchView) view.findViewById(R.id.searchView2);
        recycler_restraunt.hasFixedSize();
        recycler_restraunt.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RestrauntAdapter(getActivity());
        adapter.setCurrUser(currUser);
        adapter.setUserID(id);
        recycler_restraunt.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                loadRestraunts(searchView.getQuery());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        //loadRestraunts();
    }

    private void loadRestraunts(final CharSequence searchString) {
        Log.d("Load Restraunts","Started Load");

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Vendors");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Vendor> Newvendors = new ArrayList<>();
                adapter.clearAll();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Vendor curr = child.getValue(Vendor.class);
                    if(curr.getName().toLowerCase().contains(searchString.toString().toLowerCase())) {
                        Newvendors.add(curr);
                    }
                }
                adapter.addAll(Newvendors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
