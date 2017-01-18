package com.seostudio.vistar.testproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.adapters.RecyclerViewAdapter;
import com.seostudio.vistar.testproject.loaders.AsyncMenuLoader;
import com.seostudio.vistar.testproject.models.collections.MenuItemCollection;

public class MenuFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<MenuItemCollection> {

    public static final int LOADER_ID = 1;
    private Loader<MenuItemCollection> mLoader;

    private GridLayoutManager lLayout;
    private RecyclerView rView;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lLayout = new GridLayoutManager(this.getActivity(), 4);
        rView = (RecyclerView)this.getActivity().findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        Bundle bundle = new Bundle();
        mLoader = this.getActivity().getSupportLoaderManager().initLoader(LOADER_ID, bundle, this);
    }


    @Override
    public Loader<MenuItemCollection> onCreateLoader(int id, Bundle args) {
        Loader<MenuItemCollection> mLoader = null;
        if (id == LOADER_ID) {
            mLoader = new AsyncMenuLoader(this.getActivity(), args);
        }
        return mLoader;
    }

    // Вызовется, когда загрузчик закончит свою работу. Вызывается в основном потоке
    @Override
    public void onLoadFinished(Loader<MenuItemCollection> loader, MenuItemCollection menuItemCollection) {
        switch (loader.getId()) {
            case LOADER_ID:
                RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(this.getActivity(), menuItemCollection.getMenuItems());
                rView.setAdapter(rcAdapter);
                break;
        }
    }

    // Вызовется при уничтожении активности
    @Override
    public void onLoaderReset(Loader<MenuItemCollection> loader) {
        mLoader.cancelLoad();
    }

}
//mLoader.onContentChanged();