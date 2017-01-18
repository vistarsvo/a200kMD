package com.seostudio.vistar.testproject.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.seostudio.vistar.testproject.R;

import com.seostudio.vistar.testproject.adapters.ViewPagerAdapter;
import com.seostudio.vistar.testproject.fragments.FavoritesFragment;
import com.seostudio.vistar.testproject.fragments.MenuFragment;
import com.seostudio.vistar.testproject.fragments.OptionsFragment;
import com.seostudio.vistar.testproject.fragments.SearchFragment;
import com.seostudio.vistar.testproject.models.collections.MenuItemCollection;


public class MenuScreenActivity extends AppCompatActivity  {

    private Loader<MenuItemCollection> mLoader;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MenuFragment(), getString(R.string.MainMenu));
        adapter.addFragment(new FavoritesFragment(), getString(R.string.Favorites));
        adapter.addFragment(new SearchFragment(), getString(R.string.Search));
        adapter.addFragment(new OptionsFragment(), getString(R.string.Settings));

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_tab_switch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_switch:
                Intent intent = new Intent(TabAnimationActivity.this, TabsHeaderActivity.class);
                startActivity(intent);
                return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}
