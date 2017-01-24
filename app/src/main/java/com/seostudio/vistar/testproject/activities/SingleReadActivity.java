package com.seostudio.vistar.testproject.activities;



import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.handlers.AnekdotItemHandler;
import com.seostudio.vistar.testproject.loaders.AsyncAnekdotLoader;
import com.seostudio.vistar.testproject.models.AnekdotItem;
import com.seostudio.vistar.testproject.models.PreferencesManager;
import com.seostudio.vistar.testproject.models.collections.AnekdotItemCollection;
import com.seostudio.vistar.testproject.utils.IntentFilterUtils;

import java.util.ArrayList;
import java.util.Collection;

public class SingleReadActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LoaderManager.LoaderCallbacks<AnekdotItemCollection> {

    private TextView singleReadTextView;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector myGestDetector;
    private PreferencesManager preferencesManager;

    private int theme_id  = 0;
    private int theme_cnt  = 0;
    private String theme_short = "";
    private String theme_full = "";

    public static final int LOADER_ID = 2;
    private Loader<AnekdotItemCollection> mLoader;
    private AnekdotItem anekdotItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesManager = new PreferencesManager(this);
        setContentView(R.layout.activity_single_read);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        singleReadTextView = (TextView)findViewById(R.id.singleReadTextView);
        singleReadTextView.setTextSize(preferencesManager.getSingleReadFontSize());
        singleReadTextView.setMovementMethod(new ScrollingMovementMethod());
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());
        myGestDetector = new GestureDetector(this, onGestureListener);

        singleReadTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                event.setLocation(event.getRawX(), event.getRawY());
                scaleGestureDetector.onTouchEvent(event);
                myGestDetector.onTouchEvent(event);
                return true;
            }
        });
        singleReadTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, preferencesManager.getSingleReadFontSize());

        Intent intent = getIntent();
        theme_id = intent.getIntExtra("READ_THEME_ID", 1);
        theme_cnt = Integer.parseInt(intent.getStringExtra("READ_THEME_COUNT"));
        theme_short = intent.getStringExtra("READ_THEME_SHORT");
        theme_full = intent.getStringExtra("READ_THEME_FULL");

        getSupportActionBar().setTitle(theme_full);
        getSupportActionBar().setSubtitle("0 из " + Integer.toString(theme_cnt));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readSingleAnekdot(0);
    }

    // Read Anekdot. Last readed or Prev-next
    // Run Async Data Loader
    private void readSingleAnekdot(int vector) {
        int last = preferencesManager.getInt("theme_last_readed_" + Integer.toString(theme_id));
        String query = AnekdotItemHandler.anekdotGetQuery(last, vector, theme_id);
        Bundle bundle = new Bundle();
        bundle.putInt("count", 1);
        bundle.putString("query", query);
        if (mLoader == null) {
            mLoader = getSupportLoaderManager().initLoader(LOADER_ID, bundle, this);
        } else {
            getSupportLoaderManager().restartLoader(LOADER_ID, bundle, this);
        }

    }

    @Override
    public Loader<AnekdotItemCollection> onCreateLoader(int id, Bundle args) {
        Loader<AnekdotItemCollection> mLoader = null;
        if (id == LOADER_ID) {
            mLoader = new AsyncAnekdotLoader(this, args);
        }
        return mLoader;
    }

    // Вызовется, когда загрузчик закончит свою работу. Вызывается в основном потоке
    @Override
    public void onLoadFinished(Loader<AnekdotItemCollection> loader, AnekdotItemCollection anekdotItemCollection) {
        switch (loader.getId()) {
            case LOADER_ID:
                anekdotItem = anekdotItemCollection.getAnekdotItems().get(0);
                singleReadTextView.setText(anekdotItem.getText());
                getSupportActionBar().setSubtitle(Integer.toString(anekdotItem.getNum()) + " из " + Integer.toString(theme_cnt));
                singleReadTextView.setText(anekdotItem.getText());
                if (anekdotItem.getFavorite_id() > 0 ) {
                    singleReadTextView.append("\n FAVORITE: " + anekdotItem.getFavorite_date());
                }
                preferencesManager.setInt("theme_last_readed_" + Integer.toString(theme_id), anekdotItem.getId());
                singleReadTextView.setScrollY(0);
                break;
        }
    }

    // Вызовется при уничтожении активности
    @Override
    public void onLoaderReset(Loader<AnekdotItemCollection> loader) {
        mLoader.cancelLoad();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        myGestDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.menu_addtofavorite) {
            addToFavorite();
            readSingleAnekdot(0);
        } else if (id == R.id.menu_shareitem) {
            callShareDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent getMessageIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "Message");
        intent.setType("text/plain");
        return intent;
    }

    private void callShareDialog() {
        Intent messageIntent = getMessageIntent();
        Collection<ResolveInfo> allMatching = IntentFilterUtils.findAllMatching(getPackageManager(), messageIntent);
        startCustomPicker(messageIntent, allMatching);
    }

    private void startCustomPicker(Intent messageIntent, Collection<ResolveInfo> allMatching) {
        Intent shareIntent = new Intent(this, CustomShareActivity.class);
        shareIntent.putExtra(CustomShareActivity.MESSAGE_INTENT_EXTRA, messageIntent);
        shareIntent.putParcelableArrayListExtra(CustomShareActivity.APPS_LIST_EXTRA, new ArrayList<>(allMatching));
        startActivity(shareIntent);
    }

    private boolean addToFavorite() {
        // Not in favorites
        if (anekdotItem.getFavorite_id() == 0) {
            AnekdotItemHandler.anekdotFavoriteAdd(anekdotItem, this);
            Toast.makeText(SingleReadActivity.this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
        } else {
        // In favorites
            AnekdotItemHandler.anekdotFavoriteRemove(anekdotItem, this);
            Toast.makeText(SingleReadActivity.this, "Удалено из избранного", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    float ScaleText = 32;
    float product = 32;
    boolean canScroll = true;
    public class simpleOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            canScroll = false;
            float size = singleReadTextView.getTextSize();
            float factor = detector.getScaleFactor();
            product = (float) Math.round(size*factor * 100) / 100;
            if (product < 6f) product = 6f;
            if (product > 96f) product = 96f;
            ScaleText = product;
            product = (float) Math.round(product);
            singleReadTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);
            preferencesManager.setSingleReadFontSize(product);
            canScroll = true;
            return true;
        }
    }

    private GestureDetector.SimpleOnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //Log.i("gestureDebug333", "doubleTapped:" + e);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            //Log.i("gestureDebug333", "doubleTappedEvent:" + e);
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            //Log.i("gestureDebug333", "onDown:" + e);
            return super.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //Log.i("gestureDebug333", "flinged:" + e1 + "---" + e2);
            //Log.i("gestureDebug333", "fling velocity:" + velocityX + "---" + velocityY);
            if (e1.getAction() == MotionEvent.ACTION_DOWN && e1.getX() > (e2.getX() + 250)) {
                Toast.makeText(SingleReadActivity.this, "Следующий", Toast.LENGTH_SHORT).show();
                readSingleAnekdot(1);
            }
            if (e1.getAction() == MotionEvent.ACTION_DOWN && e2.getX() > (e1.getX() + 250)) {
                Toast.makeText(SingleReadActivity.this, "Предыдущий", Toast.LENGTH_SHORT).show();
                readSingleAnekdot(-1);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (canScroll) {
                int scrollAmount = 0;
                int linesCount = singleReadTextView.getLineCount();
                if (linesCount > 2) {
                    scrollAmount = singleReadTextView.getLayout().getLineTop(linesCount) - singleReadTextView.getHeight();
                    int nowScroll = singleReadTextView.getScrollY();
                    float newScroll = nowScroll + distanceY;
                    if (newScroll > scrollAmount + 100) newScroll = scrollAmount + 100;
                    if (newScroll < 0) newScroll = 0;
                    singleReadTextView.setScrollY(Math.round(newScroll));
                }
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }
    };
}
