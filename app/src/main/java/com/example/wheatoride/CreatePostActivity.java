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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import com.example.wheatoride.model.ForumModel;
import com.example.wheatoride.ForumActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public class CreatePostActivity extends AppCompatActivity {

    EditText text;
    EditText availbaleSeatsText, locationEdit;
    ImageButton backButton;
    Button createPostButton;
    ForumModel forumModel;
    FirebaseAuth mAuth;
    DocumentReference userRef;
    CollectionReference postRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "CreatePostActivity";
    String currentUserID, saveCurrentTime, postText, numOfSeats, location;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_create_post);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        postRef = db.collection("posts");

        userRef = FirebaseUtil.currentUserDetails();

        text = findViewById(R.id.post_text);
        backButton  = findViewById(R.id.back_btn);
        createPostButton = findViewById(R.id.save_new_post);
        locationEdit = findViewById(R.id.create_location_text);
        availbaleSeatsText = findViewById(R.id.avalible_seats_text);

        postText = text.getText().toString();
        location = locationEdit.getText().toString();

       // backButton.setOnClickListener((v)-> getOnBackPressedDispatcher().onBackPressed());
        backButton.setOnClickListener((v)-> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });


        createPostButton.setOnClickListener(v-> {
            postText = text.getText().toString();
            saveCurrentTime = FirebaseUtil.timestampToString(Timestamp.now());
            numOfSeats = availbaleSeatsText.getText().toString();
            Log.d("seats: ", numOfSeats);
            location = locationEdit.getText().toString();
            forumModel = new ForumModel(numOfSeats,text.toString(),location, Timestamp.now());

            //location = locationEdit.getText().toString();
            savingPostInformationToDatabase();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

    }
    void savingPostInformationToDatabase(){
        Map<String,String> postMap = new HashMap<>();
        postMap.put("userId", currentUserID);
        postMap.put("description", postText);
        postMap.put("numOfSeats", numOfSeats);
        postMap.put("postTimeStamp", saveCurrentTime);
        postMap.put("location", location);

        //FirebaseFirestore.getInstance().collection("posts").document(FirebaseUtil.currentUserId()).set(forumModel);

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
