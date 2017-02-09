package com.seostudio.vistar.testproject.holders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.activities.MenuScreenActivity;
import com.seostudio.vistar.testproject.activities.SingleReadActivity;
import com.seostudio.vistar.testproject.models.MenuItem;
import com.seostudio.vistar.testproject.models.collections.MenuItemCollection;

public class MenuItemViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView itemName;
    public TextView itemCount;
    public ImageView itemPicture;

    public MenuItemViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemName = (TextView)itemView.findViewById(R.id.menu_item_name);
        itemCount = (TextView)itemView.findViewById(R.id.menu_item_count);
        itemPicture = (ImageView)itemView.findViewById(R.id.menu_item_picture);
    }

    @Override
    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        MenuItem menuItem = MenuItemCollection.lastLoaded.getMenuItems().get(clickedPosition);
        goSingleReadScreen(menuItem, view);
        Toast.makeText(view.getContext(), menuItem.getFullName(), Toast.LENGTH_SHORT).show();

    }

    public void goSingleReadScreen(MenuItem menuItem, View view) {
        Intent intent;
        intent = new Intent(view.getContext(), SingleReadActivity.class);
        intent.putExtra("READ_THEME_ID", menuItem.getThemeId());
        intent.putExtra("READ_THEME_SHORT", menuItem.getShortName());
        intent.putExtra("READ_THEME_FULL", menuItem.getFullName());
        intent.putExtra("READ_THEME_COUNT", menuItem.getCnt());
        view.getContext().startActivity(intent);
        //this.finish();
    }
}
