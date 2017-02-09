package com.seostudio.vistar.testproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.holders.SearchResultViewHolders;
import com.seostudio.vistar.testproject.models.AnekdotItem;

import java.util.List;

public class SearchResultsRecyclerAdapter extends RecyclerView.Adapter<SearchResultViewHolders> {
    private List<AnekdotItem> anekdotItems;
    private Context context;

    public SearchResultsRecyclerAdapter(Context context, List<AnekdotItem> anekdotItems) {
        this.anekdotItems = anekdotItems;
        this.context = context;
    }

    @Override
    public SearchResultViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.anekdot_cart_view_item, null);
        SearchResultViewHolders srh = new SearchResultViewHolders(layoutView);
        return srh;
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolders holder, int position) {
        holder.themeName.setText(Integer.toString(anekdotItems.get(position).getTheme_id()));
        holder.anekdotText.setText(anekdotItems.get(position).getText());
        //int drawableResourceId = context.getResources().getIdentifier("chapter_" + Integer.toString(itemList.get(position).getId()), "mipmap", context.getPackageName());
        //holder.itemPicture.setImageResource(drawableResourceId);
    }

    @Override
    public int getItemCount() {
        return this.anekdotItems.size();
    }
}
