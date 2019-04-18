package com.example.mealsonwheels.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mealsonwheels.R;

public class VendorReviewViewHolder extends RecyclerView.ViewHolder {

    public RatingBar view_rating;
    public EditText view_review;


    public VendorReviewViewHolder(View itemView)
    {
        super(itemView);
        view_rating = (RatingBar) itemView.findViewById(R.id.ratingBar);
        view_review = (EditText) itemView.findViewById(R.id.reviewEditText);
    }

}
