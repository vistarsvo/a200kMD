package com.seostudio.vistar.testproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

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

    private void setListenersAndState() {
        setCensorListenerAndState();
        setSwapListenerAndState();
        setTouchSidesListenerAndState();
    }

    private void setTouchSidesListenerAndState() {
        CheckBox screenSideCheckbox =  (CheckBox) optionsView.findViewById(R.id.optionsEnableScreenSides);
        screenSideCheckbox.setChecked(preferencesManager.getIsScreenSideOn());
        screenSideCheckbox.setOnCheckedChangeListener(this);
    }

    private void setSwapListenerAndState() {
        CheckBox swapCheckbox =  (CheckBox) optionsView.findViewById(R.id.optionsEnableSwap);
        swapCheckbox.setChecked(preferencesManager.getIsSwapOn());
        swapCheckbox.setOnCheckedChangeListener(this);
    }

    private void setCensorListenerAndState() {
        CheckBox censorCheckbox =  (CheckBox) optionsView.findViewById(R.id.optionsEnableCensor);
        censorCheckbox.setChecked(preferencesManager.getIsCensorOn());
        censorCheckbox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton bView, boolean isChecked) {
        String message;
        switch (bView.getId()) {
            case R.id.optionsEnableCensor:
                preferencesManager.setIsCensorOn(isChecked);
                message = "Проверка цензурой на матосодержание " + (isChecked ? "включено" : "выключено");
                Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
                break;
            case R.id.optionsEnableSwap:
                preferencesManager.setIsSwapOn(isChecked);
                message = "Переключение жестом листания " + (isChecked ? "включено" : "выключено");
                Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
                break;
            case R.id.optionsEnableScreenSides:
                preferencesManager.setIsScreenSideOn(isChecked);
                message = "Переключение касанием краев экрана " + (isChecked ? "включено" : "выключено");
                Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}


