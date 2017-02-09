package com.seostudio.vistar.testproject.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.models.AnekdotItem;
import com.seostudio.vistar.testproject.models.collections.MenuItemCollection;

public class SearchResultViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView themeName;
    public TextView anekdotText;
    //public ImageView itemPicture;

    public SearchResultViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        themeName = (TextView)itemView.findViewById(R.id.acw_theme_name);
        anekdotText = (TextView)itemView.findViewById(R.id.acw_anekdot_text);
        //itemPicture = (ImageView)itemView.findViewById(R.id.menu_item_picture);
    }

    @Override
    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        //AnekdotItem anekdotItem = MenuItemCollection.lastLoaded.getMenuItems().get(clickedPosition);
        //goSingleReadScreen(menuItem, view);
        Toast.makeText(view.getContext(), anekdotText.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    /*
    public void goSingleReadScreen(MenuItem menuItem, View view) {
        Intent intent;
        intent = new Intent(view.getContext(), SingleReadActivity.class);
        intent.putExtra("READ_THEME_ID", menuItem.getThemeId());
        intent.putExtra("READ_THEME_SHORT", menuItem.getShortName());
        intent.putExtra("READ_THEME_FULL", menuItem.getFullName());
        intent.putExtra("READ_THEME_COUNT", menuItem.getCnt());
        view.getContext().startActivity(intent);
        //this.finish();
    }*/
}
