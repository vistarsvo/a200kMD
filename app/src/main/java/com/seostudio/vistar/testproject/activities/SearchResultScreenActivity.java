package com.seostudio.vistar.testproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.adapters.SearchResultsRecyclerAdapter;
import com.seostudio.vistar.testproject.models.collections.AnekdotItemCollection;

public class SearchResultScreenActivity extends AppCompatActivity {
    private LinearLayoutManager lLayout;
    private RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_screen);

        lLayout = new LinearLayoutManager(this);
        rView = (RecyclerView) findViewById(R.id.search_result_recycler_view);
        //rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        
        SearchResultsRecyclerAdapter adapter = new SearchResultsRecyclerAdapter(this, AnekdotItemCollection.lastLoaded.getAnekdotItems());
        rView.setAdapter(adapter);
    }
}
