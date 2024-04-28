package com.example.wheatoride;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wheatoride.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.example.wheatoride.model.ForumModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.text.DateFormat;

public class CreatePostActivity extends AppCompatActivity {

    EditText descriptionEditText, seatsEditText, locationEditText;
    ImageButton backButton;
    Button createPostButton;
    ForumModel forumModel;
    FirebaseAuth mAuth;
    DocumentReference userRef;
    CollectionReference postRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "CreatePostActivity";
    String currentUserID_s, saveCurrentTime_s, postText_s, numOfSeats_s, location_s;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_create_post);

        mAuth = FirebaseAuth.getInstance();
        currentUserID_s = mAuth.getCurrentUser().getUid();
        postRef = db.collection("posts");

        userRef = FirebaseUtil.currentUserDetails();

        backButton  = findViewById(R.id.back_btn);
        descriptionEditText = findViewById(R.id.post_text);
        createPostButton = findViewById(R.id.save_new_post);
        locationEditText = findViewById(R.id.create_location_text);
        seatsEditText = findViewById(R.id.avalible_seats_text);

        postText_s = descriptionEditText.getText().toString();
        location_s = locationEditText.getText().toString();
        numOfSeats_s = seatsEditText.getText().toString();

        backButton.setOnClickListener((v)-> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("toForum", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


        createPostButton.setOnClickListener(v-> {
            postText_s = descriptionEditText.getText().toString();
            saveCurrentTime_s = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(Timestamp.now().toDate()).toString();
            numOfSeats_s = seatsEditText.getText().toString();
            location_s = locationEditText.getText().toString();
            forumModel = new ForumModel(numOfSeats_s,descriptionEditText.toString(), location_s, saveCurrentTime_s);

            savingPostInformationToDatabase();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

    }
    void savingPostInformationToDatabase() {
        if (!postText_s.equals("") && !location_s.equals("") && !numOfSeats_s.equals("")) {
            Map<String, String> postMap = new HashMap<>();
            postMap.put("userId", currentUserID_s);
            postMap.put("description", postText_s);
            postMap.put("numOfSeats", numOfSeats_s);
            postMap.put("postTimeStamp", saveCurrentTime_s);
            postMap.put("location", location_s);
            postMap.put("isConfirmed", "false");



            postRef.add(postMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "DocumentSnapshot added with ID " + documentReference.getId());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document");
                }
            });

        }
    }
}
