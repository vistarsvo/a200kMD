package com.seostudio.vistar.testproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.holders.RecyclerViewHolders;
import com.seostudio.vistar.testproject.models.MenuItem;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<MenuItem> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<MenuItem> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card_view_list_litem, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.itemName.setText(itemList.get(position).getShortName());
        holder.itemCount.setText(itemList.get(position).getCnt());
        int drawableResourceId = context.getResources().getIdentifier("chapter_" + Integer.toString(itemList.get(position).getId()), "mipmap", context.getPackageName());
        holder.itemPicture.setImageResource(drawableResourceId);
        //holder.itemPicture.setImageResource(itemList.get(position).getPicture());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

