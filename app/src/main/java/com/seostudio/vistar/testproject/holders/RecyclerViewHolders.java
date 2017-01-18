package com.seostudio.vistar.testproject.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seostudio.vistar.testproject.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView itemName;
    public TextView itemCount;
    public ImageView itemPicture;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemName = (TextView)itemView.findViewById(R.id.menu_item_name);
        itemCount = (TextView)itemView.findViewById(R.id.menu_item_count);
        itemPicture = (ImageView)itemView.findViewById(R.id.menu_item_picture);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
