package com.neway.feedme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neway.feedme.R;
import com.neway.feedme.interfaces.OnItemClickListener;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class FoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView food_image;
    public TextView food_text;
    public OnItemClickListener itemClickListener;

    public FoodsViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        food_image = itemView.findViewById(R.id.food_image);
        food_text = itemView.findViewById(R.id.food_name);

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
