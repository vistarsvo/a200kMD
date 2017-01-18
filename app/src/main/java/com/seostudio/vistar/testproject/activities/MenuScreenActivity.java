package com.seostudio.vistar.testproject.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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


        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(R.layout.custom_tab);
        tabLayout.getTabAt(1).setCustomView(R.layout.custom_tab);
        tabLayout.getTabAt(2).setCustomView(R.layout.custom_tab);
        tabLayout.getTabAt(3).setCustomView(R.layout.custom_tab);

        View tab1_view = tabLayout.getTabAt(0).getCustomView();
        TextView tab1_title = (TextView) tab1_view.findViewById(R.id.tabContent);
        ImageView tab1_img = (ImageView) tab1_view.findViewById(R.id.tabImage);
        tab1_img.setImageResource(R.drawable.ic_tab_menu);
        tab1_title.setText(getString(R.string.MainMenu));

        View tab2_view = tabLayout.getTabAt(1).getCustomView();
        TextView tab2_title = (TextView) tab2_view.findViewById(R.id.tabContent);
        ImageView tab2_img = (ImageView) tab2_view.findViewById(R.id.tabImage);
        tab2_img.setImageResource(R.drawable.ic_tab_favorites);
        tab2_title.setText(getString(R.string.Favorites));

        View tab3_view = tabLayout.getTabAt(2).getCustomView();
        TextView tab3_title = (TextView) tab3_view.findViewById(R.id.tabContent);
        ImageView tab3_img = (ImageView) tab3_view.findViewById(R.id.tabImage);
        tab3_img.setImageResource(R.drawable.ic_tab_search);
        tab3_title.setText(getString(R.string.Search));

        View tab4_view = tabLayout.getTabAt(3).getCustomView();
        TextView tab4_title = (TextView) tab4_view.findViewById(R.id.tabContent);
        ImageView tab4_img = (ImageView) tab4_view.findViewById(R.id.tabImage);
        tab4_img.setImageResource(R.drawable.ic_tab_options);
        tab4_title.setText(getString(R.string.Settings));





        //ImageView img1 = (ImageView) tab1_view.findViewById(R.id.img);

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
