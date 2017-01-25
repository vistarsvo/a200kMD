package com.seostudio.vistar.testproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.seostudio.vistar.testproject.R;
import com.seostudio.vistar.testproject.models.PreferencesManager;

public class OptionsFragment extends Fragment
implements CompoundButton.OnCheckedChangeListener
{
    View optionsView;
    PreferencesManager preferencesManager;
    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        optionsView = inflater.inflate(R.layout.fragment_options, container, false);
        return optionsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferencesManager = new PreferencesManager(this.getContext().getApplicationContext());
        setListenersAndState();
    }

    //optionsEnableSwap
    private void setListenersAndState() {
        setCensorListenerAndState();
    }

    private void setCensorListenerAndState() {
        CheckBox censorCheckbox =  (CheckBox) optionsView.findViewById(R.id.optionsEnableCensor);
        censorCheckbox.setChecked(preferencesManager.getIsCensorOn());
        censorCheckbox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton bView, boolean isChecked) {
        switch (bView.getId()) {
            case R.id.optionsEnableCensor:
                preferencesManager.setIsCensorOn(isChecked);
                break;
        }
    }
}


