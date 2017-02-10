package com.seostudio.vistar.testproject.fragments;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.activities.SearchResultScreenActivity;
import com.seostudio.vistar.testproject.handlers.AnekdotsSearchHandler;
import com.seostudio.vistar.testproject.loaders.AsyncMenuLoader;
import com.seostudio.vistar.testproject.models.MenuItem;
import com.seostudio.vistar.testproject.models.collections.MenuItemCollection;

import java.util.concurrent.TimeUnit;

public class SearchFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<MenuItemCollection>, Button.OnClickListener {

    public static final int LOADER_CATEGORIES = 1;
    private Loader<MenuItemCollection> mLoader;
    private Button goSearchButton;
    private Button stopSearchButton;
    private EditText searchString;
    private Spinner spinnerCategories;
    private ProgressBar progressBar;
    private TextView prgText;

    private String searchText;
    private int categoryInd;

    private SearchTask searchTask;
    private boolean needStopSearch = false;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = new Bundle();
        mLoader = this.getActivity().getSupportLoaderManager().initLoader(LOADER_CATEGORIES, bundle, this);

        goSearchButton = (Button) this.getActivity().findViewById(R.id.goSearch);
        stopSearchButton = (Button) this.getActivity().findViewById(R.id.stopSearch);
        spinnerCategories = (Spinner) this.getActivity().findViewById(R.id.spinner);
        progressBar = (ProgressBar) this.getActivity().findViewById(R.id.serachProgress);
        searchString = (EditText) this.getActivity().findViewById(R.id.searchingText);
        prgText = (TextView) this.getActivity().findViewById(R.id.prgText);
        unlockInterface();
        goSearchButton.setOnClickListener(this);
        stopSearchButton.setOnClickListener(this);
    }

    @Override
    public Loader<MenuItemCollection> onCreateLoader(int id, Bundle args) {
        Loader<MenuItemCollection> mLoader = null;
        if (id == LOADER_CATEGORIES) {
            mLoader = new AsyncMenuLoader(this.getActivity(), args);
        }
        return mLoader;
    }

    // Вызовется, когда загрузчик закончит свою работу. Вызывается в основном потоке
    @Override
    public void onLoadFinished(Loader<MenuItemCollection> loader, MenuItemCollection menuItemCollection) {
        switch (loader.getId()) {
            case LOADER_CATEGORIES:
                Spinner dropdown = (Spinner) this.getActivity().findViewById(R.id.spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity().getApplicationContext(),
                        android.R.layout.simple_spinner_item,  MenuItemCollection.lastLoaded.getArrayList());
                dropdown.setAdapter( adapter );
                dropdown.setSelection(0);//adapter.getCount() - 1);
                break;
        }
    }

    // Вызовется при уничтожении активности
    @Override
    public void onLoaderReset(Loader<MenuItemCollection> loader) {
        mLoader.cancelLoad();
    }

    @SuppressLint("ShowToast")
    public boolean getSearchParams() {
        searchText = searchString.getText().toString();
        searchText = searchText.replace("**", "*");
        searchText = searchText.replace("**", "*");
        searchText = searchText.replace("**", "*");
        searchText = searchText.replace("*", "%");
        searchText = searchText.replace("%%", "%");
        searchText = searchText.replace("%%", "%");
        searchText = searchText.replace("%%", "%");
        searchText = searchText.replace("'", "");
        searchText = searchText.replace("--", "");

        if (searchText.length() < 5) {
            Toast.makeText(this.getContext(), "Не менее 5 символов для поиска", Toast.LENGTH_LONG).show();
            return false;
        } else {
            categoryInd = (int) spinnerCategories.getSelectedItemId();
            return true;
        }
    }

    public void unlockInterface() {
        goSearchButton.setEnabled(true);
        spinnerCategories.setEnabled(true);
        searchString.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
        prgText.setVisibility(View.INVISIBLE);
    }

    public void lockInterface() {
        goSearchButton.setEnabled(false);
        spinnerCategories.setEnabled(false);
        searchString.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        prgText.setVisibility(View.VISIBLE);
    }

    public void goSearchResultsScreen() {
        Intent intent;
        intent = new Intent(this.getContext(), SearchResultScreenActivity.class);
        //intent.putExtra("READ_THEME_ID", menuItem.getThemeId());
        //intent.putExtra("READ_THEME_SHORT", menuItem.getShortName());
        //intent.putExtra("READ_THEME_FULL", menuItem.getFullName());
        //intent.putExtra("READ_THEME_COUNT", menuItem.getCnt());
        this.getContext().startActivity(intent);
        //this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goSearch:
                if (getSearchParams()) {
                    prgText.setText("Подготовка к поиску");
                    searchTask = new SearchTask();
                    searchTask.execute();
                }
                break;
            case R.id.stopSearch:
                needStopSearch = true;
                break;
        }
    }

    class SearchTask extends AsyncTask<String, String, String> {
        private String exTheme = "";
        private int cnt = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lockInterface();
            AnekdotsSearchHandler.clearResults(SearchFragment.this.getContext());
            if (categoryInd == 0) {
                stopSearchButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (categoryInd != 0) {
                MenuItem menuItem = MenuItemCollection.lastLoaded.getItemByIndex(categoryInd - 1);
                if (menuItem != null) {
                    exTheme = menuItem.getShortName();
                    publishProgress(exTheme);
                    AnekdotsSearchHandler.searchIntoTheme(SearchFragment.this.getContext(), menuItem.getId(), searchText);
                } else {
                    System.out.println("FUCK не так " + Integer.toString(categoryInd));
                    //Toast.makeText(SearchFragment.this.getContext(), "Что-то пошло не так. попробуйте перезапустить приложение.", Toast.LENGTH_LONG).show();
                }
            } else {
                for (MenuItem menuItem : MenuItemCollection.lastLoaded.getMenuItems()) {
                    exTheme = menuItem.getShortName();
                    publishProgress(exTheme);
                    AnekdotsSearchHandler.searchIntoTheme(SearchFragment.this.getContext(), menuItem.getId(), searchText);
                    if (needStopSearch) {
                        needStopSearch = false;
                        break;
                    }
                }
            }
            publishProgress("Готовим результаты...");
            cnt = AnekdotsSearchHandler.resultCount(SearchFragment.this.getContext());
            publishProgress("Готовим результаты: " + String.valueOf(cnt));
            AnekdotsSearchHandler.getResultCollection(SearchFragment.this.getContext());
            if (cnt > 0) {
                SearchFragment.this.goSearchResultsScreen();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (categoryInd == 0) {
                stopSearchButton.setVisibility(View.GONE);
            }
            unlockInterface();
            if (cnt == 0) {
                Toast.makeText(SearchFragment.this.getContext(), "По вашему запросу ничего не найдено :(", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(SearchFragment.this.getContext(), "OK :(", Toast.LENGTH_LONG).show();
            }
        }

        protected void onProgressUpdate(String... progress) {
            prgText.setText(exTheme);
        }
    }
}