package com.example.wheatoride;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wheatoride.model.UserModel;
import com.example.wheatoride.utils.AndroidUtil;
import com.example.wheatoride.utils.FirebaseUtil;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class BuildProfileActivity extends AppCompatActivity {

    ImageView profilePic;
    EditText descriptionInput;
    EditText name;
    EditText email;
    Button createProfileBtn;
    ProgressBar progressBar;
    UserModel currentUserModel;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_profile);

        profilePic = findViewById(R.id.profile_image_view);
        descriptionInput = findViewById(R.id.profile_description);
        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        createProfileBtn = findViewById(R.id.profile_create_btn);
        progressBar = findViewById(R.id.profile_progress_bar);


        getUserData();

        createProfileBtn.setOnClickListener((v -> createBtnClick()));

        profilePic.setOnClickListener((v)-> ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
                .createIntent(intent -> {
                    imagePickLauncher.launch(intent);
                    return null;
                }));

        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            AndroidUtil.setProfilePic(BuildProfileActivity.this,selectedImageUri,profilePic);
                        }
                    }
                }
        );
    }

    void createBtnClick(){
        String newDescription = descriptionInput.getText().toString();
        if(newDescription.isEmpty()){
            return;
        }
        currentUserModel.setDescription(newDescription);
        setInProgress(true);

        if(selectedImageUri!=null){
            FirebaseUtil.getCurrentProfilePicStorageRef().putFile(selectedImageUri)
                    .addOnCompleteListener(task -> createToFirestore());
        }else{
            createToFirestore();
        }
    }

    void createToFirestore(){
        FirebaseUtil.currentUserDetails().set(currentUserModel)
                .addOnCompleteListener(task -> {
                    setInProgress(false);
                    if(task.isSuccessful()){
                        AndroidUtil.showToast(BuildProfileActivity.this,"Profile created successfully");
                    }else{
                        AndroidUtil.showToast(BuildProfileActivity.this,"Profile creation failed");
                    }
                });
    }

    void getUserData(){
        setInProgress(true);

        FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Uri uri  = task.getResult();
                        AndroidUtil.setProfilePic(BuildProfileActivity.this,uri,profilePic);
                    }
                });

        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            setInProgress(false);
            currentUserModel = task.getResult().toObject(UserModel.class);

            assert currentUserModel != null;
            descriptionInput.setText(currentUserModel.getDescription());
            name.setText(currentUserModel.getFullName());
            email.setText(currentUserModel.getEmail());
            AndroidUtil.setProfilePic(BuildProfileActivity.this, Uri.parse(currentUserModel.getProfilePicUri()), profilePic);
        });
    }

    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            createProfileBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            createProfileBtn.setVisibility(View.VISIBLE);
        }
    }
}
