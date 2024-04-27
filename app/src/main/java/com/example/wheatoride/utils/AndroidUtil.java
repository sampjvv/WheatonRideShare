package com.example.wheatoride.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.wheatoride.model.UserModel;

public class AndroidUtil {

   public static  void showToast(Context context,String message){
       Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void passUserModelAsIntent(Intent intent, UserModel model){
       intent.putExtra("fullName",model.getFullName());
       intent.putExtra("email",model.getEmail());
       intent.putExtra("userId",model.getUserId());
       intent.putExtra("fcmToken",model.getFcmToken());
       intent.putExtra("profilePicUri",model.getProfilePicUri());
       intent.putExtra("description",model.getDescription());
       intent.putExtra("isDriver",model.isDriver());
       intent.putExtra("vehicleModel",model.getVehicleModel());
       intent.putExtra("vehicleDescription",model.getVehicleDescription());
       intent.putExtra("vehicleNumSeats",model.getVehicleNumSeats());
    }

    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setFullName(intent.getStringExtra("fullName"));
        userModel.setEmail(intent.getStringExtra("email"));
        userModel.setUserId(intent.getStringExtra("userId"));
        userModel.setFcmToken(intent.getStringExtra("fcmToken"));
        userModel.setProfilePicUri(intent.getStringExtra("profilePicUri"));
        userModel.setDescription(intent.getStringExtra("description"));
        userModel.setDriver(intent.getBooleanExtra("isDriver", false));
        userModel.setVehicleModel(intent.getStringExtra("vehicleModel"));
        userModel.setVehicleDescription(intent.getStringExtra("vehicleDescription"));
        return userModel;
    }

    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
        System.out.println("set Profile Pic");
    }
}
