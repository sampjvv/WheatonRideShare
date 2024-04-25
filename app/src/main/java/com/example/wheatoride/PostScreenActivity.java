package com.example.wheatoride;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wheatoride.adapter.ForumRecyclerAdapter;
import com.example.wheatoride.model.UserModel;
import com.example.wheatoride.model.ForumModel;
import com.example.wheatoride.utils.AndroidUtil;
import com.example.wheatoride.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.messaging.FirebaseMessaging;

public class PostScreenActivity extends AppCompatActivity {

    ImageView profilePic;
    TextView nameView;
    TextView descriptionInput;
    TextView phoneInput;
    TextView location;
    TextView vehicleModel;
    TextView vehicleNumSeats;
    TextView vehicleDescription;

    Button directMsg;

    String userID;

    Switch driverSwitch;
    LinearLayout vehicleInfoContainer;
    Button updateProfileBtn;
    ProgressBar progressBar;
    UserModel currentUserModel;

    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    ForumRecyclerAdapter forum;
    Bundle userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_screen);
        userInfo = getIntent().getExtras();
            //profile picture
        profilePic = findViewById(R.id.profile_image_view);
        selectedImageUri = Uri.parse(userInfo.getString("profilepic"));
        AndroidUtil.setProfilePic(getBaseContext(), Uri.parse(selectedImageUri.toString()), profilePic);

        //student name
        nameView = findViewById(R.id.student_name);
        String nameOfUser = (String) userInfo.getString("name");
        nameView.setText(nameOfUser);

            //description
        descriptionInput = findViewById(R.id.profile_description);
        CharSequence descText = userInfo.getCharSequence("desc");
        descriptionInput.setText(descText);

            //role


            //location
        location = findViewById(R.id.real_location);


            //vehicle info
        vehicleModel = findViewById(R.id.real_car_model);
        vehicleNumSeats = findViewById(R.id.seat_count_label);
        //vehicleDescription = view.findViewById(R.id.vehicle_description);*/

            //message button
        directMsg = findViewById(R.id.post_to_user_button);
        directMsg.setOnClickListener((v)-> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("toForum", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }



}