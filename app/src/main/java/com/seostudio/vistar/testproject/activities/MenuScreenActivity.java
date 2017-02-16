package com.seostudio.vistar.testproject.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.seostudio.vistar.testproject.handlers.DrawbleThemeHandler;
import com.seostudio.vistar.testproject.loaders.AsyncCensorLoader;
import com.seostudio.vistar.testproject.models.PreferencesManager;
import com.seostudio.vistar.testproject.models.collections.CensorItemCollection;
import com.seostudio.vistar.testproject.models.collections.DrawbleCollection;


public class MenuScreenActivity extends AppCompatActivity
implements  LoaderManager.LoaderCallbacks<CensorItemCollection> {

    private Loader<CensorItemCollection> mLoader;
    public static final int LOADER_ID = 3;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PreferencesManager preferencesManager;
    private int tabsBackground;
    private int actionBarBackground;
    private DrawbleCollection drawbleCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesManager = new PreferencesManager(this);
        drawbleCollection = DrawbleCollection.getInstance(this);
        drawbleCollection.initDrawbles();
        setStyles();
        setContentView(R.layout.activity_menu_screen);

        toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);
        toolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), actionBarBackground, null));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setBackgroundColor(ResourcesCompat.getColor(getResources(), tabsBackground, null));
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

        Bundle bundle = new Bundle();
        mLoader = getSupportLoaderManager().initLoader(LOADER_ID, bundle, this);
    }

    private void setStyles() {
        switch (preferencesManager.getString("themeName", "NiceLightBlue")) {
            case "NiceLightBlue" :
                setTheme(R.style.NiceLightBlue);
                tabsBackground = R.color.nicelightblue_tab_color;
                actionBarBackground = R.color.nicelightblue_toolbar_color;
                drawbleCollection.colorizedCollection( ResourcesCompat.getColor(getResources(), R.color.nicelightblue_icons, null)  );
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MenuFragment(), getString(R.string.MainMenu));
        adapter.addFragment(new FavoritesFragment(), getString(R.string.Favorites));
        adapter.addFragment(new SearchFragment(), getString(R.string.Search));
        adapter.addFragment(new OptionsFragment(), getString(R.string.Settings));
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(R.layout.custom_tab);
        tabLayout.getTabAt(1).setCustomView(R.layout.custom_tab);
        tabLayout.getTabAt(2).setCustomView(R.layout.custom_tab);
        tabLayout.getTabAt(3).setCustomView(R.layout.custom_tab);

        setNameIconTab(0, R.string.MainMenu, "ic_tab_menu");
        setNameIconTab(1, R.string.Favorites, "ic_tab_favorites");
        setNameIconTab(2, R.string.Search, "ic_tab_search");
        setNameIconTab(3, R.string.Settings, "ic_tab_options");
    }

    private void setNameIconTab(int tabNum, int stringRes, String iconName) {
        View view = tabLayout.getTabAt(tabNum).getCustomView();
        TextView title = (TextView) view.findViewById(R.id.tabContent);
        ImageView img = (ImageView) view.findViewById(R.id.tabImage);
        img.setImageDrawable(drawbleCollection.getDrawble(iconName));
        title.setText(getString(stringRes));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<CensorItemCollection> onCreateLoader(int id, Bundle args) {
        Loader<CensorItemCollection> mLoader = null;
        if (id == LOADER_ID) {
            mLoader = new AsyncCensorLoader(this, args);
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<CensorItemCollection> loader, CensorItemCollection censorItemCollection) {
        switch (loader.getId()) {
            case LOADER_ID:
                CensorItemCollection.lastLoaded = censorItemCollection;
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<CensorItemCollection> loader) {
        mLoader.cancelLoad();
    }
}
