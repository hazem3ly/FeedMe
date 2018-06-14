package com.neway.feedme.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neway.feedme.R;
import com.neway.feedme.interfaces.OnItemClickListener;

/**
 * Created by Hazem Ali
 * On 5/7/2018.
 */
public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView menu_image;
    public TextView menu_text;
    public OnItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        menu_image = itemView.findViewById(R.id.menu_image);
        menu_text = itemView.findViewById(R.id.menu_name);

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null)
            itemClickListener.onClick(v, getAdapterPosition(), false);
    }

}
