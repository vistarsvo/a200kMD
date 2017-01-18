package com.seostudio.vistar.testproject.loaders;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.activities.StartScreenActivity;
import com.seostudio.vistar.testproject.models.PreferencesManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BaseAsyncInit extends AsyncTask
{
    private TextView progressText;
    private ProgressBar progressBar;
    private ViewSwitcher viewSwitcher;
    private StartScreenActivity startScreenActivity;
    private PreferencesManager preferencesManager;

    private String hasError = "";

    public BaseAsyncInit(StartScreenActivity startScreenActivity) {
        this.startScreenActivity = startScreenActivity;
        this.preferencesManager = new PreferencesManager(startScreenActivity);
    }

    @Override
    protected void onPreExecute()
    {
        viewSwitcher = new ViewSwitcher(startScreenActivity);
        viewSwitcher.addView(ViewSwitcher.inflate(startScreenActivity, R.layout.activity_start_screen, null));
        progressText = (TextView) viewSwitcher.findViewById(R.id.progressText);
        progressBar = (ProgressBar) viewSwitcher.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        startScreenActivity.setContentView(viewSwitcher);
        progressBar.setVisibility(View.VISIBLE);
        progressText.setVisibility(View.VISIBLE);
    }

    //The code to be executed in a background thread.
    @Override
    protected Object doInBackground(Object[] objects) {
            synchronized (this)
            {
                boolean isFirstrRun = preferencesManager.getIsFirstRun();
                if (isFirstrRun) {
                    try {
                        InputStream myInput = this.startScreenActivity.getAssets().open(preferencesManager.getDbName());
                        String outFileName = preferencesManager.getAppDataDir() + preferencesManager.getDbName();
                        OutputStream myOutput = new FileOutputStream(outFileName);
                        byte[] buffer = new byte[1024]; //1 kbyte
                        int length;
                        int counter = 0;
                        while ((length = myInput.read(buffer)) > 0){
                            myOutput.write(buffer, 0, length);
                            publishProgress(counter / (44347 / 100));
                            counter++;
                        }
                        myOutput.flush();
                        myOutput.close();
                        myInput.close();
                        preferencesManager.setIsFirstRun(false);
                    } catch (IOException e) {
                        hasError = this.startScreenActivity.getResources().getString(R.string.initError);
                    }
                } else {
                    //System.out.println("Not first run");
                    //hasError = "Not first run";
                }
            }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object[] values)
    {
        if((Integer)values[0] <= 100)
        {
            progressText.setText(this.startScreenActivity.getResources().getString(R.string.initDB) + ": " + Integer.toString((Integer)values[0]) + "%");
            progressBar.setProgress((Integer)values[0]);
        }
    }

    //After executing the code in the thread
    @Override
    protected void onPostExecute(Object o)
    {
        progressBar.setVisibility(View.INVISIBLE);
        progressText.setVisibility(View.INVISIBLE);
        if (!hasError.equals("")) {
            new AlertDialog.Builder(this.startScreenActivity)
                    .setTitle(this.startScreenActivity.getResources().getString(R.string.errorTitle))
                    .setMessage(hasError)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startScreenActivity.finish();
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            this.startScreenActivity.goNextScreen();
        }
    }
}
