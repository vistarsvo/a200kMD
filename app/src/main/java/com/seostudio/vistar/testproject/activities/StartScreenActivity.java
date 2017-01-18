package com.seostudio.vistar.testproject.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.loaders.BaseAsyncInit;
import com.seostudio.vistar.testproject.models.PreferencesManager;
import com.seostudio.vistar.testproject.utils.FileUtils;


public class StartScreenActivity extends AppCompatActivity {
    private static final int UI_ANIMATION_DELAY = 200;

    private View mContentView;
    private View mControlsView;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        preferencesManager = new PreferencesManager(StartScreenActivity.this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
        // If first run and no free space - exit...
        if (FileUtils.getAvailableSpaceForDataInMB(preferencesManager.getAppDataDir()) < 100L
                && preferencesManager.getIsFirstRun()
                ) {
            new AlertDialog.Builder(StartScreenActivity.this)
                    .setTitle(getResources().getString(R.string.errorTitle))
                    .setMessage(R.string.errorFreeSpace)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            new BaseAsyncInit(this).execute();
        }
    }

    public void goNextScreen() {
        Intent intent;
        if ( preferencesManager.getIsIntroRun() ) {
            intent = new Intent(this, IntroScreenActivity.class);
            preferencesManager.setIsIntoRun(false);
        } else {
            //intent = new Intent(this, IntroScreenActivity.class);
            intent = new Intent(this, MenuScreenActivity.class);
        }
        this.startActivity(intent);
        this.finish();
    }


    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);

        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
