package com.example.wheatoride;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.wheatoride.utils.FirebaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageButton searchButton;
    ChatFragment chatFragment;
    ProfileFragment profileFragment;
    ForumFragment forumFragment;

    RequestRideFragment requestRideFragment;

    SettingsFragment settingsFragment;
    boolean inChat = true;
    boolean inRide = false;
    boolean noSearch = false;

    public void searchFinder(Fragment fragment){
        if(fragment.equals(chatFragment)){
            searchButton.setEnabled(true);
            inChat = true;
            inRide = false;
            noSearch = false;

        }
        else if(fragment.equals(forumFragment)){
            searchButton.setEnabled(true);
            inChat = false;
            inRide = true;
            noSearch = false;
        }
        else{
            searchButton.setEnabled(false);
            inChat = false;
            inRide = false;
            noSearch = true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();
        forumFragment = new ForumFragment();
        settingsFragment = new SettingsFragment();
        requestRideFragment = new RequestRideFragment();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        searchButton = findViewById(R.id.main_search_btn);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_chat){
                    searchFinder(chatFragment);
                    searchButton.setOnClickListener((v)->{
                        startActivity(new Intent(MainActivity.this,SearchUserActivity.class));
                    });
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,chatFragment).commit();
                }
                if(item.getItemId()==R.id.menu_profile){
                    searchFinder(profileFragment);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,profileFragment).addToBackStack(null).commit();
                }
                if(item.getItemId()==R.id.menu_offer){
                    searchButton.setEnabled(true);
                    searchButton.setOnClickListener((v)->{
                        startActivity(new Intent(MainActivity.this,SearchRideActivity.class));
                    });
                    searchFinder(forumFragment);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,forumFragment).commit();
                }
                if(item.getItemId()==R.id.menu_request){
                    searchButton.setEnabled(true);
                    searchButton.setOnClickListener((v)->{
                        startActivity(new Intent(MainActivity.this,SearchRideActivity.class));
                    });
                    searchFinder(forumFragment);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,requestRideFragment).commit();
                }

                if(item.getItemId()==R.id.menu_settings){
                    searchFinder(settingsFragment);
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,settingsFragment).commit();
                }

                return true;
            }
        });


        boolean toForum = false;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            toForum = extras.getBoolean("toForum");
        }

        if (toForum) {
            bottomNavigationView.setSelectedItemId(R.id.menu_offer);
            searchFinder(forumFragment);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,forumFragment).commit();

        } else {
            bottomNavigationView.setSelectedItemId(R.id.menu_chat);
        }


        getFCMToken();

    }

    void getFCMToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String token = task.getResult();
                FirebaseUtil.currentUserDetails().update("fcmToken",token);

            }
        });
    }
}