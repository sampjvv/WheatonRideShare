package com.example.wheatoride;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    Button darkModeBtn;
    boolean isDark;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        darkModeBtn = view.findViewById(R.id.dark_mode_switch);
        darkModeBtn.setOnClickListener((v -> updateDarkModeBtn()));

        return view;
    }

    @SuppressLint("SetTextI18n")
    void updateDarkModeBtn() {
        if(isDark){
            darkModeBtn.setText("dark");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            isDark = false;
        }else{
            darkModeBtn.setText("light");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            isDark = true;
        }
    }
}
