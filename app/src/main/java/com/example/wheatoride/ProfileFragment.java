package com.example.wheatoride;

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

import com.example.wheatoride.model.UserModel;
import com.example.wheatoride.utils.AndroidUtil;
import com.example.wheatoride.utils.FirebaseUtil;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.messaging.FirebaseMessaging;

public class ProfileFragment extends Fragment {

    ImageView profilePic;
    TextView nameView;
    TextView emailView;
    EditText descriptionInput;
    Switch driverSwitch;
    LinearLayout vehicleInfoContainer;
    EditText vehicleModel;
    EditText vehicleNumSeats;
    EditText vehicleDescription;
    Button updateProfileBtn;
    ProgressBar progressBar;
    TextView logoutBtn;
    UserModel currentUserModel;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;



    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            AndroidUtil.setProfilePic(getContext(),selectedImageUri,profilePic);
                        }
                    }
                }
                );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        profilePic = view.findViewById(R.id.profile_image_view);
        descriptionInput = view.findViewById(R.id.profile_description);
        nameView = view.findViewById(R.id.profile_name);
        emailView = view.findViewById(R.id.profile_email);
        driverSwitch = view.findViewById(R.id.profile_switch);
        vehicleInfoContainer = view.findViewById(R.id.vehicle_info_container);
        vehicleModel = view.findViewById(R.id.vehicle_model);
        vehicleNumSeats = view.findViewById(R.id.vehicle_num_seats);
        vehicleDescription = view.findViewById(R.id.vehicle_description);
        updateProfileBtn = view.findViewById(R.id.profle_update_btn);
        progressBar = view.findViewById(R.id.profile_progress_bar);
        logoutBtn = view.findViewById(R.id.logout_btn);

        vehicleInfoContainer.setVisibility(View.GONE);

        getUserData();

        updateProfileBtn.setOnClickListener((v -> updateBtnClick()));
        driverSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        vehicleInfoContainer.setVisibility(View.VISIBLE);
                    } else {
                        vehicleInfoContainer.setVisibility(View.GONE);
                    }
                });


        logoutBtn.setOnClickListener((v)-> FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUtil.logout();
                Intent intent = new Intent(getContext(),SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }));

        profilePic.setOnClickListener((v)-> ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
                .createIntent(intent -> {
                    imagePickLauncher.launch(intent);
                    return null;
                }));

        return view;
    }

    void updateBtnClick(){

        setInProgress(true);

        String successMessage = "Updated successfully";

        //description
        String newDescription = descriptionInput.getText().toString();
        if(newDescription.isEmpty()){
            successMessage = successMessage + "; empty description";
        }
        currentUserModel.setDescription(newDescription);

        //vehicle
        boolean isDriver = driverSwitch.isChecked();
        String newVehicleModel = vehicleModel.getText().toString();
        String newVehicleNumSeats = vehicleNumSeats.getText().toString();
        String newVehicleDescription = vehicleDescription.getText().toString();

        if(isDriver){
            if(newVehicleModel.isEmpty() ||
                    newVehicleNumSeats.isEmpty() ||
                    newVehicleDescription.isEmpty()) {
                AndroidUtil.showToast(getContext(),"Update failed: missing vehicle info");
                setInProgress(false);
                return;
            }
            currentUserModel.setDriver(isDriver);
            currentUserModel.setVehicleModel(newVehicleModel);
            currentUserModel.setVehicleNumSeats(newVehicleNumSeats);
            currentUserModel.setVehicleDescription(newVehicleDescription);
        } else {
            currentUserModel.setDriver(isDriver);
            currentUserModel.setVehicleModel("");
            currentUserModel.setVehicleNumSeats("");
            currentUserModel.setVehicleDescription("");
        }

        //profile pic
        if(selectedImageUri!=null){
            System.out.println("model set profile pic set");
            currentUserModel.setProfilePicUri(selectedImageUri.toString());
            updateToFirestore(successMessage);
        }else{
            updateToFirestore(successMessage);
        }





    }

    void updateToFirestore(String successMessage){
        FirebaseUtil.currentUserDetails().set(currentUserModel)
                .addOnCompleteListener(task -> {
                    setInProgress(false);
                    if(task.isSuccessful()){
                        AndroidUtil.showToast(getContext(),successMessage);
                    }else{
                        AndroidUtil.showToast(getContext(),"Updated failed");
                    }
                });
    }



    void getUserData(){
        setInProgress(true);

        FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl()
                        .addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Uri uri  = task.getResult();
                                    AndroidUtil.setProfilePic(getContext(),uri,profilePic);
                                }
                        });

        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            setInProgress(false);
            currentUserModel = task.getResult().toObject(UserModel.class);

            assert currentUserModel != null;
            descriptionInput.setText(currentUserModel.getDescription());
            nameView.setText(currentUserModel.getFullName());
            emailView.setText(currentUserModel.getEmail());
            vehicleModel.setText(currentUserModel.getVehicleModel());
            vehicleNumSeats.setText(currentUserModel.getVehicleNumSeats());
            vehicleDescription.setText(currentUserModel.getVehicleDescription());
            driverSwitch.setChecked(currentUserModel.isDriver());
            if(currentUserModel.isDriver()){
                vehicleInfoContainer.setVisibility(View.VISIBLE);
            }

            AndroidUtil.setProfilePic(getContext(), Uri.parse(currentUserModel.getProfilePicUri()), profilePic);
        });
    }


    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            updateProfileBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            updateProfileBtn.setVisibility(View.VISIBLE);
        }
    }


}













