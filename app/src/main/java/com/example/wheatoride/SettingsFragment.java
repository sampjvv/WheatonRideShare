package com.example.wheatoride;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
        darkModeBtn.setOnClickListener((v -> darkPress()));
        System.out.println("I am here");

        return view;
    }
    void darkPress(){
        if(isDark){
            isDark = false;
        }else{
            isDark = true;
        }
        updateDarkModeBtn();


    }
    @SuppressLint("SetTextI18n")
    void updateDarkModeBtn() {
        System.out.println(isDark == true);

        if(isDark){
            darkModeBtn.setText("dark");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


            System.out.println("This is happend");

        }else{

            darkModeBtn.setText("light");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
    }
}
